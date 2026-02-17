package com.tasktracker.dto.task;

public class TaskCountDto {
    private String userId;
    private long pendingTasks;

    public TaskCountDto(String userId, long pendingTasks) {
        this.userId = userId;
        this.pendingTasks = pendingTasks;
    }
}
