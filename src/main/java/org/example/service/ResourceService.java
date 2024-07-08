package org.example.service;

import org.example.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceService {
    // Список ресурсов
    private List<Resource> resources = new ArrayList<>();

    // Конструктор для добавления примерных ресурсов
    public ResourceService() {
        // Пример добавления ресурсов при инициализации сервиса
        resources.add(new Resource("1", "Конференц-зал A", true));
        resources.add(new Resource("2", "Рабочее пространство B", false));
    }

    // Метод для получения всех ресурсов
    public List<Resource> getAllResources() {
        return resources;
    }

    // Метод для получения ресурса по его идентификатору
    public Resource getResourceById(String id) {
        return resources.stream()
                .filter(resource -> resource.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Метод для добавления нового ресурса
    public void addResource(Resource resource) {
        resources.add(resource);
    }
}
