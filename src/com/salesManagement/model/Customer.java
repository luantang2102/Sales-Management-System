package com.salesManagement.model;


import java.util.Random;

public class Customer {

    private char[] customerId;
    private char[] name;
    private char[] phoneNum;

    public Customer() {
    }

    public Customer(char[] name, char[] phoneNum) {
        customerId = ("CS" + (new Random().nextInt(99999999) + 100000000)).toCharArray();
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public Customer(char[] customerId, char[] name, char[] phoneNum) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId='" + String.valueOf(customerId) + '\'' +
                ", name='" + String.valueOf(name) + '\'' +
                ", phoneNum=" + String.valueOf(phoneNum) +
                '}';
    }

    public char[] getCustomerId() {
        return customerId;
    }
    public void setId(char[] customerId) {
        this.customerId = customerId;
    }
    public char[] getName() {
        return name;
    }
    public void setName(char[] name) {
        this.name = name;
    }
    public char[] getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(char[] phoneNum) {
        this.phoneNum = phoneNum;
    }
}