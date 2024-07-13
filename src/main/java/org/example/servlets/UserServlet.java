package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.DatabaseConfig;
import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.service.impl.UserServiceImpl;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserServiceImpl userService;
    private ObjectMapper objectMapper;
    private Validator validator;

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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDTO userDTO = objectMapper.readValue(req.getReader(), UserDTO.class);
            var violations = validator.validate(userDTO);

            if (!violations.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(objectMapper.writeValueAsString(violations));
                return;
            }

            User user = UserMapper.INSTANCE.toEntity(userDTO);
            userService.registerUser(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(UserMapper.INSTANCE.toDTO(user)));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error: " + e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(users));
    }
}

