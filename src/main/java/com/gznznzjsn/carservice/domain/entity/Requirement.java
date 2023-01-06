package com.gznznzjsn.carservice.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Requirement {
    private Long id;
    private ConsumableType consumableType;
    private long requiredQuantity;
}
