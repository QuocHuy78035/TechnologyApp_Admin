package com.example.technology_app.models.GetOrder;

import com.example.technology_app.models.GetOrder.CheckOut.Checkout;
import com.example.technology_app.models.GetOrder.products.Products;

import java.util.List;

public class Order {
    private String _id;
    private User user;
    private Checkout checkout;
    private String shipping_address;
    private Payment payment;
    private String trackingNumber;
    private int coin;
    private String voucher;
    private  List<Products> products;
    private String status;
    private String phone;
    private String deliveredDate;
    private String paymentStatus;
    private String updatedAt;

    public Order(String _id, User user, Checkout checkout, String shipping_address, Payment payment, String trackingNumber, int coin, String voucher, List<Products> products, String status, String phone, String deliveredDate, String paymentStatus, String updatedAt) {
        this._id = _id;
        this.user = user;
        this.checkout = checkout;
        this.shipping_address = shipping_address;
        this.payment = payment;
        this.trackingNumber = trackingNumber;
        this.coin = coin;
        this.voucher = voucher;
        this.products = products;
        this.status = status;
        this.phone = phone;
        this.deliveredDate = deliveredDate;
        this.paymentStatus = paymentStatus;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
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

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
