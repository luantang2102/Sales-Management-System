package com.salesManagement.service;

import com.salesManagement.dao.face.ICustomerDao;
import com.salesManagement.dao.impl.CustomerDao;
import com.salesManagement.model.*;

import java.util.List;

public class CustomerService {
    private ICustomerDao customerDao = new CustomerDao();
    private Customer customer = new Customer();
    public List<Customer> getAll() {
        return customerDao.getAll();
    }
    public Customer getByCustomerId(char[] customerId) {
        return customerDao.getById(customerId);
    }
    public int save(Customer customer) {
        return customerDao.save(customer);
    }
    public int update(Customer customer){
        return customerDao.update(customer);
    }
    public int delete(char[] customerId) {
        return customerDao.delete(customerId);
    }

    public List<Customer> getByName(String name) {
        return customerDao.getByName(name);
    }
    public List<Customer> getByPhoneNum(String phoneNum) {
        return customerDao.getByPhoneNum(phoneNum);
    }

}
