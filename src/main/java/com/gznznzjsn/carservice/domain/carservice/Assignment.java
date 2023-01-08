package com.gznznzjsn.carservice.domain.carservice;

import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Assignment {
    private Long id;
    private Specialization specialization;
    private Employee employee;
    private List<Task> tasks;
    private String commentary;
}
