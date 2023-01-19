package com.gznznzjsn.carservice.domain.carservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumableType {

    private Long id;
    private String name;
    private BigDecimal cost;

}
