package com.salesManagement.dao.impl;

import com.salesManagement.dao.face.IOrderDao;
import com.salesManagement.model.*;
import com.salesManagement.service.UserService;
import com.salesManagement.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDao implements IOrderDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private UserService userService = UserService.getInstance();

    @Override
    public List<Order> getAll() {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                        "JOIN customers ON orders.customerId = customers.customerId " +
                        "JOIN products ON orders.productId = products.productId " +
                        "WHERE customers.userId = ? AND products.userId = customers.userId";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                    resultSet.getTimestamp(3),
                                    new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                    new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                            )
                );
            }
            return orders;
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
    public Order getByOrderId(char[] orderId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                        "JOIN customers ON orders.customerId = customers.customerId " +
                        "JOIN products ON orders.productId = products.productId " +
                        "WHERE orders.orderId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(orderId));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Order(resultSet.getString(2).toCharArray(),
                    resultSet.getTimestamp(3),
                    new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                    new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
            );
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
    public int save(Order order) {
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO orders(orderId, customerId , productId, amount) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(order.getOrderId()));
            preparedStatement.setString(2, String.valueOf(order.getCustomer().getCustomerId()));
            preparedStatement.setString(3, String.valueOf(order.getProduct().getProductId()));
            preparedStatement.setInt(4, order.getAmount());
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
    public int update(Order order) {
        try {
            connection = DBUtils.getConnection();
            String sql = "UPDATE orders SET customerId = ?, productId = ?, amount = ? WHERE orderId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(order.getCustomer().getCustomerId()));
            preparedStatement.setString(2, String.valueOf(order.getProduct().getProductId()));
            preparedStatement.setInt(3, order.getAmount());
            preparedStatement.setString(4, String.valueOf(order.getOrderId()));
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
    public int delete(char[] orderId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "DELETE FROM orders WHERE orderId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(orderId));
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
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
    public List<Order> getByDate(String year, String month, String day) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE CONVERT(DATE(orders.orderDate), CHAR) LIKE ? AND customers.userId = ?";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            if (year.equals("Year")) {
                year = "%";
            }
            if (month.equals("Month")) {
                month = "%";
            }
            if (day.equals("Day")) {
                day = "%";
            }
            preparedStatement.setString(1, year + "-" + month + "-" + day);
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<Order> getByCustomerId(char[] customerId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE orders.customerId = ? ";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerId));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<Order> getByProductId(char[] productId) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE orders.productId LIKE ? ";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(productId));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<Order> getByCustomerName(String customerName) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE customers.name LIKE ? AND customers.userId = ?";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + customerName + "%");
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<Order> getByProductName(String productName) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE products.name LIKE ? AND products.userId = ?";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + productName + "%");
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<Order> getProductByPrice(double price) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE products.price LIKE ? AND products.userId = ?";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(price));
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<Order> getByCustomerPhoneNum(String phoneNum) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE customers.phoneNum LIKE ? AND customers.userId = ?";
            List<Order> orders = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + phoneNum + "%");
            preparedStatement.setString(2, userService.getCurrentUser().getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(2).toCharArray(),
                                resultSet.getTimestamp(3),
                                new Customer(resultSet.getString(8).toCharArray(), resultSet.getString(9).toCharArray(), resultSet.getString(10).toCharArray()),
                                new Product(resultSet.getString(13).toCharArray(), resultSet.getString(14).toCharArray(), resultSet.getDouble(15)), resultSet.getInt(6)
                        )
                );
            }
            return orders;
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
    public List<List<String>> orderOverViewByCustomer(String year, String month, String day) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT DATE(orders.orderDate) AS date, customers.name,  SUM(orders.amount*products.price) FROM orders " +
                    "JOIN customers ON orders.customerId = customers.customerId " +
                    "JOIN products ON orders.productId = products.productId " +
                    "WHERE customers.userId = ?" +
                    "GROUP BY customers.name, DATE(orders.orderDate)" +
                    "HAVING CONVERT((date), CHAR) LIKE ?" +
                    "ORDER BY date DESC";
            preparedStatement = connection.prepareStatement(sql);
            if (year.equals("Year")) {
                year = "%";
            }
            if (month.equals("Month")) {
                month = "%";
            }
            if (day.equals("Day")) {
                day = "%";
            }
            preparedStatement.setString(2, year + "-" + month + "-" + day);
            preparedStatement.setString(1, userService.getCurrentUser().getUserId());
            List<List<String>> overViewList = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<String> data = new ArrayList<>();
                data.add(resultSet.getString(1));
                data.add(resultSet.getString(2));
                data.add(resultSet.getString(3));
                overViewList.add(data);
            }
            return overViewList;
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
    public List<List<String>> orderOverViewByProduct(String year, String month, String day) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT DATE(orders.orderDate) AS date, products.name, SUM(orders.amount), SUM(orders.amount*products.price) FROM orders " +
                        "JOIN customers ON orders.customerId = customers.customerId " +
                        "JOIN products ON orders.productId = products.productId " +
                        "WHERE customers.userId = ?" +
                        "GROUP BY products.name, DATE(orders.orderDate)" +
                        "HAVING CONVERT((date), CHAR) LIKE ?" +
                        "ORDER BY date DESC";
            preparedStatement = connection.prepareStatement(sql);
            if(year.equals("Year")) {
                year = "%";
            }
            if(month.equals("Month")) {
                month = "%";
            }
            if(day.equals("Day")) {
                day = "%";
            }
            preparedStatement.setString(2, year + "-" + month + "-" + day);
            preparedStatement.setString(1, userService.getCurrentUser().getUserId());
            List<List<String>> overViewList = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<String> data = new ArrayList<>();
                data.add(resultSet.getString(1));
                data.add(resultSet.getString(2));
                data.add(resultSet.getString(3));
                data.add(resultSet.getString(4));
                overViewList.add(data);
            }
            return overViewList;
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