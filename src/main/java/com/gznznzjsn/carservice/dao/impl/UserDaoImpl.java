package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.UserDao;
import com.gznznzjsn.carservice.domain.carservice.User;
import com.gznznzjsn.carservice.dao.impl.util.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public Optional<User> readUser(Long userId) {
        String FETCH_BY_ID = """
                SELECT name
                FROM users
                WHERE user_id = ?;
                """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(User.builder()
                        .id(userId)
                        .name(rs.getString(1))
                        .build());
            }

        }
    }
}
