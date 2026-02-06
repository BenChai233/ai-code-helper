package com.benson.aicodehelper.controller;

import com.benson.aicodehelper.service.AiCodeHelperStreamingService;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/ai")
public class AiCodeHelperController {
    @Resource
    private AiCodeHelperStreamingService aiCodeHelperStreamingService;

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(int memoryId, String message) {
        TokenStream tokenStream = aiCodeHelperStreamingService.chatStream(memoryId, message);
        Flux<String> flux = Flux.create(emitter -> {
            AtomicBoolean emitted = new AtomicBoolean(false);
            tokenStream.onPartialResponse(part -> {
                        if (part == null || part.isEmpty()) {
                            return;
                        }
                        emitted.set(true);
                        emitter.next(part);
                    })
                    .onCompleteResponse(response -> {
                        if (!emitted.get()) {
                            // Some streams don't emit deltas; fall back to the final text once.
                            if (response.aiMessage() != null) {
                                String text = response.aiMessage().text();
                                if (text != null && !text.isEmpty()) {
                                    emitter.next(text);
                                }
                            }
                        }
                        emitter.complete();
                    })
                    .onError(emitter::error)
                    .start();
        }, FluxSink.OverflowStrategy.BUFFER);
        return flux
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }
}
