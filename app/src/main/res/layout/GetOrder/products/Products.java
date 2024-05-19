package com.example.technology_app.models.GetOrder.products;

public class Products {
    private Product product;
    private int quantity;
    private String id;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Products(Product product, int quantity, String id) {
        this.product = product;
        this.quantity = quantity;
        this.id = id;
    }
}
