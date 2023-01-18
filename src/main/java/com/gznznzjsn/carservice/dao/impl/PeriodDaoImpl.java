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
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PeriodDaoImpl implements PeriodDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public Optional<Period> erase(LocalDateTime arrivalTime, Specialization specialization, int totalDuration) {
        String FETCH_PERIOD_QUERY = """
                SELECT period_id,period_date,period_start,period_end, employee_id,name,value
                FROM periods JOIN employees USING (employee_id)
                JOIN specializations USING (specialization_id)
                WHERE value = ?
                AND period_date > ?::date
                AND (period_end- period_start)>= ?
                ORDER BY period_date,period_start
                LIMIT 1
                ;
                """;

        Connection conn = connectionPool.getConnection();
        Period period;
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_PERIOD_QUERY)) {
            stmt.setString(1, specialization.name());
            stmt.setObject(2, arrivalTime);
            stmt.setInt(3, totalDuration);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                period = Period.builder()
                        .id(rs.getLong(1))
                        .date(new java.sql.Date(rs.getDate(2).getTime()).toLocalDate())
                        .start(rs.getInt(3))
                        .end(rs.getInt(4))
                        .employee(Employee.builder()
                                .id(rs.getLong(5))
                                .name(rs.getString(6))
                                .specialization(Specialization.valueOf(rs.getString(7)))
                                .build())
                        .build();
            }
        }
        if (period.getEnd() - period.getStart() == totalDuration) {
            String DELETE_PERIOD_QUERY = """
                    DELETE FROM periods WHERE period_id = ?
                        """;
            try (PreparedStatement stmt1 = conn.prepareStatement(DELETE_PERIOD_QUERY)) {
                stmt1.setLong(1, period.getId());
                stmt1.executeUpdate();
            }
        } else {
            String UPDATE_PERIOD_QUERY = """
                    update periods set period_start=? where period_id=?
                    """;
            try (PreparedStatement stmt2 = conn.prepareStatement(UPDATE_PERIOD_QUERY)) {
                stmt2.setInt(1, period.getStart() + totalDuration);
                stmt2.setLong(2, period.getId());
                stmt2.executeUpdate();
            }
            period.setEnd(period.getStart() + totalDuration);
        }
        return Optional.of(period);
    }

}
