package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentListMapper;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final AssignmentService assignmentService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final AssignmentListMapper assignmentListMapper;



    @PostMapping("/{orderId}/send")
    public List<AssignmentDto.Response.@Valid Sent> sendAssignmentsAndOrder(
            @PathVariable Long orderId
    ) {
        List<Assignment> sentAssignments = assignmentService.sendAssignmentsAndOrder(orderId);
        return assignmentListMapper.toSentDto(sentAssignments);
    }

    @PostMapping
    public @Valid OrderDto.Response.Create createOrder(
            @Valid @RequestBody OrderDto.Request.Create orderDto
    ) {
        Order order = orderMapper.toEntity(orderDto);
        Order createdOrder = orderService.createOrder(order);
        return orderMapper.toCreateDto(createdOrder);

    }

    @GetMapping("/{orderId}")
    public @Valid OrderDto.Response.Read getOrder(
            @PathVariable Long orderId
    ) {
        Order order = orderService.getOrder(orderId);
        return orderMapper.toReadDto(order);
    }
}
