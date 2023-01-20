package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.group.OnCreateOrder;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentListMapper;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final AssignmentService assignmentService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final AssignmentListMapper assignmentListMapper;


    @PostMapping("/{orderId}/send")
    public List<AssignmentDto> sendWithAssignments(@PathVariable Long orderId) {
        List<Assignment> sentAssignments = assignmentService.sendWithOrder(orderId);
        return assignmentListMapper.toDto(sentAssignments);
    }

    @PostMapping
    public OrderDto create(@Validated(OnCreateOrder.class) @RequestBody OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order createdOrder = orderService.create(order);
        return orderMapper.toDto(createdOrder);

    }

    @GetMapping("/{orderId}")
    public OrderDto get(@PathVariable Long orderId) {
        Order order = orderService.get(orderId);
        return orderMapper.toDto(order);
    }

}
