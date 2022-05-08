package kz.sabyrzhan.webfluxdesignpatternsdemo.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/bulkheads")
public class BulkheadResource {
    private final Scheduler DO_NOTHING_SCHEDULER = Schedulers.newParallel("nothing-scheduler", 10);
    private final Scheduler FIBONACCI_SCHEDULER = Schedulers.newParallel("fib-scheduler", 10);

    @GetMapping("/calculate/{number}")
    public Mono<ResponseEntity> fibonacci(@PathVariable int number) {
        return Mono.fromSupplier(() -> calculate(number))
                .subscribeOn(FIBONACCI_SCHEDULER)
                .map(ResponseEntity.ok()::body);
    }

    @GetMapping("/calculate/nothing")
    public Mono<ResponseEntity> calculateNothing() {
        return Mono.fromSupplier(() -> "Nothing")
                .subscribeOn(DO_NOTHING_SCHEDULER)
                .map(ResponseEntity.ok()::body);
    }

    private long calculate(int number) {
        if (number < 2) {
            return number;
        }
        return calculate(number - 1) + calculate(number - 2);
    }
}
