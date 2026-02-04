package com.benson.aicodehelper.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Value("${zhipu.ai.api-key}")
    private String apiKey;

    @Bean("mcpToolProvider")
    public McpToolProvider mcpToolProvider() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing zhipu.ai.api-key for MCP transport");
        }
        // 和 MCP 服务通讯（Zhipu Web Search 当前提供的是 legacy HTTP/SSE 端点）
        McpTransport transport = HttpMcpTransport.builder()
                .sseUrl("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + apiKey)
                .logRequests(true) // 开启日志，查看更多信息
                .logResponses(true)
                .build();
        // 创建 MCP 客户端
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .key("BensonMcpClient")
                .transport(transport)
                .build();
        // 从 MCP 客户端获取工具
        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();
        return toolProvider;
    }
}
