package kz.sabyrzhan.webfluxdesignpatternsdemo.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/timeouts")
public class TimeoutResource {
    @GetMapping("/timeout")
    public Mono<ResponseEntity<String>> timeout() {
        return Mono.delay(Duration.ofSeconds(10))
                .timeout(Duration.ofSeconds(2))
                .map(l -> ResponseEntity.ok().body("success"))
                .onErrorReturn(t -> t instanceof TimeoutException, ResponseEntity.ok().body("error"));
    }
}
