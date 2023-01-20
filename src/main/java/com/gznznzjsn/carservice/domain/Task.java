package com.gznznzjsn.carservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;
    private String name;
    private int duration;
    private BigDecimal costPerHour;
    private Specialization specialization;

}
