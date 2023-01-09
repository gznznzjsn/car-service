package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.OrderDao;
import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.domain.carservice.enums.OrderStatus;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Override
    @SneakyThrows
    public Order createOrder(Long userId, Order order) {
        String INSERT_ORDER_QUERY = """
                INSERT INTO orders (status_id,user_id, created_at,arrival_time)
                VALUES ((SELECT status_id FROM statuses WHERE value=?),?,?,?);
                """;
        order.setStatus(OrderStatus.UNDER_CONSIDERATION);
        order.setCreatedAt(LocalDateTime.now());
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, order.getStatus().name());
            stmt.setLong(2, userId);
            stmt.setObject(3, order.getCreatedAt());
            stmt.setObject(4, order.getArrivalTime());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            order.setId(keys.getLong(1));
        }
        return order;
    }
}
