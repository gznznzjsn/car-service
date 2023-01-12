package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.OrderDao;
import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.domain.carservice.User;
import com.gznznzjsn.carservice.domain.carservice.order.OrderStatus;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public void createOrder(Order order) {
        String INSERT_ORDER = """
                INSERT INTO orders ( status_id, arrival_time, created_at,  user_id)
                VALUES ((SELECT status_id FROM statuses WHERE value=?),?,now(),?);
                """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setObject(2, order.getArrivalTime());
            stmt.setLong(3, order.getUser().getId());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            order.setId(keys.getLong(1));
        }


    }

    @Override
    @SneakyThrows
    public Optional<Order> readOrder(Long orderId) {
        String FETCH_BY_ID = """
                SELECT value, arrival_time,created_at,finished_at, user_id, name
                FROM orders JOIN statuses USING (status_id)
                JOIN users USING (user_id)
                WHERE order_id = ?;
                """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID)) {
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            Timestamp finishedAt = rs.getTimestamp(4);
            return Optional.of(Order.builder()
                    .id(orderId)
                    .status(OrderStatus.valueOf(rs.getString(1)))
                    .arrivalTime(rs.getTimestamp(2).toLocalDateTime())
                    .createdAt(rs.getTimestamp(3).toLocalDateTime())
                    .finishedAt(Objects.isNull(finishedAt) ? null : finishedAt.toLocalDateTime())
                    .user(User.builder()
                            .id(rs.getLong(5))
                            .name(rs.getString(6))
                            .build())
                    .build());

        }
    }

    @Override
    @SneakyThrows
    public void updateOrder(Order order) {
        String UPDATE = """
                UPDATE orders
                SET status_id=(SELECT status_id FROM statuses WHERE value=?),
                arrival_time=?;
                    """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setObject(2, order.getArrivalTime());
            stmt.executeUpdate();
        }
    }
}
