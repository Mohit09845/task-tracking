package com.tasktracker.model;

import java.util.List;

public class Programs {
    private String programId;
    private String programName;
    private List<Projects> projects;

    public String getProgramId() {
        return programId;
    }

    public String getProgramName() {
        return programName;
    }

    public List<Projects> getProjects() {
        return projects;
    }
}