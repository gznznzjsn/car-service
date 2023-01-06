package com.gznznzjsn.carservice.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ConsumableType {
    private Long id;
    private String name;
    private BigDecimal cost;
}
