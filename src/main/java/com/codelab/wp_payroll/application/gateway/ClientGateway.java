package com.codelab.wp_payroll.application.gateway;

import com.codelab.wp_payroll.domain.model.Worker;

public interface ClientGateway {
    Worker getWorkerById(Long workerId);
}
