package com.chelsea.spring5_function.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.chelsea.spring5_function.service.PostService;

/**
 * 路由层
 * 
 * @author shevchenko
 *
 */
@Configuration
@EnableWebFlux
public class Route {

    @Autowired
    PostService postService;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/posts"), postService::all)
                .andRoute(
                        RequestPredicates.POST("/posts").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        postService::create).andRoute(RequestPredicates.GET("/posts/{id}"), postService::get)
                .andRoute(RequestPredicates.PUT("/posts/{id}"), postService::update) // 默认参数为application/json
                .andRoute(RequestPredicates.DELETE("/posts/{id}"), postService::delete);
    }

}
