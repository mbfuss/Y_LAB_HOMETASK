package org.example.service;

import org.example.model.Resource;
import org.example.repository.ResourceRepository;

import java.util.List;

public class ResourceService {
    private ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void addResource(String name, boolean isConferenceRoom) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setConferenceRoom(isConferenceRoom);
        resourceRepository.save(resource);
    }

    public Resource getResource(Long id) {
        return resourceRepository.findById(id);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
}
