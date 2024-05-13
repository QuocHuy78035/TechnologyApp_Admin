package com.example.technology_app.models.Products.Laptop;

import java.util.List;

public class Metadata {
    List<ProductDetail> products;

    public Metadata(List<ProductDetail> products) {
        this.products = products;
    }

    public List<ProductDetail> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetail> products) {
        this.products = products;
    }
}
