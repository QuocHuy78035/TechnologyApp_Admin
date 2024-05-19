package com.example.technology_app.models.UpdateOrder;

import com.example.technology_app.models.GetOrder.CheckOut.Checkout;
import com.example.technology_app.models.GetOrder.Payment;

import java.util.List;

public class Order {
    String _id;
    String user;
    Checkout checkout;
    String shipping_address;
    Payment payment;
    int coin;
    List<Product> products;
    String status;
    String phone;
    String paymentStatus;
    String createdAt;
    String updatedAt;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Order(String _id, String user, Checkout checkout, String shipping_address, Payment payment, int coin, List<Product> products, String status, String phone, String paymentStatus, String createdAt, String updatedAt) {
        this._id = _id;
        this.user = user;
        this.checkout = checkout;
        this.shipping_address = shipping_address;
        this.payment = payment;
        this.coin = coin;
        this.products = products;
        this.status = status;
        this.phone = phone;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
