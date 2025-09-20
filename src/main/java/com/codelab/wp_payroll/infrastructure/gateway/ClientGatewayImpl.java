package com.codelab.wp_payroll.infrastructure.gateway;

import com.codelab.wp_payroll.application.gateway.ClientGateway;
import com.codelab.wp_payroll.domain.exception.WorkerConnectionException;
import com.codelab.wp_payroll.domain.exception.WorkerNotFound;
import com.codelab.wp_payroll.domain.model.Worker;
import com.codelab.wp_payroll.infrastructure.client.WorkerFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
            validateStatus(response.status());
            return objectMapper.readValue(response.body().asInputStream(), Worker.class);
        } catch (IOException ex) {
            log.error("Error while fetching worker with id: {}", workerId);
            throw new RuntimeException("Error while fetching worker with id: " + workerId, ex);
        }
    }

    private void validateStatus(int httpStatus) {
        switch (httpStatus) {
            case 404 -> throw new WorkerNotFound("Worker not found");
            case 500 -> throw new WorkerConnectionException("Worker service is unavailable");
        }
    }

}
