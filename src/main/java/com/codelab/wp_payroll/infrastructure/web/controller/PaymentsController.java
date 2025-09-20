package com.codelab.wp_payroll.infrastructure.web.controller;

import com.codelab.wp_payroll.application.usecases.payment.CalculatePayment;
import com.codelab.wp_payroll.domain.model.Payment;
import com.codelab.wp_payroll.infrastructure.web.routes.PaymentsRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentsController implements PaymentsRoutes {

    private final CalculatePayment calculatePayment;

    @Override
    public ResponseEntity<Payment> getPayment(Long workerId, Integer days) {
        Payment payment = this.calculatePayment.execute(workerId, days);
        return ResponseEntity.ok(payment);
    }

}
