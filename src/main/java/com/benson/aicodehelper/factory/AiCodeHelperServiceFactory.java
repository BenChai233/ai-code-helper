package com.benson.aicodehelper.factory;

import com.benson.aicodehelper.service.AiCodeHelperService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;

//@Configuration
public class AiCodeHelperServiceFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Bean
    public AiCodeHelperService aiCodeHelperService() {
        return AiServices.builder(AiCodeHelperService.class).chatModel(qwenChatModel).build();
    }
}
