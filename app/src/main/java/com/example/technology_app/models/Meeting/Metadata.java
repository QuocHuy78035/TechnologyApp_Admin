package com.example.technology_app.models.Meeting;

public class Metadata {
    Meeting meeting;

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Metadata(Meeting meeting) {
        this.meeting = meeting;
    }
}
