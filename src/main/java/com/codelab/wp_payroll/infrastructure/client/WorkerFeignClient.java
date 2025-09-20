package com.codelab.wp_payroll.infrastructure.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "worker-service", url = "${services.worker.url}")
public interface WorkerFeignClient {

    @GetMapping("{workerId}")
    Response getWorkerById(@PathVariable Long workerId);

}
