package com.tasktracker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tasktracker.dto.program.ProgramSummaryDto;
import com.tasktracker.dto.project.ProjectSummaryDto;
import com.tasktracker.dto.task.ProjectTaskCountDto;
import com.tasktracker.dto.task.TaskCountDto;
import com.tasktracker.dto.task.UserProgramTaskCountDto;
import com.tasktracker.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private DataStore dataStore;

    public DataService() {
        loadData();
    }

    private static final String FILE_PATH = System.getProperty("catalina.base") + "/data/prjdata.json";

    public void saveToFile() {
        try {
            File file = new File(FILE_PATH);
            System.out.println(FILE_PATH);
            file.getParentFile().mkdirs();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(this.dataStore, writer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            File file = new File(FILE_PATH);
            Gson gson = new Gson();

            if (file.exists()) {
                try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
                    dataStore = gson.fromJson(reader, DataStore.class);
                }
            } else {
                dataStore = new DataStore();
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataStore = new DataStore();
        }
    }

    public void replaceData(DataStore newData) {
        this.dataStore = newData;
    }

    private boolean userExists(String userId) {
        return dataStore.getIndividuals()
                .stream()
                .anyMatch(u -> u.getUserId().equals(userId));
    }

    private boolean programExists(String programId) {
        return dataStore.getPrograms()
                .stream()
                .anyMatch(p -> p.getProgramId().equals(programId));
    }

    private boolean projectExists(String projectId) {
        return dataStore.getPrograms().stream()
                .flatMap(p -> p.getProjects().stream())
                .anyMatch(pr -> pr.getProjectId().equals(projectId));
    }

    public List<ProgramSummaryDto> getProgramsByUser(String userId) {
        if (!userExists(userId)) {
            throw new RuntimeException("User not found");
        }

        List<ProgramSummaryDto> result = new ArrayList<>();

        for (Programs program : dataStore.getPrograms()) {
            for (Projects project : program.getProjects()) {
                if (project.getMembersId().contains(userId)) {
                    result.add(new ProgramSummaryDto(
                            program.getProgramId(),
                            program.getProgramName()
                    ));
                    break;
                }
            }
        }
        return result;
    }

    public String getProgramNameByProject(String projectId) {
        for (Programs program : dataStore.getPrograms()) {
            for (Projects project : program.getProjects()) {
                if (project.getProjectId().equals(projectId)) {
                    return program.getProgramName();
                }
            }
        }
        throw new RuntimeException("Project not found");
    }

    public List<ProjectSummaryDto> getProjectsByUser(String userId) {
        if (!userExists(userId)) {
            throw new RuntimeException("User not found");
        }

        List<ProjectSummaryDto> result = new ArrayList<>();

        for (Programs program : dataStore.getPrograms()) {
            for (Projects project : program.getProjects()) {
                if (project.getMembersId().contains(userId)) {
                    result.add(new ProjectSummaryDto(
                            project.getProjectId(),
                            project.getProjectName(),
                            program.getProgramId(),
                            program.getProgramName()
                    ));
                }
            }
        }
        return result;
    }

    public ProjectTaskCountDto getPendingTasksByProject(String projectId) {
        if (!projectExists(projectId)) {
            throw new RuntimeException("Project not found");
        }

        for (Programs program : dataStore.getPrograms()) {
            for (Projects project : program.getProjects()) {
                if (project.getProjectId().equals(projectId)) {
                    long count = project.getTasks().stream()
                            .filter(task -> "PENDING".equalsIgnoreCase(task.getStatus()))
                            .count();

                    return new ProjectTaskCountDto(projectId, count);
                }
            }
        }
        return new ProjectTaskCountDto(projectId, 0);
    }

    public TaskCountDto getPendingTasksByUser(String userId) {
        if (!userExists(userId)) {
            throw new RuntimeException("User not found");
        }

        long count = 0;

        for (Programs program : dataStore.getPrograms()) {
            for (Projects project : program.getProjects()) {
                for (Tasks task : project.getTasks()) {
                    if (task.getOwnerId().equals(userId) &&
                            "PENDING".equalsIgnoreCase(task.getStatus())) {
                        count++;
                    }
                }
            }
        }
        return new TaskCountDto(userId, count);
    }

    public UserProgramTaskCountDto getPendingTasksByUserAndProgram(String userId, String programId) {

        if (!userExists(userId)) {
            throw new RuntimeException("User not found");
        }

        if (!programExists(programId)) {
            throw new RuntimeException("Program not found");
        }

        long count = 0;

        for (Programs program : dataStore.getPrograms()) {
            if (program.getProgramId().equals(programId)) {
                for (Projects project : program.getProjects()) {
                    for (Tasks task : project.getTasks()) {
                        if (task.getOwnerId().equals(userId) &&
                                "PENDING".equalsIgnoreCase(task.getStatus())) {
                            count++;
                        }
                    }
                }
            }
        }
        return new UserProgramTaskCountDto(userId, programId, count);
    }
}