package com.tasktracker.dto.project;

public class ProjectSummaryDto {
    private String projectId;
    private String projectName;
    private String programId;
    private String programName;

    public ProjectSummaryDto(String projectId, String projectName, String programId, String programName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.programId = programId;
        this.programName = programName;
    }
}
