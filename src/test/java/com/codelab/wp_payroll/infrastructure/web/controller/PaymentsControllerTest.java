package com.codelab.wp_payroll.infrastructure.web.controller;

import com.codelab.wp_payroll.application.gateway.ClientGateway;
import com.codelab.wp_payroll.application.usecases.payment.CalculatePayment;
import com.codelab.wp_payroll.domain.model.Payment;
import com.codelab.wp_payroll.domain.model.Worker;
import com.codelab.wp_payroll.factory.WorkerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PaymentsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CalculatePayment calculatePayment;
    @MockitoBean
    ClientGateway clientGateway;

    @Test
    @DisplayName("Should get payment when GET /payments/{workerId}/days/{days} is called")
    void case01() throws Exception {
        Worker worker = WorkerFactory.createWorker();
        Payment payment = new Payment(worker.name(), worker.dailyIncome(), 21, 2100.0);

        when(clientGateway.getWorkerById(1L)).thenReturn(worker);
        when(calculatePayment.execute(1L, 21)).thenReturn(payment);

        mockMvc.perform(get("/api/v1/payments/{workerId}/days/{days}", 1L, 21))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(worker.name()))
                .andExpect(jsonPath("$.dailyIncome").value(worker.dailyIncome()))
                .andExpect(jsonPath("$.daysWorked").value(21))
                .andExpect(jsonPath("$.totalPayment").value(2100.0));

        verify(calculatePayment, times(1)).execute(1L, 21);
    }

}