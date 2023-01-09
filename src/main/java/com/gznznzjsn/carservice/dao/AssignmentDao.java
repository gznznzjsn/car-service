package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Assignment;

import java.time.LocalDateTime;

public interface AssignmentDao {

    Assignment createAssignment(Long orderId, LocalDateTime arrivalTime, Assignment assignment);
}
