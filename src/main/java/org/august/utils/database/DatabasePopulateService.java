package org.august.utils.database;

import org.august.utils.config.Config;
import org.august.utils.exception.DatabaseQueryIOException;
import org.august.utils.exception.DatabaseQuerySQLException;
import org.august.utils.model.Client;
import org.august.utils.model.Project;
import org.august.utils.model.ProjectToWorkersRelation;
import org.august.utils.model.Worker;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;


public class DatabasePopulateService {

    Connection connection;

    public DatabasePopulateService(Connection connection) {
        this.connection = connection;
    }

    public void initPopulate() {
        try {
            String fileName = new Config().getValue(Config.INIT_POPULATE_DB_FILE);
            String sqlUpdate = String.join("\n", Files.readAllLines(Paths.get(fileName)));
            PreparedStatement preparedSt = connection.prepareStatement(sqlUpdate);
            preparedSt.executeUpdate();
        } catch (IOException e) {
            throw new DatabaseQueryIOException(e);
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
    }

    public Worker insertWorker(Worker worker) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO worker (name, birthday, level, salary) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setString(2, worker.getBirthday().toString());
            preparedStatement.setString(3, worker.getLevel());
            preparedStatement.setLong(4, worker.getSalary());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                worker.setId(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return worker;
    }

    public Client insertClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO client (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return client;
    }

    public Project insertProject(Project project) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO project (client_id, name, start_date, finish_date) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, project.getClientId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getStartDate().toString());
            preparedStatement.setString(4, project.getFinishDate().toString());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                project.setId(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return project;
    }

    public ProjectToWorkersRelation insertProjectToWorkerRelation(ProjectToWorkersRelation projectWorker) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO project_worker (project_id, worker_id) VALUES (?, ?)");
            for (long currentWorkerId : projectWorker.getWorkerId()) {
                preparedStatement.setLong(1, projectWorker.getProjectId());
                preparedStatement.setLong(2, currentWorkerId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseQuerySQLException(e);
        }
        return projectWorker;
    }
}
