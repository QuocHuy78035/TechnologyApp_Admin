package com.example.technology_app.models;

import java.util.ArrayList;

public class CategoryModel{
    public int status;
    public String message;
    public Metadata metadata;

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

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public CategoryModel(int status, String message, Metadata metadata) {
        this.status = status;
        this.message = message;
        this.metadata = metadata;
    }

    public static class Category{

        public Category(String _id, String name, String createdAt, String updatedAt, int __v, String image) {
            this._id = _id;
            this.name = name;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.__v = __v;
            this.image = image;
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

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String _id;
        public String name;
        public String createdAt;
        public String updatedAt;
        public int __v;
        public String image;
    }

    public static class Metadata{
        public ArrayList<Category> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Category> categories) {
            this.categories = categories;
        }

        public ArrayList<Category> categories;
    }
}
