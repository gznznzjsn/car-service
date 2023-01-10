package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.carservice.enums.AssignmentStatus;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

@Repository
public class AssignmentDaoImpl implements AssignmentDao {
    @Override
    @SneakyThrows
    public Assignment createAssignment(Long orderId, LocalDateTime arrivalTime, Assignment assignment) {

        String CREATE_ASSIGNMENT = """
                INSERT INTO assignments ( order_id, specialization_id,  assignment_status_id, user_commentary)
                VALUES (?,(SELECT specialization_id FROM specializations WHERE value =?),(SELECT assignment_status_id FROM assignment_statuses WHERE value =?),?)
                """;
        String INSERT_TASKS_TO_ASSIGNMENT = """
                INSERT INTO assignments_tasks (task_id, assignment_id)
                VALUES (?,?)
                    """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement createStmt = conn.prepareStatement(CREATE_ASSIGNMENT, Statement.RETURN_GENERATED_KEYS);
            createStmt.setLong(1, orderId);
            createStmt.setString(2, assignment.getSpecialization().name());
            createStmt.setString(3, assignment.getStatus().name());
            createStmt.setString(4, assignment.getUserCommentary());
            createStmt.executeUpdate();
            ResultSet keys = createStmt.getGeneratedKeys();
            keys.next();
            assignment.setId(keys.getLong(1));
            PreparedStatement insertStmt = conn.prepareStatement(INSERT_TASKS_TO_ASSIGNMENT, Statement.RETURN_GENERATED_KEYS);
            for (Task t : assignment.getTasks()) {
                insertStmt.setLong(1, t.getId());
                insertStmt.setLong(2, assignment.getId());
                insertStmt.executeUpdate();
            }
        }
        return assignment;
    }

    @Override
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
    public Assignment updateAssignment(Assignment assignment) {
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
        return assignment;
    }
}
