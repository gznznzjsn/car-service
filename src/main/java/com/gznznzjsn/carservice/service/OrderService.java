package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Order;

public interface OrderService {
    Order createOrder(Long userId, Order order);
}
