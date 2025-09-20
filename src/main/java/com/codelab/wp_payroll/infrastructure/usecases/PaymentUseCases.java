package com.codelab.wp_payroll.infrastructure.usecases;

import com.codelab.wp_payroll.application.gateway.ClientGateway;
import com.codelab.wp_payroll.application.usecases.payment.CalculatePayment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentUseCases {

    @Bean
    public CalculatePayment calculatePayment(ClientGateway clientGateway) {
        return new CalculatePayment(clientGateway);
    }

}
