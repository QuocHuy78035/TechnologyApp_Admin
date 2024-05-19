package com.example.technology_app.models.GetOrder;

public class Payment {
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Payment(String method) {
        this.method = method;
    }
}
