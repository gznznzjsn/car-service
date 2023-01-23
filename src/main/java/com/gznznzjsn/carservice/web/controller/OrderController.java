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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId}/orders")
public class OrderController {

    private final AssignmentService assignmentService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final AssignmentListMapper assignmentListMapper;

    @PreAuthorize("@securityChecks.hasOrder(#userId,#orderId)")
    @PostMapping("/{orderId}/send")
    public List<AssignmentDto> sendWithAssignments(@PathVariable Long orderId, @PathVariable Long userId) {
        List<Assignment> sentAssignments = assignmentService.sendWithOrder(orderId);
        return assignmentListMapper.toDto(sentAssignments);
    }

    @PreAuthorize("@securityChecks.hasUser(#userId) && orderDto.user().id().equals(#userId)")
    @PostMapping
    public OrderDto create(@Validated(OnCreateOrder.class) @RequestBody OrderDto orderDto, @PathVariable Long userId) {
        Order order = orderMapper.toEntity(orderDto);
        Order createdOrder = orderService.create(order);
        return orderMapper.toDto(createdOrder);

    }

    @PreAuthorize("@securityChecks.hasOrder(#userId,#orderId)")
    @GetMapping("/{orderId}")
    public OrderDto get(@PathVariable Long orderId, @PathVariable Long userId) {
        Order order = orderService.get(orderId);
        return orderMapper.toDto(order);
    }

}
