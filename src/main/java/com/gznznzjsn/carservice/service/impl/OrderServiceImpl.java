package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.OrderDao;
import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final AssignmentService assignmentService;

    @Override
    @Transactional
    public Order createOrder(Long userId, Order order) {
        Order createdOrder = orderDao.createOrder(userId, order);
        List<Assignment> createdAssignments = new ArrayList<>();
        order.getAssignments().forEach(a -> {
            Assignment createdAssignment = assignmentService.createAssignment(order.getId(), order.getArrivalTime(),a);
            createdAssignments.add(createdAssignment);
        });
        createdOrder.setAssignments(createdAssignments);
        return createdOrder;
    }
}
