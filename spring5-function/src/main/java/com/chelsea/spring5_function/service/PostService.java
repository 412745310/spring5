package com.chelsea.spring5_function.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import com.chelsea.spring5_function.entity.Post;
import com.chelsea.spring5_function.repository.PostRepository;

/**
 * 业务层
 * 
 * @author shevchenko
 *
 */
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(postRepository.findAll(), Post.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Post.class).flatMap(post -> this.postRepository.createOrUpdate(post))
                .flatMap(post -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        return this.postRepository.findById(Long.valueOf(req.pathVariable("id")))
                .flatMap(post -> ServerResponse.ok().syncBody(post)).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return Mono.zip((data) -> {
            Post p = (Post) data[0];// 原始数据
                Post p2 = (Post) data[1];// 修改的数据
                p.setTitle(p2.getTitle());
                p.setContent(p2.getContent());
                return p;
            }, this.postRepository.findById(Long.valueOf(req.pathVariable("id"))), req.bodyToMono(Post.class))
                .cast(Post.class).flatMap(post -> this.postRepository.createOrUpdate(post))
                .flatMap(post -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return ServerResponse.noContent().build(this.postRepository.delete(Long.valueOf(req.pathVariable("id"))));
    }
}
