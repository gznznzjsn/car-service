package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.domain.carservice.User;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.UserService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentListMapper;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import jakarta.validation.Valid;
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
    private final AssignmentListMapper assignmentListMapper;

    @PostMapping("/{userId}/orders")
    public @Valid OrderDto.Response.Create createOrder(
            @PathVariable Long userId,
            @Valid @RequestBody OrderDto.Request.Create orderDto
    ) {
        Order order = orderMapper.toEntity(orderDto);
        User user = userService.getUser(userId);
        order.setUser(user);
        Order createdOrder = orderService.createOrder(order);
        return orderMapper.toCreateDto(createdOrder);

    }

    @GetMapping("/{userId}/orders/{orderId}")
    public @Valid OrderDto.Response.Read getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        //гыукшв
        Order order = orderService.getOrder(orderId);
        return orderMapper.toReadDto(order);

    }


    @PostMapping("/{userId}/orders/{orderId}")
    public @Valid AssignmentDto.Response.Create createAssignment(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @Valid @RequestBody AssignmentDto.Request.Create assignmentDto
    ) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Order order = orderService.getOrder(orderId);
        assignment.setOrder(order);
        //todo check for unique
        // check userid
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return assignmentMapper.toCreateDto(createdAssignment);
    }


    @PostMapping("/{userId}/orders/{orderId}/send")
    public List<AssignmentDto.Response.@Valid Sent> sendAssignmentsAndOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        // check userid
        List<Assignment> sentAssignments = assignmentService.sendAssignmentsAndOrder(orderId);
        return assignmentListMapper.toSentDto(sentAssignments);
    }


}
