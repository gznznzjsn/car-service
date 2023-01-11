package com.gznznzjsn.carservice.domain.carservice;

import com.gznznzjsn.carservice.domain.carservice.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {

    private Long id;
    private User user;
    private OrderStatus status;
    private LocalDateTime arrivalTime;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

}
