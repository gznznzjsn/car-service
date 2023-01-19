package com.gznznzjsn.carservice.domain.carservice.order;

import com.gznznzjsn.carservice.domain.carservice.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private User user;
    private OrderStatus status;
    private LocalDateTime arrivalTime;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

}
