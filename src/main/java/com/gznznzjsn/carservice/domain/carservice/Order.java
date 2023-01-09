package com.gznznzjsn.carservice.domain.carservice;

import com.gznznzjsn.carservice.domain.carservice.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {

    private Long id;
    private OrderStatus status;
    private LocalDateTime arrivalTime;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private List<Assignment> assignments;

}
