package org.example.model;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private User user;
    private Resource resource;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Бронирование " +
                "id: " + id +
                ", начало: " + startTime +
                ", конец: " + endTime +
                ", пользователь: " + user.getUsername() +
                ", ресурс: " + resource.getName();

    }
}
