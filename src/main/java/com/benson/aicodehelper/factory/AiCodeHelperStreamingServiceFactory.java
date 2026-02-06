package com.benson.aicodehelper.factory;

import com.benson.aicodehelper.service.AiCodeHelperStreamingService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCodeHelperStreamingServiceFactory {

    @Resource(name = "streamingChatModel")
    private StreamingChatModel streamingChatModel;

    @Resource(name = "windowChatMemory")
    private ChatMemory chatMemory;

    @Resource
    private ChatMemoryProvider chatMemoryProvider;

    @Bean
    public AiCodeHelperStreamingService aiCodeHelperStreamingService() {
        return AiServices.builder(AiCodeHelperStreamingService.class)
                .chatMemory(chatMemory)
                .chatMemoryProvider(chatMemoryProvider)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
