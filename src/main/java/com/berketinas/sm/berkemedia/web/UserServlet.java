package com.berketinas.sm.berkemedia.web;

import com.berketinas.sm.berkemedia.dao.UserDAOImpl;
import com.berketinas.sm.berkemedia.model.User;
import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

// ***
// controller to handle requests
// ***

@WebServlet("/")
@MultipartConfig
public class UserServlet extends HttpServlet {
    private UserDAOImpl userDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        String action = request.getServletPath();

        if (action.startsWith("index", 1)) {
            try {
                index(response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (action.startsWith("show", 1)) {
            try {
                show(UUID.fromString(request.getParameter("id")), response);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        update(request);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        delete(UUID.fromString(request.getParameter("id")));
    }

    private void index(HttpServletResponse response)
        throws SQLException, IOException {

        List<User> users = userDAO.findAll();
        String usersJsonString = new Gson().toJson(users);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(usersJsonString);
        out.flush();
    }

    private void show(UUID id, HttpServletResponse response)
        throws SQLException, IOException {

        User user = userDAO.find(id);
        String userJsonString = new Gson().toJson(user);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();
    }

    private void update(HttpServletRequest request) {
        userDAO.save(new User(request.getParameter("given"), request.getParameter("family"), request.getParameter("email"), request.getParameter("country")));
    }

    private void delete(UUID id) {
        userDAO.delete(id);
    }
}
