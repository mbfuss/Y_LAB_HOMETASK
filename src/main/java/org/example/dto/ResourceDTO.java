package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ResourceDTO {
    @NotBlank
    private String name;

    @NotNull
    private Boolean isConferenceRoom;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getConferenceRoom() {
        return isConferenceRoom;
    }

    public void setConferenceRoom(Boolean conferenceRoom) {
        isConferenceRoom = conferenceRoom;
    }
}
