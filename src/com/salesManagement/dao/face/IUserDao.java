package com.salesManagement.dao.face;
import com.salesManagement.model.User;

import java.util.List;

public interface IUserDao {
    List<User> getAll();
    User get(String userName);
    int save(User user);
    int update(User user);
    int delete(String userId);
}
