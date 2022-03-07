package com.example.reactiveservice.configurations;

import com.example.reactiveservice.producer.GreetingsProducer;
import com.example.reactiveservice.request.GreetingsRequest;
import com.example.reactiveservice.response.GreetingsResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

@Configuration
public class WebSocketConfiguration {
    @Bean
    SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping() {
            {
                setUrlMap(Map.of("/ws/greetings", webSocketHandler));
                setOrder(10);
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler(GreetingsProducer greetingsProducer) {
        return session -> {

            var response = session
                    .receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map(GreetingsRequest::new)
                    .flatMap(greetingsProducer::greet)
                    .map(GreetingsResponse::getMessage)
                    .map(session::textMessage);

            return session.send(response);
        };
    }
}
