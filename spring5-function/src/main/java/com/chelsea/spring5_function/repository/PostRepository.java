package com.chelsea.spring5_function.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.chelsea.spring5_function.entity.Post;

/**
 * 持久层
 * 
 * @author shevchenko
 *
 */
@Repository
public class PostRepository {

    // 模拟数据库
    private Map<Long, Post> data = new HashMap<>();
    // 设置ID
    private AtomicLong nextIdGenerator = new AtomicLong(1L);

    // 初始化数据
    public PostRepository() {
        Stream.of("post one", "post two").forEach(title -> {
            Long id = this.nextId();
            data.put(id, Post.builder().id(id).title(title).content("你是" + id).build());
        });
    }

    private Long nextId() {
        return nextIdGenerator.getAndIncrement(); // the previous value
    }

    public Flux<Post> findAll() {
        return Flux.fromIterable(data.values());
    }

    public Mono<Post> findById(Long id) {
        if (data.get(id) == null) {
            return Mono.empty();
        }
        return Mono.just(data.get(id));
    }

    public Mono<Post> save(Post post) {
        Long id = nextId();
        Post saved = Post.builder().id(id).title(post.getTitle()).content(post.getContent()).build();
        data.put(id, saved);
        return Mono.just(saved);
    }

    public Mono<Post> update(Long id, Post post) {
        Post old = data.get(id);
        old.setTitle(post.getTitle());
        old.setContent(post.getContent());
        data.put(id, old);
        return Mono.just(old);
    }

    public Mono<Post> createOrUpdate(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            return save(post);
        } else {
            return update(post.getId(), post);
        }
    }

    public Mono<Void> delete(Long id) {
        data.remove(id);
        return Mono.empty();
    }

}
