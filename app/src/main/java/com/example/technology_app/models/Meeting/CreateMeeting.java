package com.example.technology_app.models.Meeting;

public class CreateMeeting {
    String message;
    int status;
    Metadata metadata;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public CreateMeeting(String message, int status, Metadata metadata) {
        this.message = message;
        this.status = status;
        this.metadata = metadata;
    }
}
