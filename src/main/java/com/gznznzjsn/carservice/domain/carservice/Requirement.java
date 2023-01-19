package com.gznznzjsn.carservice.domain.carservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {

    private Long id;
    private Task task;
    private ConsumableType consumableType;
    private long requiredQuantity;

}
