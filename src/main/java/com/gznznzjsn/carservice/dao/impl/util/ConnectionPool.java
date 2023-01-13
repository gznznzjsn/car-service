package com.gznznzjsn.carservice.dao.impl.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@RequiredArgsConstructor
public class ConnectionPool {

    private final DataSource ds;

    public Connection getConnection() {
        return DataSourceUtils.getConnection(ds);
    }

}