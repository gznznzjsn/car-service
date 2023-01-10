package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.exception.NotEnoughResources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderService {
    Order createOrder(Long userId, Order order);

    static LocalDateTime calculateFinishTime(Order order) {
        return order.getAssignments()
                .stream()
                .map(a -> a.getStartTime().plusHours(a.getTasks()
                        .stream()
                        .map(Task::getDuration).
                        reduce(0, Integer::sum)))
                .max((d1, d2) -> d1.isAfter(d2) ? +1 : -1).orElseThrow(() -> new NotEnoughResources("Assignment is empty!"));
    }
}
