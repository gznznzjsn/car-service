package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.PeriodDao;
import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.Specialization;
import com.gznznzjsn.carservice.dao.impl.util.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PeriodDaoImpl implements PeriodDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public void delete(Long periodId) {
        String DELETE_PERIOD = """
                    DELETE FROM periods WHERE period_id = ?;
                """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_PERIOD)) {
            stmt.setLong(1, periodId);
            stmt.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<Period> readBy(LocalDateTime arrivalTime, Specialization specialization, int totalDuration) {
        String FETCH_PERIOD = """
                SELECT period_id,period_date,period_start,period_end, employee_id,name,value
                 FROM periods JOIN employees USING (employee_id)
                 JOIN specializations USING (specialization_id)
                 WHERE value = ?
                 AND period_date > ?::date
                 AND (period_end- period_start)>= ?
                 ORDER BY period_date,period_start
                 LIMIT 1;
                 """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_PERIOD)) {
            stmt.setString(1, specialization.name());
            stmt.setObject(2, arrivalTime);
            stmt.setInt(3, totalDuration);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(Period.builder()
                        .id(rs.getLong(1))
                        .date(new java.sql.Date(rs.getDate(2).getTime()).toLocalDate())
                        .start(rs.getInt(3))
                        .end(rs.getInt(4))
                        .employee(Employee.builder()
                                .id(rs.getLong(5))
                                .name(rs.getString(6))
                                .specialization(Specialization.valueOf(rs.getString(7)))
                                .build())
                        .build());
            }
        }
    }

    @Override
    @SneakyThrows
    public void update(Period period) {
        String UPDATE_PERIOD = """
                UPDATE periods
                SET
                employee_id = ?,
                period_start=?,
                period_end=?
                WHERE period_id=?
                """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement updateStmt = conn.prepareStatement(UPDATE_PERIOD, Statement.RETURN_GENERATED_KEYS)) {
            updateStmt.setLong(1, period.getEmployee().getId());
            updateStmt.setInt(2, period.getStart());
            updateStmt.setInt(3, period.getEnd());
            updateStmt.setLong(4, period.getId());
            updateStmt.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<Period> read(Long periodId) {
        String FETCH_PERIOD = """
                SELECT period_id,period_date,period_start,period_end, employee_id,name,value
                 FROM periods JOIN employees USING (employee_id)
                 JOIN specializations USING (specialization_id)
                 WHERE period_id=?
                 """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_PERIOD)) {
            stmt.setLong(1, periodId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(Period.builder()
                        .id(rs.getLong(1))
                        .date(new java.sql.Date(rs.getDate(2).getTime()).toLocalDate())
                        .start(rs.getInt(3))
                        .end(rs.getInt(4))
                        .employee(Employee.builder()
                                .id(rs.getLong(5))
                                .name(rs.getString(6))
                                .specialization(Specialization.valueOf(rs.getString(7)))
                                .build())
                        .build());
            }
        }
    }

}
