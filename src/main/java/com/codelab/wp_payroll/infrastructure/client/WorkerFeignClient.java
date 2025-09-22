package com.codelab.wp_payroll.infrastructure.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wp-worker")
public interface WorkerFeignClient {

    @GetMapping("/api/v1/worker/{workerId}")
    Response getWorkerById(@PathVariable Long workerId);

}
