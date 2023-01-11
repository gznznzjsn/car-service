package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.domain.carservice.User;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.UserService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//todo everything to dto
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final OrderMapper orderMapper;

    private final AssignmentMapper assignmentMapper;
    private final AssignmentService assignmentService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/{userId}/orders")
    public Order createOrder(
            @PathVariable Long userId,
            @RequestBody OrderDto.Request.Create orderDto
    ) {
        Order order = orderMapper.toEntity(orderDto);
        User user = userService.getUser(userId);
        order.setUser(user);
        Order createdOrder = orderService.createOrder(order);
        return createdOrder;

    }

    @GetMapping("/{userId}/orders/{orderId}")
    public Order getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        //гыукшв
        Order order = orderService.getOrder(orderId);
        return order;

    }


    @PostMapping("/{userId}/orders/{orderId}")
    public Assignment createAssignment(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @RequestBody AssignmentDto.Request.Create assignmentDto
    ) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Order order = orderService.getOrder(orderId);
        assignment.setOrder(order);
        //todo check for unique
        // check userid
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return createdAssignment;
    }


    @PostMapping("/{userId}/orders/{orderId}/send")
    public List<Assignment> sendAssignmentsAndOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        // check userid
        List<Assignment> sentAssignments = assignmentService.sendAssignmentsAndOrder(orderId);
        return sentAssignments;
    }


}
