package com.example.technology_app.models;



public class UserModel{
    public int statusCode;
    public String message;
    public Metadata metadata;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public UserModel(int statusCode, String message, Metadata metadata) {
        this.statusCode = statusCode;
        this.message = message;
        this.metadata = metadata;
    }

    public static class Metadata{
        public User user;
        public Tokens tokens;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Tokens getTokens() {
            return tokens;
        }

        public void setTokens(Tokens tokens) {
            this.tokens = tokens;
        }
    }

    public static class User{
        public  String _id;
        public  String name;
        public  String email;

        public User(String _id, String name, String email, String tokenFirebase) {
            this._id = _id;
            this.name = name;
            this.email = email;
            this.tokenFirebase = tokenFirebase;
        }

        public User() {

        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTokenFirebase() {
            return tokenFirebase;
        }

        public void setTokenFirebase(String tokenFirebase) {
            this.tokenFirebase = tokenFirebase;
        }

        public  String tokenFirebase;
    }

    public static class Tokens{
        public String accessToken;
        public String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}

