package com.tasktracker.servlet;

import com.google.gson.Gson;
import com.tasktracker.model.DataStore;
import com.tasktracker.service.DataService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/importdata")
public class DataImportServlet extends HttpServlet {
    @Override
    public void init() {
        if (getServletContext().getAttribute("dataService") == null) {
            getServletContext().setAttribute("dataService", new DataService());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        DataStore data = gson.fromJson(request.getReader(), DataStore.class);

        DataService service = (DataService) getServletContext().getAttribute("dataService");

        service.replaceData(data);
        service.saveToFile();

        response.setContentType("application/json");
        response.getWriter().write("message: Data imported successfully");
    }
}
