package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ResourceDTO;
import org.example.mapper.ResourceMapper;
import org.example.model.Resource;
import org.example.service.ResourceService;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.AuthUtils;

import java.util.List;


@WebServlet("/resources")
public class ResourceServlet extends HttpServlet {
    private final ResourceService resourceService = new ResourceService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Обрабатывает GET-запросы HTTP для получения списка всех ресурсов.
     * Возвращает JSON-объект, содержащий список всех ресурсов.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Resource> resources = resourceService.getAllResources();
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), resources);
    }

    /**
     * Обрабатывает POST-запросы HTTP для добавления нового ресурса.
     * Ожидает JSON-объект ResourceDTO в теле запроса.
     * При успешном добавлении ресурса возвращает статус 201 (Создано), иначе возвращает статус 400 (Плохой запрос).
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtils.isAdmin()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Только администраторы.");
            return;
        }

        ResourceDTO resourceDTO = objectMapper.readValue(req.getInputStream(), ResourceDTO.class);
        try {
            Resource resource = ResourceMapper.INSTANCE.resourceDTOToResource(resourceDTO);
            resourceService.addResource(resource);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
