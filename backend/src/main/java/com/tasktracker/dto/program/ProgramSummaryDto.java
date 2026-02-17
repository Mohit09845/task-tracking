package com.tasktracker.dto.program;

public class ProgramSummaryDto {
    private String programId;
    private String programName;

    public ProgramSummaryDto(String programId, String programName) {
        this.programId = programId;
        this.programName = programName;
    }
}
