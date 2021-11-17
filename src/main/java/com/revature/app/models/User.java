package com.revature.app.models;

import java.util.Objects;
import java.util.regex.Pattern;

public class User {

    private static final Pattern validEmail = Pattern.compile("^[A-z][A-z0-9.@-_]+$");

    private String id;
    private String role;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User() {};

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String id, String role, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return true;
    }

    public static boolean isValidUser(User user) {
        return User.isValidEmail(user.getEmail());
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return (Objects.equals(this.id, user.id) && Objects.equals(this.email, user.email) && this.password.equals(user.password));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.email, this.password, this.firstName, this.lastName);
    }


}
