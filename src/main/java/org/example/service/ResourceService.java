package org.example.service;

import org.example.model.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервисный класс для управления ресурсами.
 */
public class ResourceService {
    private Map<String, Resource> resources = new HashMap<>();

    /**
     * Добавляет новый ресурс в систему.
     *
     *  id               Идентификатор ресурса.
     *  name             Название ресурса.
     *  isConferenceRoom Флаг, указывающий, является ли ресурс конференц-залом.
     * throws IllegalArgumentException Если ресурс с таким идентификатором уже существует.
     */
    public void addResource(String id, String name, boolean isConferenceRoom) {
        if (resources.containsKey(id)) {
            throw new IllegalArgumentException("Resource with this ID already exists");
        }
        resources.put(id, new Resource(id, name, isConferenceRoom));
    }

    /**
     * Возвращает ресурс по его идентификатору.
     *
     *  id Идентификатор ресурса.
     * return Ресурс с указанным идентификатором или null, если ресурс не найден.
     */
    public Resource getResource(String id) {
        return resources.get(id);
    }

    /**
     * Возвращает все ресурсы, доступные в системе.
     *
     * return Карта всех ресурсов, где ключ - идентификатор ресурса, значение - сам ресурс.
     */
    public Map<String, Resource> getAllResources() {
        return new HashMap<>(resources);
    }
}
