package org.august.utils.database;

import org.august.utils.config.Config;
import org.august.utils.exception.DatabaseQueryIOException;
import org.august.utils.exception.DatabaseQuerySQLException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DatabaseInitService {
    private final Connection connection;

    public DatabaseInitService(Connection connection) {
        this.connection = connection;
    }

    public void initialize() {
        try {
            String fileName = new Config().getValue(Config.INIT_DB_FILE);
            String sqlUpdate = String.join("\n", Files.readAllLines(Paths.get(fileName)));
            PreparedStatement preparedSt = connection.prepareStatement(sqlUpdate);
            preparedSt.executeUpdate();
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
    }
}
