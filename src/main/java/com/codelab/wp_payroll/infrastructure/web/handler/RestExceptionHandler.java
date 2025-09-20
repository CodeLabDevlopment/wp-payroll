package com.codelab.wp_payroll.infrastructure.web.handler;

import com.codelab.wp_payroll.domain.exception.DaysException;
import com.codelab.wp_payroll.domain.exception.WorkerConnectionException;
import com.codelab.wp_payroll.domain.exception.WorkerNotFound;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DaysException.class)
    public ResponseEntity<Void> handleDaysException(DaysException ex) {
        log.error("DaysException: {}", ex.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(WorkerConnectionException.class)
    public ResponseEntity<Void> handleWorkerConnectionException(WorkerConnectionException ex) {
        log.error("WorkerConnectionException: {}", ex.getMessage());
        return ResponseEntity.status(503).build();
    }

    @ExceptionHandler(WorkerNotFound.class)
    public ResponseEntity<Void> handleWorkerNotFound(WorkerNotFound ex) {
        log.error("WorkerNotFound: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGenericException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(500).build();
    }

}
