package kz.sabyrzhan.webfluxdesignpatternsdemo.resources;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class BaseTest {
    WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }
}
