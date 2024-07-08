package org.example.dto;

public class ResourceDTO {
    private String id;
    private String name;
    private boolean conferenceRoom;

    public ResourceDTO() {
    }

    public ResourceDTO(String id, String name, boolean conferenceRoom) {
        this.id = id;
        this.name = name;
        this.conferenceRoom = conferenceRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(boolean conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
