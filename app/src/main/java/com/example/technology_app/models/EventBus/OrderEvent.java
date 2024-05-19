package com.example.technology_app.models.EventBus;

import com.example.technology_app.models.GetOrder.Order;

public class OrderEvent {
    Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderEvent(Order order) {
        this.order = order;
    }
}
