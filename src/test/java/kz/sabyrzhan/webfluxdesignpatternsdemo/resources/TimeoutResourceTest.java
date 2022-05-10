package kz.sabyrzhan.webfluxdesignpatternsdemo.resources;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TimeoutResourceTest extends BaseTest {
    @Test
    void testTimeout() {
        StepVerifier.create(runTimeouts()).verifyComplete();
    }

    Mono<Void> runTimeouts() {
        return Flux.range(1, 40)
                .map(i -> {
                    var list = new ArrayList<>();
                    var stopWatch = new StopWatch();
                    stopWatch.start();
                    list.add(stopWatch);
                    return list;
                })
                .flatMap(list -> this.webClient
                        .get()
                        .uri("/api/timeouts/timeout")
                        .retrieve()
                        .bodyToMono(String.class)
                        .then(Mono.fromSupplier(() -> {
                            ((StopWatch)list.get(0)).stop();
                            return list;
                        })))
                .doOnNext(objects -> System.out.println("Total time taken (in seconds): " + ((StopWatch)objects.get(0)).getTotalTimeSeconds()))
                .then();
    }
}