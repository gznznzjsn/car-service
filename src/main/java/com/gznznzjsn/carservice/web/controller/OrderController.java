package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import com.gznznzjsn.carservice.web.dto.group.OnCreateOrder;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentListMapper;
import com.gznznzjsn.carservice.web.dto.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car-service/users/{userId}/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final AssignmentService assignmentService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final AssignmentListMapper assignmentListMapper;

    @PreAuthorize("@securityChecks.hasOrder(#userId,#orderId)")
    @PostMapping("/{orderId}/send")
    @Operation(
            tags = {"Orders"},
            summary = "Send order and attached assignments",
            description = "Enter ids of user and his order, which you want to send",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order successfully sent",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderDto.class))
                    )
            },
            parameters = {
                    @Parameter(name = "userId", example = "1"),
                    @Parameter(name = "orderId", example = "1")
            }
    )
    public List<AssignmentDto> sendWithAssignments(@PathVariable Long orderId, @PathVariable Long userId) {
        List<Assignment> sentAssignments = assignmentService.sendWithOrder(orderId);
        return assignmentListMapper.toDto(sentAssignments);
    }

    @PreAuthorize("@securityChecks.hasUser(#userId)")
    @PostMapping
    @Operation(
            tags = {"Orders"},
            summary = "Create order",
            description = "Enter id of user and fulfill information about order to create it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order successfully created",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderDto.class))
                    )
            },
            parameters = {
                    @Parameter(name = "userId", example = "1")
            }
    )
    public OrderDto create(@Validated(OnCreateOrder.class) @RequestBody OrderDto orderDto, @PathVariable Long userId) {
        Order order = orderMapper.toEntity(orderDto);
        order.setUser(User.builder()
                .id(userId)
                .build());
        Order createdOrder = orderService.create(order);
        return orderMapper.toDto(createdOrder);

    }

    @PreAuthorize("@securityChecks.hasOrder(#userId,#orderId)")
    @GetMapping("/{orderId}")
    @Operation(
            tags = {"Orders"},
            summary = "Get order",
            description = "Enter ids of user and his order, you want to obtain",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order successfully returned",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderDto.class))
                    )
            },
            parameters = {
                    @Parameter(name = "userId", example = "1"),
                    @Parameter(name = "orderId", example = "1")
            }
    )
    public OrderDto get(@PathVariable Long orderId, @PathVariable Long userId) {
        Order order = orderService.get(orderId);
        return orderMapper.toDto(order);
    }

}
