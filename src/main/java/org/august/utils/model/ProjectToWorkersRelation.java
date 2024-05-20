package org.august.utils.model;

import java.util.Arrays;

public class ProjectToWorkersRelation {
    private long projectId;
    private long[] workerId;

    public ProjectToWorkersRelation(long projectId, long[] workerId) {
        this.projectId = projectId;
        this.workerId = workerId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long[] getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long[] workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "ProjectToWorkersRelation{" +
                "projectId=" + projectId +
                ", workerId=" + Arrays.toString(workerId) +
                '}';
    }
}
