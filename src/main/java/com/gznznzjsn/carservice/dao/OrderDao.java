package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.order.Order;

import java.util.Optional;

public interface OrderDao {

    void create(Order order);

    Optional<Order> read(Long orderId);

    void update(Order order);

}
