package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Order;

public interface OrderDao {
    Order createOrder(Long userId, Order order);
}
