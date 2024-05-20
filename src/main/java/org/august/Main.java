package org.august;

import org.august.utils.database.Database;
import org.august.utils.database.DatabasePopulateService;
import org.august.utils.database.DatabaseQueryService;
import org.august.utils.model.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Connection connection = Database.getConnection();
//        new DatabaseInitService(connection).initialize();
        DatabasePopulateService dbPopulateService = new DatabasePopulateService(connection);

        Worker newWorker = new Worker(
                "Markus Person",
                LocalDate.now().minusYears(33),
                "Middle",
                3500
        );

        long workerId = dbPopulateService.insertWorker(newWorker).getId();
        System.out.println("newWorker = " + newWorker);


        Client newClient = new Client("Mojang");
        long clientId = dbPopulateService.insertClient(newClient).getId();
        System.out.println("newClient = " + newClient);

        Project newProject = new Project(
                clientId,
                "Project X",
                LocalDate.now().minusMonths(7),
                LocalDate.now());
        long projectId = dbPopulateService.insertProject(newProject).getId();
        System.out.println("newProject = " + newProject);

        ProjectToWorkersRelation projectToWorkerRelation = new ProjectToWorkersRelation(
                projectId,
                new long[]{workerId}
        );
        dbPopulateService.insertProjectToWorkerRelation(projectToWorkerRelation);
        System.out.println("projectToWorkerRelation = " + projectToWorkerRelation);

        DatabaseQueryService dbQueryService = new DatabaseQueryService(connection);

        List<LongestProject> longestProjectList = dbQueryService.findLongestProject();
        longestProjectList.forEach(System.out::println);

        List<MaxSalaryWorker> maxSalaryWorkerList = dbQueryService.findMaxSalaryWorker();
        maxSalaryWorkerList.forEach(System.out::println);

        List<MaxProjectsClient> maxProjectsClientList = dbQueryService.findMaxProjectsClient();
        maxProjectsClientList.forEach(System.out::println);

        List<EldestWorker> eldestWorkerList = dbQueryService.findEldestWorker();
        eldestWorkerList.forEach(System.out::println);

        List<YoungestWorker> youngestWorkerList = dbQueryService.findYoungestWorker();
        youngestWorkerList.forEach(System.out::println);

        List<ProjectPrice> projectPriceList = dbQueryService.getProjectPrices();
        projectPriceList.forEach(System.out::println);
    }
}
