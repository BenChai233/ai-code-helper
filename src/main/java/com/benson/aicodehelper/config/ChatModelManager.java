package com.benson.aicodehelper.config;

import dev.langchain4j.community.model.zhipu.ZhipuAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ChatModelManager {

    @Bean(name = "zhipuChatModel")
    public ChatModel zhipuChatModel() {
        return ZhipuAiChatModel.builder()
                .apiKey("3c9be82654a04f959c2c11ec26f0ca65.iMEWa9tsYuGgCMsZ")
                .model("glm-4.6")
                .temperature(0.7)
                .maxToken(4096)
                .build();
    }
}
