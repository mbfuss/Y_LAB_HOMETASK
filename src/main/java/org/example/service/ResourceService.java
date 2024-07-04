package org.example.service;

import org.example.model.Resource;

import java.util.List;

public interface ResourceService {
    void addResource(String name, boolean isConferenceRoom);
    Resource getResource(Long id);
    List<Resource> getAllResources();
}

