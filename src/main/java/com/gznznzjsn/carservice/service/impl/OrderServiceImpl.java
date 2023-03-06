package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.OrderDao;
import com.gznznzjsn.carservice.domain.exception.NotEnoughResourcesException;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.order.OrderStatus;
import com.gznznzjsn.carservice.domain.exception.IllegalActionException;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserService userService;
    private final WebClient.Builder webClientBuilder;

    @Override
    @Transactional
    public Order create(Order order) {
        if (order.getUser() == null) {
            throw new IllegalActionException("You can't create order without user!");
        }
        User user = userService.get(order.getUser().getId());
        //todo
//        Boolean isAcceptable = webClientBuilder.build().get().uri("http://inventory-service/api/inventory")
//                .retrieve()
//                .bodyToMono(Boolean.class)
//                .block();
//        if (Boolean.FALSE.equals(isAcceptable)) {
//            throw new NotEnoughResourcesException("We are out of order forms!)))");
//        }
        Order orderToCreate = Order.builder()
                .status(OrderStatus.NOT_SENT)
                .user(user)
                .arrivalTime(order.getArrivalTime())
                .build();
        orderDao.create(orderToCreate);
        return orderToCreate;
    }

    @Override
    @Transactional(readOnly = true)
    public Order get(Long orderId) {
        return orderDao.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id = " + orderId + " doesn't exist!"));
    }

    @Override
    @Transactional
    public Order send(Long orderId) {
        Order order = get(orderId);
        if (!order.getStatus().equals(OrderStatus.NOT_SENT)) {
            throw new IllegalActionException("You can't send order with id = " + order.getId() + ", because it's already sent!");
        }
        Order orderToUpdate = Order.builder()
                .id(orderId)
                .status(OrderStatus.UNDER_CONSIDERATION)
                .build();
        return update(orderToUpdate);
    }

    @Override
    @Transactional
    public Order update(Order order) {
        Order orderFromRepository = get(order.getId());
        if (order.getStatus() != null) {
            orderFromRepository.setStatus(order.getStatus());
        }
        if (order.getArrivalTime() != null) {
            orderFromRepository.setArrivalTime(order.getArrivalTime());
        }
        orderDao.update(orderFromRepository);
        return orderFromRepository;
    }

}
