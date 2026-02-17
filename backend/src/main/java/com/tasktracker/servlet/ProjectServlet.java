package com.tasktracker.servlet;

import com.google.gson.Gson;
import com.tasktracker.dto.project.ProjectSummaryDto;
import com.tasktracker.dto.task.ProjectTaskCountDto;
import com.tasktracker.service.DataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private DataService dataService;

    @Override
    public void init() throws ServletException {
        dataService = (DataService) getServletContext().getAttribute("dataService");
        if (dataService == null) {
            dataService = new DataService();
            getServletContext().setAttribute("dataService", dataService);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        String userId = request.getParameter("userId");
        String projectId = request.getParameter("projectId");

        try {
            if (userId != null && projectId == null) {
                List<ProjectSummaryDto> projects = dataService.getProjectsByUser(userId);
                response.getWriter().write(gson.toJson(projects));
                return;
            }

            if (projectId != null && userId == null) {
                ProjectTaskCountDto countDto = dataService.getPendingTasksByProject(projectId);
                response.getWriter().write(gson.toJson(countDto));
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("error: Provide either userId or projectId");

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("error: " + e.getMessage());
        }
    }
}