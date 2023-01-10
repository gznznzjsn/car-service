package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.PeriodDao;
import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PeriodDaoImpl implements PeriodDao {
    @Override
    @SneakyThrows
    public Optional<Period> erasePeriod(LocalDateTime arrivalTime, Specialization specialization, int totalDuration) {
        String FETCH_PERIOD_QUERY = """
                SELECT period_id,period_date,period_start,period_end, employee_id,name,value
                FROM periods JOIN employees USING (employee_id)
                JOIN specializations USING (specialization_id)
                WHERE value = ?
                AND period_date > (?::date)
                AND (period_end- period_start)>= ?
                ORDER BY period_date,period_start
                LIMIT 1
                ;
                """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(FETCH_PERIOD_QUERY);
            stmt.setString(1, specialization.name());
            stmt.setObject(2, arrivalTime);
            stmt.setInt(3, totalDuration);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            Period period = Period.builder()
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
            if (period.getEnd() - period.getStart() == totalDuration) {
                String DELETE_PERIOD_QUERY = """
                        DELETE FROM periods WHERE period_id = ?
                            """;
                stmt = conn.prepareStatement(DELETE_PERIOD_QUERY);
                stmt.setLong(1, period.getId());
                stmt.executeUpdate();
            } else {
                String UPDATE_PERIOD_QUERY = """
                        update periods set period_start=? where period_id=?
                        """;
                stmt = conn.prepareStatement(UPDATE_PERIOD_QUERY);
                stmt.setInt(1, period.getStart() + totalDuration);
                stmt.setLong(2, period.getId());
                stmt.executeUpdate();
                period.setEnd(period.getStart() + totalDuration);
            }
            return Optional.of(period);
        }
    }
}
