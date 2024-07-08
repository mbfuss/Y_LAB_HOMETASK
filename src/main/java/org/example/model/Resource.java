package org.example.model;

public class Resource {
    private String id; // Идентификатор
    private String name;
    private boolean isConferenceRoom;

    // Конструктор по умолчанию
    public Resource() {}

    // Конструктор с параметрами
    public Resource(String id, String name, boolean isConferenceRoom) {
        this.id = id;
        this.name = name;
        this.isConferenceRoom = isConferenceRoom;
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
        return isConferenceRoom;
    }

    public void setConferenceRoom(boolean conferenceRoom) {
        isConferenceRoom = conferenceRoom;
    }
}
