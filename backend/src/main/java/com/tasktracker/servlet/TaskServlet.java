package com.tasktracker.servlet;

import com.google.gson.Gson;
import com.tasktracker.dto.task.TaskCountDto;
import com.tasktracker.dto.task.UserProgramTaskCountDto;
import com.tasktracker.service.DataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        Gson gson = new Gson();

        String userId = request.getParameter("userId");
        String programId = request.getParameter("programId");

        try {
            if (userId != null && programId == null) {
                TaskCountDto dto = dataService.getPendingTasksByUser(userId);
                response.getWriter().write(gson.toJson(dto));
                return;
            }

            if (userId != null && programId != null) {
                UserProgramTaskCountDto dto =
                        dataService.getPendingTasksByUserAndProgram(userId, programId);
                response.getWriter().write(gson.toJson(dto));
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("error: Missing parameters");

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("error: " + e.getMessage());
        }
    }
}
