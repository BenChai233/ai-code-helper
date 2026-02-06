package com.benson.aicodehelper.config;

import dev.langchain4j.community.model.zhipu.ZhipuAiChatModel;
import dev.langchain4j.community.model.zhipu.ZhipuAiStreamingChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatModelManager {

    @Value("${zhipu.ai.api-key}")
    private String apiKey;

    @Resource
    private ChatModelListener chatModelListener;

    @Bean(name = "zhipuChatModel")
    public ChatModel zhipuChatModel() {
        return ZhipuAiChatModel.builder()
                .apiKey(apiKey)
//                .logRequests(true)
//                .logResponses(true)
                .listeners(List.of(chatModelListener))
                .model("glm-4.6")
                .temperature(0.7)
                .maxToken(4096)
                .build();
    }

    @Bean(name = "streamingChatModel")
    public StreamingChatModel streamingChatModel() {
        ZhipuAiStreamingChatModel streamingChatModel = ZhipuAiStreamingChatModel.builder()
                .model("glm-4.6")
                .logRequests(true)
                .logResponses(true)
                .listeners(List.of(chatModelListener))
                .apiKey(apiKey)
                .build();
        return streamingChatModel;
    }
}
