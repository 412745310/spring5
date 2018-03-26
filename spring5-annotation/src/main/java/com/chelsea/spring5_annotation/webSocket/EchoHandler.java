package com.chelsea.spring5_annotation.webSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

/**
 * socket事件处理
 * 
 * @author shevchenko
 *
 */
@Component
public class EchoHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive().map(msg -> session.textMessage("ECHO -> " + msg.getPayloadAsText())));
    }

}
