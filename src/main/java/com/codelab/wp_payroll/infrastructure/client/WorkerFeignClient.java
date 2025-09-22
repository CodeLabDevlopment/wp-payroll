package com.codelab.wp_payroll.infrastructure.client;

import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;

@FeignClient(name = "wp-worker")
public interface WorkerFeignClient {

    @GetMapping("/api/v1/worker/{workerId}")
    @CircuitBreaker(name = "wp-worker", fallbackMethod = "getWorkerByIdAlternative")
    Response getWorkerById(@PathVariable Long workerId);

    default Response getWorkerByIdAlternative() {
        return Response.builder()
                .status(200)
                .body("{\"id\": 0, \"name\": \"Brann\", \"dailyIncome\": 0.0}", StandardCharsets.UTF_8)
                .build();
    }

}
