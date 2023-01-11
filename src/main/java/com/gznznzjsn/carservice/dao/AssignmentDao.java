package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Assignment;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

public interface AssignmentDao {


    @SneakyThrows
    List<Assignment> readAssignments(Long orderId);

    void updateAssignment(Assignment createdAssignment);

    void createAssignment(Assignment assignment);

    Optional<Assignment> readAssignment(Long assignmentId);
}
