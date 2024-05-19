package com.example.technology_app.models.GetOrder.products;

import java.util.List;

public class Product {
    private String id;
    private String name;
    private  String sale_price;
    private List<String> images;

    public Product(String id, String name, String sale_price, List<String> images) {
        this.id = id;
        this.name = name;
        this.sale_price = sale_price;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalePrice() {
        return sale_price;
    }

    public void setSalePrice(String sale_price) {
        this.sale_price = sale_price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
