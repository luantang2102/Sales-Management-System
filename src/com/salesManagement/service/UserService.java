package com.salesManagement.service;

import com.salesManagement.dao.impl.UserDao;
import com.salesManagement.model.User;

import java.util.List;

public class UserService {
    private static UserService instance;
    private UserService() {
    }
    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserDao userDao = new UserDao();
    private User user = new User();

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User getUser(String userId){
        user = userDao.get(userId);
        return user;
    }
    public User getCurrentUser(){
        return user;
    }

    public int save(User user) {
        return userDao.save(user);
    }
}
