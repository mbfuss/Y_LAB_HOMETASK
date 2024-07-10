package org.example.service;

import org.example.model.Resource;

import java.util.List;

public interface ResourceService {
    void addResource(Resource resource);
    Resource getResource(Long id);
    List<Resource> getAllResources();
}

