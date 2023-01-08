package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    @PostMapping("/users/{id}/orders")
    public void createOrder(
            @PathVariable("id") String user_id,
            @RequestBody OrderDto.Request.Create orderDto
    ) {
        System.out.println(orderDto.requestedAssignments().get(0).requestedTasks().get(0).id());
    }
}
