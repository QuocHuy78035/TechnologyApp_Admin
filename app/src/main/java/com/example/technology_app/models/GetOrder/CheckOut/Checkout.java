package com.example.technology_app.models.GetOrder.CheckOut;

import java.util.List;

public class Checkout {
    private int totalPrice;
    private int totalApplyDiscount;
    private int feeShip;
    private int total;
    private String _id;
    private List<String> priceOfProducts;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalApplyDiscount() {
        return totalApplyDiscount;
    }

    public void setTotalApplyDiscount(int totalApplyDiscount) {
        this.totalApplyDiscount = totalApplyDiscount;
    }

    public int getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(int feeShip) {
        this.feeShip = feeShip;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getPriceOfProducts() {
        return priceOfProducts;
    }

    public void setPriceOfProducts(List<String> priceOfProducts) {
        this.priceOfProducts = priceOfProducts;
    }

    public Checkout(int totalPrice, int totalApplyDiscount, int feeShip, int total, String _id, List<String> priceOfProducts) {
        this.totalPrice = totalPrice;
        this.totalApplyDiscount = totalApplyDiscount;
        this.feeShip = feeShip;
        this.total = total;
        this._id = _id;
        this.priceOfProducts = priceOfProducts;
    }
}
