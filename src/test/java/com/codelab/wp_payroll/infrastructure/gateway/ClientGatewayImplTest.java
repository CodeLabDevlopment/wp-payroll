package com.codelab.wp_payroll.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static feign.Request.create;
import static feign.Request.HttpMethod.GET;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.codelab.wp_payroll.domain.exception.WorkerConnectionException;
import com.codelab.wp_payroll.domain.exception.WorkerNotFound;
import com.codelab.wp_payroll.domain.model.Worker;
import com.codelab.wp_payroll.infrastructure.client.WorkerFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.Response.Body;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientGatewayImplTest {

    @Mock
    WorkerFeignClient workerFeignClient;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    ClientGatewayImpl clientGateway;

    Long WORKER_ID = 1L;

    @Test
    @DisplayName("Should return a worker when the worker exists")
    void case01() throws IOException {
        Body mockBody = mock(Body.class);
        InputStream mockInputStream = mock(InputStream.class);

        Worker fakeWorker = new Worker(WORKER_ID, "John Doe", 300.0);

        when(workerFeignClient.getWorkerById(WORKER_ID)).thenReturn(buildResponse(200, mockBody));
        when(mockBody.asInputStream()).thenReturn(mockInputStream);
        when(objectMapper.readValue(mockInputStream, Worker.class)).thenReturn(fakeWorker);

        Worker worker = clientGateway.getWorkerById(WORKER_ID);

        assertNotNull(worker);
        assertEquals(WORKER_ID, worker.id());
        assertEquals("John Doe", worker.name());
    }

    @Test
    @DisplayName("Should throw WorkerNotFound when the worker does not exist")
    void case02() {
        when(workerFeignClient.getWorkerById(WORKER_ID)).thenReturn(buildResponse(404, null));

        assertThrows(WorkerNotFound.class, () -> clientGateway.getWorkerById(WORKER_ID));
    }

    @Test
    @DisplayName("Should throw WorkerConnectionException when there is a connection issue")
    void case03() {
        when(workerFeignClient.getWorkerById(WORKER_ID)).thenReturn(buildResponse(500, null));

        assertThrows(WorkerConnectionException.class, () -> clientGateway.getWorkerById(WORKER_ID));
    }

    @Test
    @DisplayName("Should throw RuntimeException when there is an IOException while reading the response")
    void case04() throws IOException {
        Body mockBody = mock(Body.class);
        InputStream mockInputStream = mock(InputStream.class);

        when(workerFeignClient.getWorkerById(WORKER_ID)).thenReturn(buildResponse(200, mockBody));
        when(mockBody.asInputStream()).thenReturn(mockInputStream);

        when(objectMapper.readValue(mockInputStream, Worker.class)).thenThrow(new IOException("Simulated I/O error"));

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> clientGateway.getWorkerById(WORKER_ID));
        assertEquals("Error while fetching worker with id: " + WORKER_ID, thrownException.getMessage());
    }

    private Response buildResponse(int status, Body body) {
        return Response.builder()
                .status(status)
                .request(create(GET, "", Map.of(), null, null, null))
                .body(body)
                .build();
    }

}