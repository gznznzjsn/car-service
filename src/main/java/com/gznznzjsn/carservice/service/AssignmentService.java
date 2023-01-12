package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AssignmentService {

    static BigDecimal calculateTotalCost(Assignment assignment) {
        return assignment.getTasks().stream()
                .map(t -> t.getCostPerHour().multiply(BigDecimal.valueOf(t.getDuration())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Assignment createAssignment(Assignment assignment);

    List<Assignment> sendAssignmentsAndOrder(Long orderId);

    Assignment updateAssignment(Assignment assignment);

    Assignment getAssignment(Long assignmentId);

    List<Assignment> getAssignments(Long orderId);
}
