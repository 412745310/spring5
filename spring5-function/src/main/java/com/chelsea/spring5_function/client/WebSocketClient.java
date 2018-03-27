package com.chelsea.spring5_function.client;

import java.net.URI;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import reactor.core.publisher.Flux;

/**
 * WebFlux的WebSocket客户端
 * 
 * @author shevchenko
 *
 */
public class WebSocketClient {

    public static void main(final String[] args) {
        ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                URI.create("ws://localhost:8080/echo"),
                session -> session.send(Flux.just(session.textMessage("Hello")))
                        .thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))
                        .doOnNext(System.out::println).then()).block(Duration.ofMillis(1000));
    }

}
