package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.EmployeeDao;
import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import com.gznznzjsn.carservice.util.ConnectionPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    @SneakyThrows
    public List<Employee> readAll() {
        String FETCH_ALL_QUERY = """
                SELECT employee_id, name, specializations.value FROM employees
                JOIN specializations USING(specialization_id);
                """;
        List<Employee> allEmployees = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(FETCH_ALL_QUERY);
            while (rs.next()) {
                allEmployees.add(Employee.builder()
                        .id(rs.getLong("employee_id"))
                        .name(rs.getString("name"))
                        .specialization(Specialization.valueOf(rs.getString("value")))
                        .build());
            }
        }
        return allEmployees;
    }

    @Override
    @SneakyThrows
    public Employee createEmployee(Employee employee) {
        String ADD_EMPLOYEE_QUERY = """
                                INSERT INTO employees (name, specialization_id)
                                VALUES (?,
                                (SELECT specialization_id FROM specializations WHERE value = ?)
                                );
                """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(ADD_EMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSpecialization().name());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            employee.setId(keys.getLong(1));
        }
        return employee;
    }

    @Override
    @SneakyThrows
    public Optional<Employee> readEmployeeById(Long employeeId) {
        String FETCH_BY_ID_QUERY = """
                SELECT name, specializations.value
                FROM employees JOIN specializations USING (specialization_id)
                WHERE employee_id = ?;
                """;

        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(FETCH_BY_ID_QUERY);
            stmt.setLong(1, employeeId);
            ResultSet rs = stmt.executeQuery();
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
