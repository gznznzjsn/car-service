package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @PostMapping("/users/{id}/orders")
    public Order createOrder(
            @PathVariable("id") Long userId,
            @RequestBody OrderDto.Request.Create orderDto
    ) {
        Order order = orderMapper.toEntity(orderDto);
        Order returnedOrder = orderService.createOrder(userId, order);
        return returnedOrder;
//        return orderMapper.toDto(returnedOrder);
    }
}
