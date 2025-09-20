package com.codelab.wp_payroll.infrastructure.web.routes;

import com.codelab.wp_payroll.domain.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/payments")
public interface PaymentsRoutes {

    @GetMapping("/{workerId}/days/{days}")
    ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days);

}
