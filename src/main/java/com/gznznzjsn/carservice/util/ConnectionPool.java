package com.gznznzjsn.carservice.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
public class ConnectionPool {

    private final DataSource ds;

    public Connection getConnection() {
        return DataSourceUtils.getConnection(ds);
    }

}