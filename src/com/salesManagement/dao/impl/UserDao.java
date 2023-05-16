package com.salesManagement.dao.impl;

import com.salesManagement.dao.face.IUserDao;
import com.salesManagement.model.User;
import com.salesManagement.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDao implements IUserDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Override
    public List<User> getAll() {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM users";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
            return users;
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
    public User get(String userName) {
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM users WHERE userName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            return user;
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
    public int save(User user) {
        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO users(userId, userName, userPass) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPass());
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
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(String userId) {
        return 0;
    }
}
