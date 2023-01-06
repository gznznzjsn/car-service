package com.gznznzjsn.carservice.domain.entity;

import lombok.*;

@Data
@Builder
public class Consumable {
    private Long id;
    private ConsumableType consumableType;
    private long availableQuantity;
}
