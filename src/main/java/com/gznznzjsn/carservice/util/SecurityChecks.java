package com.gznznzjsn.carservice.util;

import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityChecks {

    private final OrderService orderService;
    private final UserService userService;
    private final AssignmentService assignmentService;

    public boolean hasAssignment(Long userId, Long orderId, Long assignmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assignment assignment = assignmentService.get(assignmentId);
        return authentication.getName().equals(assignment.getOrder().getUser().getEmail())
               && assignment.getOrder().getId().equals(orderId)
               && assignment.getOrder().getUser().getId().equals(userId);
    }

    public boolean hasOrder(Long userId, Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Order order = orderService.get(orderId);
        return authentication.getName().equals(order.getUser().getEmail())
               && order.getUser().getId().equals(userId);
    }

    public boolean hasUser(Long userId) {
        User user = userService.get(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(user.getEmail());
    }

}
