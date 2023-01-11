package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Order;

import java.util.Optional;

public interface OrderDao {
    void createOrder(Order order);

    Optional<Order> readOrder(Long orderId);

    void updateOrder(Order order);
}
