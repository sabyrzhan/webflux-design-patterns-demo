package kz.sabyrzhan.webfluxdesignpatternsdemo.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class BulkheadResourceTest extends BaseTest {
    @Test
    void runConcurrent() {
        StepVerifier.create(Flux.zip(runFibonacci(), runDoNothing()).parallel()).verifyComplete();
    }

    Mono<Void> runFibonacci() {
        return Flux.range(1, 40)
                .flatMap(i -> this.webClient.get().uri("/api/bulkheads/calculate/50").retrieve().bodyToMono(Long.class))
                .doOnNext(System.out::println)
                .then();
    }

    Mono<Void> runDoNothing() {
        return Mono.delay(Duration.ofMillis(100))
                .thenMany(Flux.range(1, 40))
                .flatMap(i -> this.webClient.get().uri("/api/bulkheads/calculate/nothing").retrieve().bodyToMono(String.class))
                .doOnNext(System.out::println)
                .then();
    }
}