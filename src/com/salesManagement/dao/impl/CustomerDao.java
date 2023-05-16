package com.salesManagement.dao.impl;

import com.salesManagement.dao.face.ICustomerDao;
import com.salesManagement.model.*;
import com.salesManagement.service.UserService;
import com.salesManagement.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerDao implements ICustomerDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private UserService userService = UserService.getInstance();

    @Override
    public List<Customer> getAll() {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM customers where userId = ?";

            List<Customer> customers = new ArrayList<>();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getString(4).toCharArray()));
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Customer getById(char[] customerId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM customers WHERE customerId = ? AND userId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerId));
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Customer customer = new Customer(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getString(4).toCharArray());
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int save(Customer customer) {
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO customers(customerId, name, phoneNum, userId) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customer.getCustomerId()));
            preparedStatement.setString(2, String.valueOf(customer.getName()));
            preparedStatement.setString(3, String.valueOf(customer.getPhoneNum()));
            preparedStatement.setString(4, userService.getCurrentUser().getUserId());
            int i = preparedStatement.executeUpdate();
            if(i>0) {
                System.out.println("Added!");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Add failed :(");
        return 0;
    }

    @Override
    public int update(Customer customer) {
        try {
            connection = DBUtils.getConnection();
            String sql = "UPDATE customers SET name = ?, phoneNum = ? WHERE customerId = ? and userId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customer.getName()));
            preparedStatement.setString(2, String.valueOf(customer.getPhoneNum()));
            preparedStatement.setString(3, String.valueOf(customer.getCustomerId()));
            preparedStatement.setString(4, userService.getCurrentUser().getUserId());
            int i = preparedStatement.executeUpdate();
            if(i>0) {
                System.out.println("Updated!");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Update failed :(");
        return 0;
    }

    @Override
    public int delete(char[] customerId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "DELETE FROM customers WHERE customerId = ? AND userId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerId));
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            int i = preparedStatement.executeUpdate();
            if(i>0) {
                System.out.println("Deleted!");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Delete failed :(");
        return 0;
    }

    @Override
    public List<Customer> getByName(String name) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM customers WHERE customers.name LIKE ? AND userId = ?";

            List<Customer> customers = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getString(4).toCharArray()));
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<Customer> getByPhoneNum(String phoneNum) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM customers WHERE customers.phoneNum LIKE ? AND userId = ?";
            List<Customer> customers = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + phoneNum + "%");
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getString(4).toCharArray()));
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtils.close(connection, preparedStatement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }
}
