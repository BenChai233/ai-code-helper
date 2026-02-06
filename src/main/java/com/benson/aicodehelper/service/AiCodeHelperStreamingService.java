package com.benson.aicodehelper.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

//@AiService(streamingChatModel = "streamingChatModel")
// @AiService注解会默认将mcp、tool注入，手动创建的不会
public interface AiCodeHelperStreamingService {
    // 流式对话
    Flux<String> chatStream(@MemoryId int memoryId, @UserMessage String userMessage);

}
