package com.benson.aicodehelper.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ChatMemoryManager {
    @Bean("windowChatMemory")
    public ChatMemory windowChatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(10).build();
    }

//    @Bean
//    ChatMemoryStore chatMemoryStore(/* 注入你的 Redis client 配置 */) {
//        return RedisChatMemoryStore.builder()
//                // ...redis连接信息...
//                .build();
//    }

    @Bean("chatMemoryProvider")
    ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
//                .chatMemoryStore(store)
                .build();
    }

}
