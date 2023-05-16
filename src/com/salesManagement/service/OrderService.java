package com.salesManagement.service;

import com.salesManagement.dao.face.IOrderDao;
import com.salesManagement.dao.impl.OrderDao;
import com.salesManagement.model.*;
import java.util.List;

public class OrderService {

    private IOrderDao orderDao = new OrderDao();

    public List<Order> getAll() {
        return orderDao.getAll();
    }

    public Order getByOrderId(char[] orderId) {
        return orderDao.getByOrderId(orderId);
    }

    public int save(Order order) {
        return orderDao.save(order);
    }

    public int update(Order order){
        return orderDao.update(order);
    }

    public int delete(char[] orderId) {
        return orderDao.delete(orderId);
    }

    public List<Order> getByDate(String year, String month, String day) {
        return orderDao.getByDate(year, month, day);
    }
    public List<Order> getByCustomerId(char[] customerId) {
        return orderDao.getByCustomerId(customerId);
    }
    public List<Order> getByCustomerName(String customerName){
        return orderDao.getByCustomerName(customerName);
    }
    public List<Order> getByCustomerPhoneNum(String phoneNum) {
        return orderDao.getByCustomerPhoneNum(phoneNum);
    }
    public List<Order> getByProductId(char[] productId) {
        return orderDao.getByProductId(productId);
    }
    public List<Order> getByProductName(String productName) {
        return orderDao.getByProductName(productName);
    }
    public List<Order> getByPrice(double price){
        return orderDao.getProductByPrice(price);
    }
    public List<List<String>> orderOverView(String content, String year, String month, String day) {
        if(content.equals("Customer")) return orderDao.orderOverViewByCustomer(year, month, day);
        else return orderDao.orderOverViewByProduct(year, month, day);
    }
}
