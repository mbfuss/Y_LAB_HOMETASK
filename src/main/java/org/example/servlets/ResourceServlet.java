package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.DatabaseConfig;
import org.example.dto.ResourceDTO;
import org.example.mapper.ResourceMapper;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.repository.UserRepository;
import org.example.service.impl.ResourceServiceImpl;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/resources")
public class ResourceServlet extends HttpServlet {
    private ResourceServiceImpl resourceService;
    private ObjectMapper objectMapper;
    private Validator validator;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DatabaseConfig dbConfig = new DatabaseConfig();
            this.resourceService = new ResourceServiceImpl(dbConfig.createResourceRepository());
        } catch (IOException e) {
            throw new ServletException("Failed to initialize ResourceRepository", e);
        }
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    private boolean isUserLoggedIn(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isUserLoggedIn(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("User is not logged in");
            return;
        }

        ResourceDTO resourceDTO = objectMapper.readValue(req.getReader(), ResourceDTO.class);
        var violations = validator.validate(resourceDTO);

        if (!violations.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(objectMapper.writeValueAsString(violations));
            return;
        }

        Resource resource = ResourceMapper.INSTANCE.toEntity(resourceDTO);
        resourceService.addResource(resource);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(objectMapper.writeValueAsString(ResourceMapper.INSTANCE.toDTO(resource)));
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isUserLoggedIn(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("User is not logged in");
            return;
        }

        List<ResourceDTO> resources = resourceService.getAllResources()
                .stream()
                .map(ResourceMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(resources));
    }
}

