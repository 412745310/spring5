package com.chelsea.spring5_annotation.controller;

import io.netty.util.internal.ThreadLocalRandom;

import java.time.Duration;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

/**
 * 服务器推送事件（Server-Sent Events，SSE）
 * 
 * @author shevchenko
 *
 */
@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping("/randomNumbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(seq -> {
                    System.out.println(seq);
                    return Tuples.of(seq, ThreadLocalRandom.current().nextInt());
                })
                .map(data -> ServerSentEvent.<Integer>builder().event("random").id(Long.toString(data.getT1()))
                        .data(data.getT2()).build());
    }

}
