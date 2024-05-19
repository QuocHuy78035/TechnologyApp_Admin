package com.example.technology_app.models.GetOrder;

import java.util.List;

public class Metadata {
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public Metadata(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
