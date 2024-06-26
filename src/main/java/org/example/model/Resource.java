package org.example.model;


public class Resource {
    private String id;
    private String name;
    private boolean isConferenceRoom;

    public Resource(String id, String name, boolean isConferenceRoom) {
        this.id = id;
        this.name = name;
        this.isConferenceRoom = isConferenceRoom;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isConferenceRoom() {
        return isConferenceRoom;
    }
}

