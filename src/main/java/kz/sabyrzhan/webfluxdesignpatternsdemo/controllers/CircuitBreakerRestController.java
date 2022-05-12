package kz.sabyrzhan.webfluxdesignpatternsdemo.controllers;

import kz.sabyrzhan.webfluxdesignpatternsdemo.services.CircuitBreakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cbreaker")
@RequiredArgsConstructor
public class CircuitBreakerRestController {
    private final CircuitBreakerService fibonacciService;

    @GetMapping
    public Mono<ResponseEntity<Long>> fibonacciBreaker() {
        return fibonacciService.execute().map(val -> {
            if (val == -1) {
                return ResponseEntity.ok(val);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Timeout after 2sec");
            }
        });
    }
}
