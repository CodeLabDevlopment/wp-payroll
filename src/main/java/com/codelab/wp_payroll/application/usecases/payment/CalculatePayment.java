package com.codelab.wp_payroll.application.usecases.payment;

import com.codelab.wp_payroll.application.gateway.ClientGateway;
import com.codelab.wp_payroll.domain.exception.DaysException;
import com.codelab.wp_payroll.domain.model.Payment;
import com.codelab.wp_payroll.domain.model.Worker;

public record CalculatePayment(ClientGateway clientGateway) {

    public Payment execute(long workerId, int daysWorked) {
        if (daysWorked <= 0) {
            throw new DaysException("Days worked must be greater than zero");
        }

        Worker worker = clientGateway.getWorkerById(workerId);
        
        Double totalPayment = worker.dailyIncome() * daysWorked;

        return new Payment(worker.name(), worker.dailyIncome(), daysWorked, totalPayment);
    }

}
