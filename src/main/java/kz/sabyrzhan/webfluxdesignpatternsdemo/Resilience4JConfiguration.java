package kz.sabyrzhan.webfluxdesignpatternsdemo;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Configuration
public class Resilience4JConfiguration {
    @Bean
    public CircuitBreakerConfigCustomizer circuitBreakerConfigCustomizer() {
        return CircuitBreakerConfigCustomizer.of(
                "cbreaker-service", builder -> {
                    builder.slidingWindow(10, 5, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED);
                    builder.failureRateThreshold(50);
                    builder.waitDurationInOpenState(Duration.ofSeconds(10));
                    builder.maxWaitDurationInHalfOpenState(Duration.ofSeconds(10));
                    builder.permittedNumberOfCallsInHalfOpenState(4);
                    builder.recordException(throwable -> throwable instanceof TimeoutException);
                }
        );
    }
}
