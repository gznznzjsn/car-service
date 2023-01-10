package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Assignment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AssignmentService {


    Assignment createAssignment(Long orderId, LocalDateTime arrivalTime, Assignment assignment);

    static BigDecimal calculateTotalCost(Assignment assignment) {
        return assignment.getTasks().stream()
                .map(t -> t.getCostPerHour().multiply(BigDecimal.valueOf(t.getDuration())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Assignment acceptAssignment(Assignment assignment);
}
