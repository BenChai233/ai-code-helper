package com.benson.aicodehelper.config;

import dev.langchain4j.community.model.zhipu.ZhipuAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ChatModelManager {

    @Value("${zhipu.ai.api-key}")
    private String apiKey;

    @Bean(name = "zhipuChatModel")
    public ChatModel zhipuChatModel() {
        return ZhipuAiChatModel.builder()
                .apiKey(apiKey)
                .model("glm-4.6")
                .temperature(0.7)
                .maxToken(4096)
                .build();
    }
}
