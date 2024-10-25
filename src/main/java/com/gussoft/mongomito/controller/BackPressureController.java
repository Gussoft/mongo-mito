package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.transfer.response.DishResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;

@RestController
@RequestMapping("/backpressure")
public class BackPressureController {

    @GetMapping(value = "/jsonstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) //"text/event-stream"
    public Flux<DishResponse> jsonStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(t -> new DishResponse("1", "name", new BigDecimal(15), true));
    }

    @GetMapping("/limitRate")
    public Flux<Integer> testLimit() {
        return Flux.range(1, 100)
                .log()
                .limitRate(10,2)
                .delayElements(Duration.ofMillis(100));
    }

}
