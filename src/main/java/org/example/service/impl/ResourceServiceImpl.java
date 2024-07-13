package org.example.service.impl;

import org.example.aspects.AspectExecutor;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.service.ResourceService;

import java.util.List;

public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public void addResource(Resource resource) {
        long startTime = System.currentTimeMillis();
        String methodName = "addResource";
        try {
            AspectExecutor.logMethodStart(methodName);
            resourceRepository.save(resource);
            AspectExecutor.auditAction(methodName, resource);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
    }

    @Override
    public Resource getResource(Long id) {
        long startTime = System.currentTimeMillis();
        String methodName = "getResource";
        try {
            AspectExecutor.logMethodStart(methodName);
            Resource resource = resourceRepository.findById(id);
            AspectExecutor.auditAction(methodName, id);
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        return null;
    }

    @Override
    public List<Resource> getAllResources() {
        long startTime = System.currentTimeMillis();
        String methodName = "getAllResources";
        try {
            AspectExecutor.logMethodStart(methodName);
            List<Resource> resources = resourceRepository.findAll();
            AspectExecutor.auditAction(methodName);
            return resources;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        return null;
    }
}
