package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.order.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface OrderDao {

    void create(Order order);

    Optional<Order> findById(Long orderId);

    void update(Order order);

}
