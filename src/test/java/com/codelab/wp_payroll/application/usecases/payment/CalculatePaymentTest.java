package com.codelab.wp_payroll.application.usecases.payment;

import com.codelab.wp_payroll.application.gateway.ClientGateway;
import com.codelab.wp_payroll.domain.exception.DaysException;
import com.codelab.wp_payroll.domain.model.Payment;
import com.codelab.wp_payroll.domain.model.Worker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatePaymentTest {

    @Mock
    ClientGateway clientGateway;
    @InjectMocks
    CalculatePayment calculatePayment;

    @Test
    @DisplayName("Should calculate payment")
    void case01() {
        Worker worker = new Worker(1L, "John Doe", 100.0);

        when(clientGateway.getWorkerById(1L)).thenReturn(worker);

        Payment payment = calculatePayment.execute(1L, 21);

        assertAll("Assertions",
                () -> assertEquals(worker.name(), payment.name()),
                () -> assertEquals(worker.dailyIncome(), payment.dailyIncome()),
                () -> assertEquals(21, payment.daysWorked()),
                () -> assertEquals(2100.0, payment.totalPayment())
        );
        verify(clientGateway, times(1)).getWorkerById(1L);
    }

    @Test
    @DisplayName("Should throw exception when days worked is zero")
    void case02() {
        assertThrows(DaysException.class,
                () -> calculatePayment.execute(1L, 0)
        );
        verify(clientGateway, never()).getWorkerById(1L);
    }

}