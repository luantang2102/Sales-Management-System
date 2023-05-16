package com.salesManagement.dao.impl;

import com.salesManagement.dao.face.IProductDao;
import com.salesManagement.model.Product;
import com.salesManagement.service.UserService;
import com.salesManagement.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDao implements IProductDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    UserService userService = UserService.getInstance();

    @Override
    public List<Product> getAll() {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM products WHERE userId = ?";
            List<Product> products = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getDouble(4)));
            }
            return products;
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
    public Product getByProductId(char[] productId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM products WHERE productId = ? AND userId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(productId));
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Product product = new Product(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getDouble(4));
            return product;
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
    public int save(Product product) {
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO products(productId, name, price, userId) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(product.getProductId()));
            preparedStatement.setString(2, String.valueOf(product.getName()));
            preparedStatement.setDouble(3, product.getPrice());
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
    public int update(Product product) {
        try {
            connection = DBUtils.getConnection();
            String sql = "UPDATE products SET name = ?, price = ? WHERE productId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(product.getName()));
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, String.valueOf(product.getProductId()));
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
    public int delete(char[] productId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "DELETE FROM products WHERE productId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(productId));
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
    public List<Product> getByName(String name) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM products WHERE products.name LIKE ? AND userID = ?";
            List<Product> products = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, '%' + name + '%');
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getDouble(4)));
            }
            return products;
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
    public List<Product> getByPrice(double price) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM products WHERE products.price = ? AND userId = ?";
            List<Product> products = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(price));
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getString(2).toCharArray(), resultSet.getString(3).toCharArray(), resultSet.getDouble(4)));
            }
            return products;
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
