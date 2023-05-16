package com.salesManagement.dao.face;
import com.salesManagement.model.Customer;
import com.salesManagement.model.Order;
import com.salesManagement.model.Product;

import java.util.Date;
import java.util.List;

public interface IOrderDao {
    List<Order> getAll();
    Order getByOrderId(char[] id);
    int save(Order t);
    int update(Order t);
    int delete(char[] orderId);

    List<Order> getByDate(String year, String month, String day);
    List<Order> getByCustomerId(char[] customerId);
    List<Order> getByCustomerName(String customerName);
    List<Order> getByCustomerPhoneNum(String phoneNum);
    List<Order> getByProductId(char[] productId);
    List<Order> getByProductName(String productName);
    List<Order> getProductByPrice(double price);
    List<List<String>> orderOverViewByCustomer(String year, String month, String day);
    List<List<String>> orderOverViewByProduct(String year, String month, String day);
}
