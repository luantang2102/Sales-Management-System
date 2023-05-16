package com.salesManagement.model;

import java.util.Random;

public class Product {
    private char[] productId;
    private char[] name;
    private double price;

    public Product() {}

    public Product(char[] name, double price) {
        productId = ("PD" + (new Random().nextInt(99999999) + 100000000)).toCharArray();
        this.name = name;
        this.price = price;
    }

    public Product(char[] productId, char[] name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + String.valueOf(productId) +
                ", name=" + String.valueOf(name) +
                ", price=" + price +
                '}';
    }

    public char[] getProductId() {
        return productId;
    }
    public void setProductId(char[] productId) {
        this.productId = productId;
    }
    public char[] getName() {
        return name;
    }
    public void setName(char[] name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
