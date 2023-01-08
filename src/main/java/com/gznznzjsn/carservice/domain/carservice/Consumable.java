package com.gznznzjsn.carservice.domain.carservice;

import lombok.*;

@Data
@Builder
public class Consumable {
    private Long id;
    private ConsumableType consumableType;
    private long availableQuantity;
}
