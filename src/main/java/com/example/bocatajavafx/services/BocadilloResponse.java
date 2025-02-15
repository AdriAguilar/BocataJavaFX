package com.example.bocatajavafx.services;

public class BocadilloResponse {
    private boolean success;
    private String message;

    public BocadilloResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BocadilloResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
