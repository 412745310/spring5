package com.chelsea.spring5_function.client;

import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebFlux的SSE客户端
 * 
 * @author shevchenko
 *
 */
public class SSEClient {

    public static void main(final String[] args) {
        WebClient client = WebClient.create();
        client.get()
                .uri("http://localhost:8080/sse/randomNumbers")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(
                        response -> response.body(BodyExtractors
                                .toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {})))
                .filter(sse -> Objects.nonNull(sse.data())).map(ServerSentEvent::data).buffer(10)
                .doOnNext(System.out::println).blockFirst();
    }

}
