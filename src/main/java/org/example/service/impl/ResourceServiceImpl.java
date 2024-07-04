package org.example.service.impl;

import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.service.ResourceService;

import java.util.List;

/**
 * Реализация интерфейса ResourceService для управления ресурсами.
 */
public class ResourceServiceImpl implements ResourceService {
    private ResourceRepository resourceRepository;

    /**
     * Конструктор для инициализации ResourceServiceImpl с указанным репозиторием ресурсов.
     */
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Добавляет новый ресурс с указанными параметрами.
     */
    public void addResource(String name, boolean isConferenceRoom) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setConferenceRoom(isConferenceRoom);
        resourceRepository.save(resource);
    }

    /**
     * Возвращает ресурс по его идентификатору.
     */
    public Resource getResource(Long id) {
        return resourceRepository.findById(id);
    }

    /**
     * Возвращает список всех ресурсов.
     */
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
}