package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Order;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrder(Long orderId);

    Order sendOrder(Long orderId);

    Order updateOrder(Order order);

//    static LocalDateTime calculateFinishTime(Order order) {
//        return order.getAssignments()
//                .stream()
//                .map(a -> a.getStartTime().plusHours(a.getTasks()
//                        .stream()
//                        .map(Task::getDuration).
//                        reduce(0, Integer::sum)))
//                .max((d1, d2) -> d1.isAfter(d2) ? +1 : -1).orElseThrow(() -> new NotEnoughResources("Assignment is empty!"));
//    }
}
