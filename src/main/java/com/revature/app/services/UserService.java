package com.revature.app.services;

import com.revature.app.daos.UserDAO;
import com.revature.app.exceptions.AuthenticationException;
import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;
import com.revature.app.models.User;


public class UserService {

    private final UserDAO userDAO;
    private User sessionUser;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.sessionUser = null;
    }

    public User getSessionUser() {
        return this.sessionUser;
    }

    public void authenticateUser(String email, String password) {

        if (email == null || email.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        User authenticatedUser = userDAO.findByEmailAndPassword(email, password);

        if (authenticatedUser == null) {
            throw new AuthenticationException();
        }

        sessionUser = authenticatedUser;

    }


    public boolean registerNewUser (User newUser){

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        boolean emailAvailable = userDAO.findByEmail(newUser.getEmail()) == null;

        if (!emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users:";
            if (!emailAvailable) msg = msg + "\n\t- email";
            throw new ResourcePersistenceException(msg);
        }

        User registeredUser = userDAO.save(newUser);

        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }

        return true;

    }

    public void logout() {
        sessionUser = null;
    }

    public boolean isSessionActive() {
        return sessionUser != null;
    }

    public boolean isUserValid(User user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

}
