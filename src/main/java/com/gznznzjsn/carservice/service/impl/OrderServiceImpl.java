package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.OrderDao;
import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.domain.carservice.order.OrderStatus;
import com.gznznzjsn.carservice.domain.exception.IllegalActionException;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.NOT_SENT);
        orderDao.createOrder(order);
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderDao.readOrder(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id = " + orderId + " doesn't exist!"));
    }

    @Override
    @Transactional
    public Order sendOrder(Long orderId) {
        Order order = getOrder(orderId);
        if (!order.getStatus().equals(OrderStatus.NOT_SENT)) {
            throw new IllegalActionException("You can't send order with id = " + order.getId() + ", because it's already sent!");
        }
        Order orderToUpdate = Order.builder()
                .id(orderId)
                .status(OrderStatus.UNDER_CONSIDERATION)
                .build();
        return updateOrder(orderToUpdate);


    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        Order orderFromRepository = getOrder(order.getId());

        if (order.getStatus() != null) {
            orderFromRepository.setStatus(order.getStatus());
        }
        if (order.getArrivalTime() != null) {
            orderFromRepository.setArrivalTime(order.getArrivalTime());
        }
        orderDao.updateOrder(orderFromRepository);

        return orderFromRepository;
    }
}
