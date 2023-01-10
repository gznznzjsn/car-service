package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Override
    @SneakyThrows
    public Optional<Task> readTask(Long id) {
        String FETCH_BY_ID_QUERY = """
                SELECT name, duration, cost_per_hour,specializations.value
                FROM tasks JOIN specializations USING (specialization_id)
                WHERE task_id = ?;
                """;
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID_QUERY);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
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
