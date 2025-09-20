package com.codelab.wp_payroll.domain.exception;

public class WorkerNotFound extends RuntimeException {
    public WorkerNotFound(String message) {
        super(message);
    }
}
