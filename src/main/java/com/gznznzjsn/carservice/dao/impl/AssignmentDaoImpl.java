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
        String CALCULATE_DURATION_QUERY = """
                with t as(
                select * from tasks
                where task_id in
                (select task_id from assignments_tasks
                 where assignment_id =${assignment.getId()})
                )
                SELECT spe count(t.duration) FROM  t
                GROUP BY specialization_id
                """;

        assignment.setStatus(AssignmentStatus.UNDER_CONSIDERATION);
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
//           PreparedStatement findStmt = conn.prepareStatement(FIND_EMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS);


        }
        return assignment;
    }
}
