package com.benson.aicodehelper.controller;

import com.benson.aicodehelper.service.AiCodeHelperStreamingService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiCodeHelperController {
    @Resource
    private AiCodeHelperStreamingService aiCodeHelperStreamingService;

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(int memoryId, String message) {
        Flux<String> flux = aiCodeHelperStreamingService.chatStream(memoryId, message);
        return flux.map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }
}
