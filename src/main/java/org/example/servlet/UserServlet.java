package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.AuthUtils;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Обрабатывает POST-запросы HTTP для создания нового пользователя.
     * Ожидает JSON-объект UserDTO в теле запроса.
     * В случае успешного создания пользователя регистрирует его, иначе возвращает статус 400 (Плохой запрос).
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        try {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            userService.registerUser(user.getUsername(), user.getPassword(), user.isAdmin());
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Обрабатывает PUT-запросы HTTP для аутентификации пользователя.
     * Ожидает JSON-объект UserDTO в теле запроса.
     * В случае успешной аутентификации пользователя выполняет вход на сайт, иначе возвращает статус 401 (Не авторизован).
     */
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        try {
            User user = userService.authenticateUser(userDTO.getUsername(), userDTO.getPassword());
            AuthUtils.login(user);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * Обрабатывает DELETE-запросы HTTP для выхода из системы текущего аутентифицированного пользователя.
     * Выполняет выход пользователя из системы и возвращает статус 204 (Нет содержимого).
     */
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthUtils.logout();
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
