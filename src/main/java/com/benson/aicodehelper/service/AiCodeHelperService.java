package com.benson.aicodehelper.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

@AiService(chatModel = "zhipuChatModel", chatMemory = "windowChatMemory", chatMemoryProvider = "chatMemoryProvider")
public interface AiCodeHelperService {

    @SystemMessage(fromResource = "prompt/system-prompt.txt")
    String chat(String userMessage);

    @SystemMessage(fromResource = "prompt/system-prompt.txt")
    String chatWithMemory(@MemoryId String memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "prompt/system-prompt.txt")
    Report chatForReport(String userMessage);

    // 学习报告
    record Report(String name, List<String> suggestionList){}

}
