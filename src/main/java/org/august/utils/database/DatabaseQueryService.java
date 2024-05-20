package org.august.utils.database;

import org.august.utils.config.Config;
import org.august.utils.exception.DatabaseQueryIOException;
import org.august.utils.exception.DatabaseQuerySQLException;
import org.august.utils.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private final Config config;
    private final Connection connection;

    public DatabaseQueryService(Connection connection) {
        this.connection = connection;
        config = new Config();
    }

    public List<LongestProject> findLongestProject() {
        List<LongestProject> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Config.FIND_LONGEST_PROJECT_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new LongestProject(
                        rs.getString("name"),
                        rs.getInt("month_count")));
            }
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return result;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Config.FIND_MAX_SALARY_WORKER_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new MaxSalaryWorker(
                        rs.getString("name"),
                        rs.getLong("salary")));
            }
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return result;
    }

    public List<MaxProjectsClient> findMaxProjectsClient() {
        List<MaxProjectsClient> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Config.FIND_MAX_PROJECTS_CLIENT_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new MaxProjectsClient(
                        rs.getString("name"),
                        rs.getInt("count_project")));
            }
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return result;
    }

    public List<EldestWorker> findEldestWorker() {
        List<EldestWorker> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Config.FIND_YOUNGEST_ELDEST_WORKER_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals("Eldest")) {
                    result.add(new EldestWorker(
                            rs.getString("type"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("birthday"))));
                }
            }
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return result;
    }

    public List<YoungestWorker> findYoungestWorker() {
        List<YoungestWorker> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Config.FIND_YOUNGEST_ELDEST_WORKER_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals("Youngest")) {
                    result.add(new YoungestWorker(
                            rs.getString("type"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("birthday"))));
                }
            }
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return result;
    }

    public List<ProjectPrice> getProjectPrices() {
        List<ProjectPrice> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Config.GET_PROJECT_PRICE_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new ProjectPrice(
                        rs.getString("name"),
                        rs.getLong("price")
                ));
            }
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return result;
    }

    private String getSqlQueryFromFileName(String prefsKey) throws IOException {
        String fileName = config.getValue(prefsKey);
        return String.join("\n", Files.readAllLines(Paths.get(fileName)));
    }
}
