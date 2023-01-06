package com.gznznzjsn.carservice.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Assignment {
    private Long id;
    private Employee employee;
    private Task task;
    private String commentary;
}
