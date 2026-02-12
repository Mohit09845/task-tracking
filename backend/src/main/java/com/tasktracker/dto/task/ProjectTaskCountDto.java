package com.tasktracker.dto.task;

public class ProjectTaskCountDto {
    private String projectId;
    private long pendingTasks;

    public ProjectTaskCountDto(String projectId, long pendingTasks) {
        this.projectId = projectId;
        this.pendingTasks = pendingTasks;
    }
}
