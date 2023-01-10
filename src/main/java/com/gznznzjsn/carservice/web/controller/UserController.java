package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @PostMapping("/{id}/orders")
    public OrderDto.Response.ReadPrecalculated createOrder(
            @PathVariable("id") Long userId,
            @RequestBody OrderDto.Request.Create orderDto
    ) {
        Order order = orderMapper.toEntity(orderDto);
        Order returnedOrder = orderService.createOrder(userId, order);
        return orderMapper.toDto(returnedOrder);
    }



//    @GetMapping("/{id}/orders")
//    public List<OrderDto.Response.ReadReduced> readOrders(
//            @PathVariable("id") Long userId
//    ) {
//        List<Order> orders = orderService.readOrders(userId);
//        return
//    }
}
