package com.example.bocatajavafx.services;

import com.example.bocatajavafx.models.Usuario;

public class LoginResponse {
    private boolean success;
    private String message;
    private String role;

    public LoginResponse(boolean success, String message, String role) {
        this.success = success;
        this.message = message;
        this.role = role;
    }

    public LoginResponse(boolean success, String message) {
        this(success, message, "alumno");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }
}
