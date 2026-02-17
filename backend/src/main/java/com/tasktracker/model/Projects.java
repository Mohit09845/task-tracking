package com.tasktracker.model;

import java.util.List;

public class Projects {

    private String projectId;
    private String projectName;
    private List<String> membersId;
    private List<Tasks> tasks;

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<String> getMembersId() {
        return membersId;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }
}
