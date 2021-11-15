package com.revature.app.models;

import com.revature.app.models.Password;
import com.revature.app.models.Profile;

import java.util.Objects;
import java.util.regex.Pattern;

public class User {

    static private final Pattern validUsername = Pattern.compile("^[A-z][A-z0-9_-]+$");

    private String id;
    private String username;
    private final Profile profile;
    private final Password credentials;

    public User(String username, Profile profile, Password credentials) {
        this.username = username;
        this.profile = profile;
        this.credentials = credentials;
    }

    public User(String id, String username, Profile profile, Password credentials) {
        this(username, profile, credentials);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        return User.validUsername.matcher(username).matches();
    }

    public static boolean isValidUser(User user) {
        return User.isValidUsername(user.getUsername()) && Profile.isValidProfile(user.profile) && Password.isValidPassword(user.credentials);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username) && Objects.equals(this.credentials, user.credentials) && Objects.equals(this.profile, user.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.credentials, this.profile);
    }

}
