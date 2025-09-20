package com.codelab.wp_payroll.domain.model;

public record Worker(
        Long id,
        String name,
        Double dailyIncome
) {

}
