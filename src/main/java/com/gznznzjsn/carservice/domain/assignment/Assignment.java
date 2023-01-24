package com.gznznzjsn.carservice.domain.assignment;

import com.gznznzjsn.carservice.domain.Employee;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.Task;
import com.gznznzjsn.carservice.domain.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    private Long id;
    private Order order;
    private AssignmentStatus status;
    private Specialization specialization;
    private LocalDateTime startTime;
    private BigDecimal finalCost;
    private Employee employee;
    private List<Task> tasks;
    private String userCommentary;
    private String employeeCommentary;

}
