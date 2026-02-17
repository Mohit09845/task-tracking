package com.tasktracker.dto.task;

public class TaskSummaryDto{
    private String taskId;
    private String taskName;
    private String status;

    public TaskSummaryDto(String taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }
}
