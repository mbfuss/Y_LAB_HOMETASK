package org.example.model;


public class Resource {
    private Long id;
    private String name;
    private boolean isConferenceRoom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConferenceRoom() {
        return isConferenceRoom;
    }

    public void setConferenceRoom(boolean conferenceRoom) {
        isConferenceRoom = conferenceRoom;
    }
}

