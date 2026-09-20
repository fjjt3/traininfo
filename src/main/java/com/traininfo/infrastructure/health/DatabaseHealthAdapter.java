package com.traininfo.infrastructure.health;

import com.traininfo.domain.port.DatabaseHealthPort;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthAdapter implements DatabaseHealthPort {

    private final DataSource dataSource;

    public DatabaseHealthAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean isUp() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(3);
        } catch (SQLException | RuntimeException e) {
            return false;
        }
    }
}