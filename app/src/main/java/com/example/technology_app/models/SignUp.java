package com.example.technology_app.models;

public class SignUp {
    public int status;
    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignUp(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
