package com.gznznzjsn.carservice.domain.carservice;

import com.gznznzjsn.carservice.domain.carservice.enums.AssignmentStatus;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Assignment {
    private Long id;
    private AssignmentStatus status;
    private Specialization specialization;
    private LocalDateTime startTime;
    private BigDecimal finalCost;
    private Employee employee;
    private List<Task> tasks;
    private String userCommentary;
    private String employeeCommentary;

}
