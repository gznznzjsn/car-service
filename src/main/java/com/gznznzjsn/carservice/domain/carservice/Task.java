package com.gznznzjsn.carservice.domain.carservice;

import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Task {

    private Long id;
    private String name;
    private int duration;
    private BigDecimal costPerHour;

    private Specialization requiredSpecialization;

    private List<Requirement> requiredConsumables;

}
