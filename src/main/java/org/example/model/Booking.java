package org.example.model;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class Booking {
    private String id;
    private User user;
    private Resource resource;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking(String id, User user, Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.user = user;
        this.resource = resource;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Resource getResource() {
        return resource;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
