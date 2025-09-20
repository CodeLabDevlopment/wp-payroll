package com.codelab.wp_payroll.domain.model;

public record Payment(
        String name,
        Double dailyIncome,
        Integer daysWorked,
        Double totalPayment
) {

}
