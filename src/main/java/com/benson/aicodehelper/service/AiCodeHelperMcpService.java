package com.benson.aicodehelper.service;

import dev.langchain4j.service.spring.AiService;

@AiService(chatModel = "zhipuChatModel",
        chatMemory = "windowChatMemory",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "localTimeTool",
        toolProvider = "mcpToolProvider")
public interface AiCodeHelperMcpService {
    String chat(String userMessage);
}
