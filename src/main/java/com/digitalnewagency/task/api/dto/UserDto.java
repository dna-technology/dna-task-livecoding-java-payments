package com.digitalnewagency.task.api.dto;

import java.util.UUID;

public class UserDto {

    private UUID userId;

    private String fullName;

    private String email;

    public UserDto() {
    }

    public UserDto(UUID userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
