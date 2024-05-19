package com.example.technology_app.models.GetOrder.products;

import java.util.List;

public class Product {
    private String id;
    private String name;
    private  String salePrice;
    private List<String> images;

    public Product(String id, String name, String salePrice, List<String> images) {
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
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
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
