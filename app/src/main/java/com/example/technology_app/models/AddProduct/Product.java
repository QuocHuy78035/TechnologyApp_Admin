package com.example.technology_app.models.AddProduct;

import java.util.List;

public class Product {
    String _id;
    String name;
    List<String> images;
    String linkytb;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getLinkytb() {
        return linkytb;
    }

    public void setLinkytb(String linkytb) {
        this.linkytb = linkytb;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public List<String> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(List<String> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Product(String _id, String name, List<String> images, String linkytb, String sale_price, List<String> extraInfo, String type, Description description, String category, int sold, int left, int view, int rating, String thumb) {
        this._id = _id;
        this.name = name;
        this.images = images;
        this.linkytb = linkytb;
        this.sale_price = sale_price;
        this.extraInfo = extraInfo;
        this.type = type;
        this.description = description;
        this.category = category;
        this.sold = sold;
        this.left = left;
        this.view = view;
        this.rating = rating;
        this.thumb = thumb;
    }

    String sale_price;
    List<String> extraInfo;
    String type;
    Description description;
    String category;
    int sold;
    int left;
    int view;
    int rating;
    String thumb;
}
