package com.example.reactiveservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class HttpConfiguration {
    @Bean
    RouterFunction<ServerResponse> routes() {
        return route().GET("/reservations", request -> ServerResponse.ok().body("", Void.class)).build();
    }
}
