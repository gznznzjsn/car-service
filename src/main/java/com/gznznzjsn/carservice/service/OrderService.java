package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.order.Order;

public interface OrderService {

    Order create(Order order);

    Order get(Long orderId);

    Order send(Long orderId);

    Order update(Order order);

}
