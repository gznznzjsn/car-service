package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.EmployeeDao;
import com.gznznzjsn.carservice.dao.impl.util.ConnectionPool;
import com.gznznzjsn.carservice.domain.Employee;
import com.gznznzjsn.carservice.domain.Specialization;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final ConnectionPool connectionPool;

    @Override
    @SneakyThrows
    public List<Employee> findAll() {
        String FETCH_ALL_QUERY = """
                SELECT employee_id, name, specializations.value FROM employees
                JOIN specializations USING(specialization_id);
                """;
        List<Employee> allEmployees = new ArrayList<>();
        Connection conn = connectionPool.getConnection();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(FETCH_ALL_QUERY)) {
                while (rs.next()) {
                    allEmployees.add(Employee.builder()
                            .id(rs.getLong("employee_id"))
                            .name(rs.getString("name"))
                            .specialization(Specialization.valueOf(rs.getString("value")))
                            .build());
                }
            }
        }
        return allEmployees;
    }

    @Override
    @SneakyThrows
    public void create(Employee employee) {
        String ADD_EMPLOYEE_QUERY = """
                    INSERT INTO employees (name, specialization_id)
                    VALUES (?,
                       (SELECT specialization_id FROM specializations WHERE value = ?)
                        );
                """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(ADD_EMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSpecialization().name());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                employee.setId(keys.getLong(1));
            }
        }
    }

    @Override
    @SneakyThrows
    public Optional<Employee> findById(Long employeeId) {
        String FETCH_BY_ID_QUERY = """
                SELECT name, specializations.value
                FROM employees JOIN specializations USING (specialization_id)
                WHERE employee_id = ?;
                """;

        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID_QUERY)) {
            stmt.setLong(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(Employee.builder()
                        .id(employeeId)
                        .name(rs.getString(1))
                        .specialization(Specialization.valueOf(rs.getString(2)))
                        .build());
            }
        }
    }

    @Override
    @SneakyThrows
    public void delete(Long employeeId) {
        String DELETE_BY_ID = """
                DELETE FROM employees WHERE employee_id=?;
                """;
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID)) {
            stmt.setLong(1, employeeId);
            stmt.executeUpdate();
        }
    }

}



