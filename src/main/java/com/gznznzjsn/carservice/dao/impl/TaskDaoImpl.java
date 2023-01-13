package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.carservice.Specialization;
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
public class TaskDaoImpl implements TaskDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public Optional<Task> read(Long id) {
        String FETCH_BY_ID_QUERY = """
                SELECT name, duration, cost_per_hour,specializations.value
                FROM tasks JOIN specializations USING (specialization_id)
                WHERE task_id = ?;
                """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(Task.builder()
                        .id(id)
                        .name(rs.getString(1))
                        .duration(rs.getInt(2))
                        .costPerHour(rs.getBigDecimal(3))
                        .requiredSpecialization(Specialization.valueOf(rs.getString(4)))
                        .build());
            }
        }
    }

}
