package org.august.utils.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.august.utils.config.Config;
import org.august.utils.exception.DatabaseQuerySQLException;

import java.sql.Connection;
import java.sql.SQLException;


public class Database {

    private static final Config config = new Config();
    private static final HikariConfig hikariConfig = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        hikariConfig.setJdbcUrl(config.getValue(Config.DB_URL_CONNECTION));
        ds = new HikariDataSource(hikariConfig);
    }

    private Database() {
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
    }
}