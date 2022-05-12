package kz.sabyrzhan.webfluxdesignpatternsdemo.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.concurrent.TimeoutException;

@Service
public class CircuitBreakerService {
    Random random = new Random();
    @CircuitBreaker(name = "fibonacci-service", fallbackMethod = "fallbackService")
    public Mono<Long> execute() {
        if (random.nextInt(2) == 1) {
            return Mono.just(1L);
        } else {
            return Mono.error(new TimeoutException("custom timeout"));
        }
    }

    public Mono<Long> fallbackService(TimeoutException exc) {
        return Mono.just(-1L);
    }
}
