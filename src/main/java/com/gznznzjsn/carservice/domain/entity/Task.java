package com.gznznzjsn.carservice.domain.entity;

import com.gznznzjsn.carservice.domain.enums.Specialization;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Task {

    private final long id;
    private String name;
    private int duration;
    private long cost_per_hour;

    private Specialization requiredSpecialization;

    private List<Requirement> requiredConsumables;

}
