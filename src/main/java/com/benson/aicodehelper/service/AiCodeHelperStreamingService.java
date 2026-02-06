package com.benson.aicodehelper.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

//@AiService(streamingChatModel = "streamingChatModel")
// @AiService注解会默认将mcp、tool注入，手动创建的不会
public interface AiCodeHelperStreamingService {
    // 流式对话
    TokenStream chatStream(@MemoryId int memoryId, @UserMessage String userMessage);

}
