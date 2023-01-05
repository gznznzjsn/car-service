package com.gznznzjsn.carservice.dao.impl;

import com.gznznzjsn.carservice.dao.EmployeeDao;
import com.gznznzjsn.carservice.domain.entity.Employee;
import com.gznznzjsn.carservice.util.ConnectionPool;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class EmployeeDaoImpl implements EmployeeDao {
    private final String FETCH_ALL_QUERY = " SELECT * FROM employees";
    @Override
    public List<Employee> fetchAll() {
        try (Connection conn = ConnectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(FETCH_ALL_QUERY);
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.print("ID: " + rs.getLong("id"));
                System.out.print(", Name: " + rs.getString("name"));
                System.out.print(", Specialization: " + rs.getLong("specialization_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
