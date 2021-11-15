package com.revature.app.services;

import com.revature.app.daos.UserDAO;
import com.revature.app.exceptions.AuthenticationException;
import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;
import com.revature.app.models.User;
import com.revature.app.models.Profile;
import com.revature.app.models.Password;

public class UserService {

    private final UserDAO userDAO;
    private User sessionUser;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.sessionUser = null;
    }

    public User registerNewUser(UserStub userStub) throws InvalidRequestException {
        Password newCredentials = new Password(userStub.password);
        Profile newProfile = new Profile(userStub.email, userStub.firstName, userStub.lastName);
        User newUser = new User(userStub.username, newProfile, newCredentials);

        if (!User.isValidUser(newUser)) {
            throw new InvalidRequestException("Invalid user data provided");
        }

        if (!userDAO.findUserByUsername(newUser.getUsername())) {
            throw new ResourcePersistenceException("That username is already taken!");
        }

        User registeredUser = userDAO.create(newUser);

        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }

        return registeredUser;
    }

    public void authenticateUser(UserStub stub) throws AuthenticationException {
        if (!User.isValidUsername(stub.username) || !Password.isValidPasswordText(stub.password)) {
            throw new InvalidRequestException("Invalid credentials provided");
        }

        User authenticatedUser = userDAO.findUserByUsernameAndPassword(stub.username, stub.password);
        if (authenticatedUser == null) {
            throw new AuthenticationException();
        }

        sessionUser = authenticatedUser;
    }

    public void logout() {
        sessionUser = null;
    }

    public boolean isSessionActive() {
        return sessionUser != null;
    }

    public static class UserStub {
        public String username;
        public String password;
        public String email;
        public String firstName;
        public String lastName;

        public UserStub(String username, String password) {
            this.username = username.trim();
            this.password = password.trim();
        }

        public UserStub(String username, String password, String email, String firstName, String lastName) {
            this.username = username.trim();
            this.password = password.trim();
            this.email = email.trim();
            this.firstName = firstName.trim();
            this.lastName = lastName.trim();
        }
    }

}
