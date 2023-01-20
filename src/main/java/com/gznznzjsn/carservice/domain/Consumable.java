package com.gznznzjsn.carservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consumable {

    private Long id;
    private ConsumableType consumableType;
    private long availableQuantity;

}
