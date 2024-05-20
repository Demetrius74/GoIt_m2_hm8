package org.august.utils.model;

import java.time.LocalDate;

public class Project {
    private long id;
    private long clientId;
    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;

    public Project(long id, long clientId, String name, LocalDate startDate, LocalDate finishDate) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Project(long clientId, String name, LocalDate startDate, LocalDate finishDate) {
        this.clientId = clientId;
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                '}';
    }
}