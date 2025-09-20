package com.codelab.wp_payroll.infrastructure.gateway;

import com.codelab.wp_payroll.application.gateway.ClientGateway;
import com.codelab.wp_payroll.domain.model.Worker;
import com.codelab.wp_payroll.infrastructure.client.WorkerFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClientGatewayImpl implements ClientGateway {

    private final WorkerFeignClient workerFeignClient;
    private final ObjectMapper objectMapper;

    public ClientGatewayImpl(WorkerFeignClient workerFeignClient, ObjectMapper objectMapper) {
        this.workerFeignClient = workerFeignClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Worker getWorkerById(Long workerId) {
        log.debug("Fetching worker with id: {}", workerId);
        Response response = this.workerFeignClient.getWorkerById(workerId);

        try {
            if (response.status() != 200) {
                log.error("Failed to fetch worker. Status code: {}", response.status());
                throw new RuntimeException("Failed to fetch worker with id: " + workerId);
            }

            return objectMapper.readValue(response.body().asInputStream(), Worker.class);
        } catch (Exception e) {
            log.error("Error while fetching worker with id: {}", workerId, e);
            throw new RuntimeException("Error while fetching worker with id: " + workerId, e);
        }
    }

}
