package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentDao {

    List<Assignment> readAllByOrderId(Long orderId);

    void update(Assignment createdAssignment);

    void create(Assignment assignment);

    Optional<Assignment> read(Long assignmentId);

}
