package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.config.DatabaseConfig;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mapper.UserMapper;
import org.example.model.LoginRequest;
import org.example.model.User;
import org.example.service.impl.UserServiceImpl;

import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserServiceImpl userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DatabaseConfig dbConfig = new DatabaseConfig();
            this.userService = new UserServiceImpl(dbConfig.createUserRepository());
        } catch (IOException e) {
            throw new ServletException("Failed to initialize UserRepository", e);
        }
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginRequest loginRequest = objectMapper.readValue(req.getReader(), LoginRequest.class);
            User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

            if (user != null) {
                req.getSession().setAttribute("user", user);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(objectMapper.writeValueAsString(UserMapper.INSTANCE.toDTO(user)));
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(UserMapper.INSTANCE.toDTO(user)));
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("User is not logged in");
        }
    }
}

