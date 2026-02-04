package com.benson.aicodehelper.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;


@AiService(chatModel = "zhipuChatModel", chatMemory = "windowChatMemory", chatMemoryProvider = "chatMemoryProvider", contentRetriever = "contentRetriever")
public interface AiCodeHelperRagService {
    Result<String> chatWithRag(@UserMessage String userMessage);
}
