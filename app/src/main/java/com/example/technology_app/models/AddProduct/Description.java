package com.example.technology_app.models.AddProduct;

public class Description {
    String screen;
    String rear_camera;
    String front_camera;
    String chip;
    String ram;
    String storage;
    String sim;
    String battery;

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getRear_camera() {
        return rear_camera;
    }

    public void setRear_camera(String rear_camera) {
        this.rear_camera = rear_camera;
    }

    public String getFront_camera() {
        return front_camera;
    }

    public void setFront_camera(String front_camera) {
        this.front_camera = front_camera;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Description(String screen, String rear_camera, String front_camera, String chip, String ram, String storage, String sim, String battery, String brand) {
        this.screen = screen;
        this.rear_camera = rear_camera;
        this.front_camera = front_camera;
        this.chip = chip;
        this.ram = ram;
        this.storage = storage;
        this.sim = sim;
        this.battery = battery;
        this.brand = brand;
    }

    String brand;
}
