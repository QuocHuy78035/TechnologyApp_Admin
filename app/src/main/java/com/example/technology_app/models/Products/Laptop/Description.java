package com.example.technology_app.models.Products.Laptop;

import java.io.Serializable;

public class Description implements Serializable {
    private String cpu;
    private String ram;
    private String hard_drive;
    private String screen;
    private String graphics_card;
    private String port;
    private String operating_system;
    private String design;
    private String size;
    private String released_date;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHard_drive() {
        return hard_drive;
    }

    public void setHard_drive(String hard_drive) {
        this.hard_drive = hard_drive;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getGraphics_card() {
        return graphics_card;
    }

    public void setGraphics_card(String graphics_card) {
        this.graphics_card = graphics_card;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOperating_system() {
        return operating_system;
    }

    public void setOperating_system(String operating_system) {
        this.operating_system = operating_system;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getReleased_date() {
        return released_date;
    }

    public void setReleased_date(String released_date) {
        this.released_date = released_date;
    }

    public Description(String cpu, String ram, String hard_drive, String screen, String graphics_card, String port, String operating_system, String design, String size, String released_date) {
        this.cpu = cpu;
        this.ram = ram;
        this.hard_drive = hard_drive;
        this.screen = screen;
        this.graphics_card = graphics_card;
        this.port = port;
        this.operating_system = operating_system;
        this.design = design;
        this.size = size;
        this.released_date = released_date;
    }
}
