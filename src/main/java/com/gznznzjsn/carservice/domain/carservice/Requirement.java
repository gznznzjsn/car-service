package com.gznznzjsn.carservice.domain.carservice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Requirement {

    private Long id;
    private Task task;
    private ConsumableType consumableType;
    private long requiredQuantity;

}
