package com.benson.aicodehelper.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
@Configuration
public class McpConfig {

    @Value("${zhipu.ai.api-key}")
    private String apiKey;

//    @Value("${baidu.map.api-key}")
//    private String baiduMapApiKey;

    @Bean("mcpToolProvider")
    public McpToolProvider mcpToolProvider() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing zhipu.ai.api-key for MCP transport");
        }
        // 和 MCP 服务通讯（Zhipu Web Search 当前提供的是 legacy HTTP/SSE 端点）
        McpTransport webSearchTransport = HttpMcpTransport.builder()
                .sseUrl("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + apiKey)
                .logRequests(true) // 开启日志，查看更多信息
                .logResponses(true)
                .build();
        // 创建 MCP 客户端
        McpClient zhipuMcpClient = new DefaultMcpClient.Builder()
                .key("zhipu-web-search")
                .transport(webSearchTransport)
                .build();

//        if (baiduMapApiKey == null || baiduMapApiKey.isBlank()) {
//            throw new IllegalStateException("Missing baidu.map.api-key for MCP transport");
//        }
//        McpTransport baiduMapTransport = StdioMcpTransport.builder()
//                .command(List.of("npx", "-y", "@baidumap/mcp-server-baidu-map"))
//                .environment(Map.of("BAIDU_MAP_API_KEY", baiduMapApiKey))
//                .logEvents(true)
//                .build();
//        McpClient baiduMapMcpClient = new DefaultMcpClient.Builder()
//                .key("baidu-map")
//                .transport(baiduMapTransport)
//                .build();
        // 从 MCP 客户端获取工具
        McpToolProvider toolProvider = McpToolProvider.builder()
//                .mcpClients(zhipuMcpClient, baiduMapMcpClient)
                .mcpClients(zhipuMcpClient)
                .build();
        return toolProvider;
    }
}
