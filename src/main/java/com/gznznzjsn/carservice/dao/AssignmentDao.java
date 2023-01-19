package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AssignmentDao {

    List<Assignment> readAllByOrderId(Long orderId);

    void update(Assignment createdAssignment);

    void create(Assignment assignment);

    void createTasks(Assignment assignment);

    Optional<Assignment> read(Long assignmentId);

}
