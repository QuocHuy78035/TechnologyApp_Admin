package com.example.technology_app.models.UpdateOrder;

public class Metadata {
    Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Metadata(Order order) {
        this.order = order;
    }
}
