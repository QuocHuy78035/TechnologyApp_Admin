package com.example.technology_app.models.Statistic;

public class Statistic {
    String product;
    int sold;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Statistic(String product, int sold) {
        this.product = product;
        this.sold = sold;
    }
}
