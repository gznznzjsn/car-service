package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.carservice.*;
import com.gznznzjsn.carservice.domain.carservice.enums.AssignmentStatus;
import com.gznznzjsn.carservice.domain.carservice.enums.OrderStatus;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AssignmentDaoImpl implements AssignmentDao {
    @Override
    @SneakyThrows
    public void createAssignment(Assignment assignment) {

        String CREATE_ASSIGNMENT = """
                INSERT INTO assignments (order_id, specialization_id, start_time, final_cost, employee_id, assignment_status_id, user_commentary, employee_commentary)
                VALUES (?,(SELECT specialization_id FROM specializations WHERE value =?),?,?,?,(SELECT assignment_status_id FROM assignment_statuses WHERE value =?),?,?)
                """;
        String INSERT_TASKS_TO_ASSIGNMENT = """
                INSERT INTO assignments_tasks (task_id, assignment_id)
                VALUES (?,?)
                    """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement createStmt = conn.prepareStatement(CREATE_ASSIGNMENT, Statement.RETURN_GENERATED_KEYS);
            createStmt.setLong(1, assignment.getOrder().getId());
            createStmt.setString(2, assignment.getSpecialization().name());
            createStmt.setObject(3, assignment.getStartTime());
            createStmt.setBigDecimal(4, assignment.getFinalCost());
            createStmt.setObject(5, Objects.isNull(assignment.getEmployee()) ? null : assignment.getEmployee().getId());
            createStmt.setString(6, assignment.getStatus().name());
            createStmt.setString(7, assignment.getUserCommentary());
            createStmt.setString(8, assignment.getEmployeeCommentary());
            createStmt.executeUpdate();
            ResultSet keys = createStmt.getGeneratedKeys();
            keys.next();
            assignment.setId(keys.getLong(1));

            PreparedStatement insertStmt = conn.prepareStatement(INSERT_TASKS_TO_ASSIGNMENT);
            for (Task t : assignment.getTasks()) {
                insertStmt.setLong(1, t.getId());
                insertStmt.setLong(2, assignment.getId());
                insertStmt.executeUpdate();
            }
        }
    }

    @Override
    @SneakyThrows
    public Optional<Assignment> readAssignment(Long assignmentId) {
        String FETCH_BY_ID = """
                SELECT order_id, st.value, arrival_time,created_at,finished_at, user_id,u.name,
                    sp.value,
                    start_time,
                    final_cost,
                    e.employee_id,e.name,sp.value,
                    a_s.value,
                    user_commentary,
                    employee_commentary
                FROM assignments a
                JOIN orders o USING (order_id)
                JOIN specializations sp USING (specialization_id)
                LEFT JOIN employees e USING (employee_id)
                JOIN assignment_statuses a_s USING(assignment_status_id)
                JOIN statuses st USING (status_id)
                JOIN users u USING (user_id)
                WHERE assignment_id = ?;
                """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID);
            stmt.setLong(1, assignmentId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            Timestamp finishedAt = rs.getTimestamp(5);
            Timestamp startTime = rs.getTimestamp(9);
            return Optional.of(Assignment.builder()
                    .id(assignmentId)
                    .order(Order.builder()
                            .id(rs.getLong(1))
                            .status(OrderStatus.valueOf(rs.getString(2)))
                            .arrivalTime(rs.getTimestamp(3).toLocalDateTime())
                            .createdAt(rs.getTimestamp(4).toLocalDateTime())
                            .finishedAt(Objects.isNull(finishedAt) ? null : finishedAt.toLocalDateTime())
                            .user(User.builder()
                                    .id(rs.getLong(6))
                                    .name(rs.getString(7))
                                    .build())
                            .build())
                    .specialization(Specialization.valueOf(rs.getString(8)))
                    .startTime(Objects.isNull(startTime) ? null : startTime.toLocalDateTime())
                    .finalCost(rs.getBigDecimal(10))
                    .employee(rs.getLong(11) == 0 ? null : Employee.builder()
                            .id(rs.getLong(11))
                            .name(rs.getString(12))
                            .specialization(Specialization.valueOf(rs.getString(13)))
                            .build())
                    .status(AssignmentStatus.valueOf(rs.getString(14)))
                    .userCommentary(rs.getString(15))
                    .employeeCommentary(rs.getString(16))
                    .build());
        }
    }

    @Override
    @SneakyThrows
    public List<Assignment> readAssignments(Long orderId) {
        String FETCH_BY_ID = """
                SELECT order_id, st.value, arrival_time,created_at,finished_at, user_id,u.name,
                    sp.value,
                    start_time,
                    final_cost,
                    e.employee_id,e.name,sp.value,
                    a_s.value,
                    user_commentary,
                    employee_commentary,
                    assignment_id
                FROM assignments a
                JOIN orders o USING (order_id)
                JOIN specializations sp USING (specialization_id)
                LEFT JOIN employees e USING (employee_id)
                JOIN assignment_statuses a_s USING(assignment_status_id)
                JOIN statuses st USING (status_id)
                JOIN users u USING (user_id)
                WHERE order_id = ?;
                """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID);
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            List<Assignment> assignmentList = new ArrayList<>();
            while (rs.next()) {
                Timestamp finishedAt = rs.getTimestamp(5);
                Timestamp startTime = rs.getTimestamp(9);
                Assignment assignment = Assignment.builder()
                        .order(Order.builder()
                                .id(rs.getLong(1))
                                .status(OrderStatus.valueOf(rs.getString(2)))
                                .arrivalTime(rs.getTimestamp(3).toLocalDateTime())
                                .createdAt(rs.getTimestamp(4).toLocalDateTime())
                                .finishedAt(Objects.isNull(finishedAt) ? null : finishedAt.toLocalDateTime())
                                .user(User.builder()
                                        .id(rs.getLong(6))
                                        .name(rs.getString(7))
                                        .build())
                                .build())
                        .specialization(Specialization.valueOf(rs.getString(8)))
                        .startTime(Objects.isNull(startTime) ? null : startTime.toLocalDateTime())
                        .finalCost(rs.getBigDecimal(10))
                        .employee(rs.getLong(11) == 0 ? null : Employee.builder()
                                .id(rs.getLong(11))
                                .name(rs.getString(12))
                                .specialization(Specialization.valueOf(rs.getString(13)))
                                .build())
                        .status(AssignmentStatus.valueOf(rs.getString(14)))
                        .userCommentary(rs.getString(15))
                        .employeeCommentary(rs.getString(16))
                        .id(rs.getLong(17))
                        .build();
                assignmentList.add(assignment);
            }
            return assignmentList;

        }
    }

    @SneakyThrows
    public Assignment acceptAssignment(Assignment assignment) {
        String UPDATE_QUERY = """
                UPDATE assignments
                SET assignment_status_id=(SELECT assignment_status_id FROM assignment_statuses WHERE value=?) ,
                employee_commentary=?,
                final_cost=?
                WHERE assignment_id=?
                """;
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            updateStmt.setString(1, assignment.getStatus().name());
            updateStmt.setString(2, assignment.getEmployeeCommentary());
            updateStmt.setBigDecimal(3, assignment.getFinalCost());
            updateStmt.setLong(4, assignment.getId());
            updateStmt.executeUpdate();
            ResultSet keys = updateStmt.getGeneratedKeys();
            keys.next();
        }
        return assignment;
    }

    @Override
    @SneakyThrows
    public void updateAssignment(Assignment assignment) {
        String UPDATE_QUERY = """
                UPDATE assignments
                SET
                specialization_id=(SELECT specialization_id FROM specializations WHERE value=?),
                start_time=?,
                final_cost=?,
                employee_id=?,
                assignment_status_id=(SELECT assignment_status_id FROM assignment_statuses WHERE value=?) ,
                user_commentary=?,
                employee_commentary=?
                WHERE assignment_id=?
                """;
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            updateStmt.setString(1, assignment.getSpecialization().name());
            updateStmt.setObject(2, assignment.getStartTime());
            updateStmt.setBigDecimal(3, assignment.getFinalCost());
            updateStmt.setLong(4, assignment.getEmployee().getId());
            updateStmt.setString(5, assignment.getStatus().name());
            updateStmt.setString(6, assignment.getUserCommentary());
            updateStmt.setString(7, assignment.getEmployeeCommentary());
            updateStmt.setLong(8, assignment.getId());
            updateStmt.executeUpdate();
            ResultSet keys = updateStmt.getGeneratedKeys();
            keys.next();
        }
    }
}
