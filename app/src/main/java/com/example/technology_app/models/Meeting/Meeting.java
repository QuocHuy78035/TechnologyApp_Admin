package com.example.technology_app.models.Meeting;

public class Meeting {
    String id;
    String token;
    String _id;
    String createdAt;
    String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Meeting(String id, String token, String _id, String createdAt, String updatedAt) {
        this.id = id;
        this.token = token;
        this._id = _id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
