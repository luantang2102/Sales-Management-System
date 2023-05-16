package com.salesManagement.dao.face;

import com.salesManagement.model.Customer;

import java.util.List;

public interface ICustomerDao {
    List<Customer> getAll();
    Customer getById(char[] id);
    int save(Customer t);
    int update(Customer t);
    int delete(char[] customerId);

    List<Customer> getByName(String name);
    List<Customer> getByPhoneNum(String phoneNum);
}
