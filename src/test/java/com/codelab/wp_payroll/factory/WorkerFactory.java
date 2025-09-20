package com.codelab.wp_payroll.factory;

import com.codelab.wp_payroll.domain.model.Worker;

public final class WorkerFactory {

    public static Worker createWorker() {
        return new Worker(1L, "John Doe", 100.0);
    }

}
