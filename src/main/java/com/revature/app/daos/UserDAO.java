package com.revature.app.daos;

import com.revature.app.models.User;

public class UserDAO implements EntityDAO<User> {

    public boolean findUserByUsername(String username) {
        return false;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public User save(User newUser) {
        return null;
    }

    @Override
    public void deleteById(String id) {
      // TODO complete method stub
    }

    @Override
    public User update(User updatedUser) {
        return null;
    }

}
