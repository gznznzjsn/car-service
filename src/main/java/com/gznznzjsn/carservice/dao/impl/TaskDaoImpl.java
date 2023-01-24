package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.Task;
import com.gznznzjsn.carservice.domain.Specialization;
import com.gznznzjsn.carservice.dao.impl.util.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class TaskDaoImpl implements TaskDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public Optional<Task> findById(Long taskId) {
        String FETCH_BY_ID_QUERY = """
                SELECT name, duration, cost_per_hour,specializations.value
                FROM tasks JOIN specializations USING (specialization_id)
                WHERE task_id = ?;
                """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID_QUERY)) {
            stmt.setLong(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(Task.builder()
                        .id(taskId)
                        .name(rs.getString(1))
                        .duration(rs.getInt(2))
                        .costPerHour(rs.getBigDecimal(3))
                        .specialization(Specialization.valueOf(rs.getString(4)))
                        .build());
            }
        }
    }

    @Override
    @SneakyThrows
    public List<Task> findAllByAssignmentId(Long assignmentId) {
        String FETCH_TASKS = """
                SELECT tasks.task_id, name, duration, cost_per_hour, value FROM assignments_tasks
                JOIN tasks USING (task_id)
                JOIN specializations USING (specialization_id)
                WHERE assignment_id=?
                    """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement fetchStmt = conn.prepareStatement(FETCH_TASKS)) {
            fetchStmt.setLong(1, assignmentId);
            List<Task> tasks = new ArrayList<>();
            try (ResultSet rs = fetchStmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(Task.builder()
                            .id(rs.getLong(1))
                            .name(rs.getString(2))
                            .duration(rs.getInt(3))
                            .costPerHour(rs.getBigDecimal(4))
                            .specialization(Specialization.valueOf(rs.getString(5)))
                            .build());
                }
            }
            return tasks;
        }
    }
}
