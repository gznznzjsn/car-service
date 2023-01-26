package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.OrderDao;
import com.gznznzjsn.carservice.domain.exception.IllegalActionException;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.order.OrderStatus;
import com.gznznzjsn.carservice.domain.user.Role;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void createWithoutUser() {
        Order givenOrder = Order.builder()
                .user(null)
                .build();

        assertThrows(IllegalActionException.class, () -> orderService.create(givenOrder));

        verify(orderDao, never()).create(any());
    }

    @Test
    public void createWithIncorrectUser() {
        Order givenOrder = Order.builder()
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();
        when(userService.get(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> orderService.create(givenOrder));

        verify(orderDao, never()).create(any());
    }

    @Test
    public void createCorrect() {
        Order givenOrder = Order.builder()
                .user(User.builder()
                        .id(1L)
                        .build())
                .arrivalTime(LocalDateTime.MAX)
                .build();
        User user = User.builder()
                .id(1L)
                .password("pass")
                .role(Role.USER)
                .name("userName")
                .email("e@mail.com").build();
        Order orderToCreate = Order.builder()
                .status(OrderStatus.NOT_SENT)
                .user(user)
                .arrivalTime(givenOrder.getArrivalTime())
                .build();
        doAnswer(invocation -> {
            ((Order) invocation.getArgument(0)).setId(1L);
            return null;
        }).when(orderDao).create(any());
        when(userService.get(1L)).thenReturn(user);

        Order returnedOrder = orderService.create(givenOrder);

        verify(userService).get(1L);
        verify(orderDao).create(any());
        orderToCreate.setId(1L);
        orderToCreate.setUser(user);
        assertEquals(orderToCreate, returnedOrder);
    }

    @Test
    public void getNonExisting() {
        when(orderDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.get(1L));

        verify(orderDao).findById(1L);
    }

    @Test
    public void getExisting() {
        Order order = Order.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .password("pass")
                        .role(Role.USER)
                        .name("userName")
                        .email("e@mail.com")
                        .build())
                .status(OrderStatus.DONE)
                .arrivalTime(LocalDateTime.MAX)
                .createdAt(LocalDateTime.MAX)
                .finishedAt(LocalDateTime.MAX)
                .build();
        when(orderDao.findById(1L)).thenReturn(Optional.of(order));

        Order returnedOrder = orderService.get(1L);

        verify(orderDao).findById(1L);
        assertEquals(order, returnedOrder);
    }

    @Test
    public void sendNonExisting() {
        OrderService spyOS = spy(orderService);
        doThrow(ResourceNotFoundException.class).when(spyOS).get(1L);

        assertThrows(ResourceNotFoundException.class, () -> spyOS.send(1L));

        verify(spyOS).get(anyLong());
        verify(spyOS, never()).update(any());
    }

    @Test
    public void sendAlreadySent() {
        OrderService spyOS = spy(orderService);
        doReturn(Order.builder().status(OrderStatus.UNDER_CONSIDERATION).build()).when(spyOS).get(1L);

        assertThrows(IllegalActionException.class, () -> spyOS.send(1L));

        verify(spyOS).get(anyLong());
        verify(spyOS, never()).update(any());
    }

    @Test
    public void sendCorrect() {
        OrderService spyOS = spy(orderService);
        Order order = Order.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .password("pass")
                        .role(Role.USER)
                        .name("userName")
                        .email("e@mail.com")
                        .build())
                .status(OrderStatus.NOT_SENT)
                .arrivalTime(LocalDateTime.MAX)
                .createdAt(LocalDateTime.MAX)
                .finishedAt(LocalDateTime.MAX)
                .build();
        doReturn(order).when(spyOS).get(1L);
        doReturn(order).when(spyOS).update(Order.builder().id(1L)
                .status(OrderStatus.UNDER_CONSIDERATION)
                .build());

        Order returnedOrder = spyOS.send(1L);

        verify(spyOS).get(anyLong());
        verify(spyOS).update(Order.builder().id(1L)
                .status(OrderStatus.UNDER_CONSIDERATION)
                .build());
        order.setStatus(OrderStatus.UNDER_CONSIDERATION);
        assertEquals(order, returnedOrder);
    }

    @Test
    public void updateNonExisting() {
        Order order = Order.builder().id(1L).build();
        OrderService spyOS = spy(orderService);
        doThrow(ResourceNotFoundException.class).when(spyOS).get(1L);

        assertThrows(ResourceNotFoundException.class, () -> spyOS.update(order));

        verify(spyOS).get(anyLong());
        verifyNoInteractions(orderDao);
    }

    @Test
    public void updateFull() {
        Order order = Order.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .password("pass")
                        .role(Role.USER)
                        .name("userName")
                        .email("e@mail.com")
                        .build())
                .status(OrderStatus.NOT_SENT)
                .arrivalTime(LocalDateTime.MAX)
                .createdAt(LocalDateTime.MAX)
                .finishedAt(LocalDateTime.MAX)
                .build();
        Order orderToUpdate = Order.builder()
                .id(1L)
                .status(order.getStatus())
                .arrivalTime(order.getArrivalTime())
                .build();
        OrderService spyOS = spy(orderService);
        doReturn(Order.builder().id(1L).build()).when(spyOS).get(1L);

        Order returnedOrder = spyOS.update(order);

        verify(spyOS).get(1L);
        verify(orderDao).update(orderToUpdate);
        assertEquals(orderToUpdate, returnedOrder);
    }

    @Test
    public void updateNone() {
        Order order = Order.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .password("pass")
                        .role(Role.USER)
                        .name("userName")
                        .email("e@mail.com")
                        .build())
                .createdAt(LocalDateTime.MAX)
                .finishedAt(LocalDateTime.MAX)
                .build();
        Order orderToUpdate = Order.builder()
                .id(1L)
                .build();
        OrderService spyOS = spy(orderService);
        doReturn(Order.builder().id(1L).build()).when(spyOS).get(1L);

        Order returnedOrder = spyOS.update(order);

        verify(spyOS).get(1L);
        verify(orderDao).update(orderToUpdate);
        assertEquals(orderToUpdate, returnedOrder);
    }
}