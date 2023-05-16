package com.salesManagement.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Order {
    private char[] orderId;
    private Date orderDate;
    private Customer customer;
    private Product product;
    private int amount;

    public Order() {
    }

    public Order(Date orderDate, Customer customer, Product product, int amount) {
        orderId = ("OD" + (new Random().nextInt(99999999) + 100000000)).toCharArray();
        this.orderDate = orderDate;
        this.customer = customer;
        this.product = product;
        this.amount = amount;
    }

    public Order(char[] orderId, Date orderDate, Customer customer, Product product, int amount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.product = product;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + Arrays.toString(orderId) +
                ", orderDate=" + orderDate +
                ", customer=" + customer +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }

    public char[] getOrderId() {
        return orderId;
    }
    public void setOrderId(char[] orderId) {
        this.orderId = orderId;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
