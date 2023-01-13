package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;

import java.math.BigDecimal;
import java.util.List;

public interface AssignmentService {

    static BigDecimal calculateTotalCost(Assignment assignment) {
        return assignment.getTasks().stream()
                .map(t -> t.getCostPerHour().multiply(BigDecimal.valueOf(t.getDuration())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Assignment create(Assignment assignment);

    List<Assignment> sendWithOrder(Long orderId);

    Assignment update(Assignment assignment);

    Assignment get(Long assignmentId);

    List<Assignment> getAllByOrderId(Long orderId);

}
