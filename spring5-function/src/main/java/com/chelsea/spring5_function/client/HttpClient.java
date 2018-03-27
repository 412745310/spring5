package com.chelsea.spring5_function.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.chelsea.spring5_function.entity.Post;


public class HttpClient {

    public static void main(String[] args) {
        WebClient client = WebClient.create();
        Mono<Post> result =
                client.get().uri("http://localhost:8080/posts/1").accept(MediaType.APPLICATION_JSON).exchange()
                        .flatMap(response -> response.bodyToMono(Post.class));
        System.out.println(result.block());
    }

}
