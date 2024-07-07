package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UserDTO;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserServlet(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);

        // Валидация
        // if (!isValid(userDTO)) {
        //     resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //     return;
        // }

        userService.registerUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.isAdmin());
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}


