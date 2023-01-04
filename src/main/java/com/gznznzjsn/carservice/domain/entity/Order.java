package com.gznznzjsn.carservice.domain.entity;

import com.gznznzjsn.carservice.domain.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {

    private final long id;
    private User customer;

    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private List<Assignment> assignments;

}
