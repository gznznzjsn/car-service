package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Assignment;

import java.time.LocalDateTime;

public interface AssignmentService {


    Assignment createAssignment(Long orderId, LocalDateTime arrivalTime, Assignment assignment);
}
