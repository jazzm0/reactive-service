package com.example.reactiveservice.producer;

import com.example.reactiveservice.request.GreetingsRequest;
import com.example.reactiveservice.response.GreetingsResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Component
public class GreetingsProducer {
    public Flux<GreetingsResponse> greet(GreetingsRequest request) {
        return Flux.fromStream(Stream.generate(() -> new GreetingsResponse("hello" + request.getName() + " @" + Instant.now()))).delayElements(Duration.ofSeconds(1));
    }
}
