package com.tasktracker.dto.task;

public class UserProgramTaskCountDto {
    private String userId;
    private String programId;
    private long pendingTasks;

    public UserProgramTaskCountDto(String userId, String programId, long pendingTasks) {
        this.userId = userId;
        this.programId = programId;
        this.pendingTasks = pendingTasks;
    }
}
