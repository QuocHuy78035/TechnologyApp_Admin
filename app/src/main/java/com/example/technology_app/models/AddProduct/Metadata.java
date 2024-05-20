package com.example.technology_app.models.AddProduct;

public class Metadata {
    Product product;

    public Metadata(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
