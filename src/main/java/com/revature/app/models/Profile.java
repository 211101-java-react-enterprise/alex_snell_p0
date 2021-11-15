package com.revature.app.models;

import java.util.Objects;
import java.util.regex.Pattern;

public class Profile {

    static private final Pattern validName = Pattern.compile("^[A-z][A-z,'-.]+$");
    static private final Pattern validEmail = Pattern.compile("^[A-z][A-z0-9.@-_]+$");

    private String id;
    private String email;
    private String firstName;
    private String lastName ;

    public Profile() {
        this.email = "";
        this.firstName = "";
        this.lastName = "";
    }

    public Profile(String email, String firstName, String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    public Profile(String id, String email, String firstName, String lastName) {
        this(firstName, lastName, email);
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public boolean setFirstName(String firstName) {
        if (Profile.isValidName(firstName)) {
            this.firstName = firstName;
            return true;
        }
        return false;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean setLastName(String lastName) {
        if (Profile.isValidName(lastName)) {
            this.lastName = lastName;
            return true;
        }
        return false;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean setEmail(String email) {
        if (Profile.isValidEmail(email)) {
            this.email = email;
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return Profile.validEmail.matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        return Profile.validName.matcher(name).matches();
    }

    public static boolean isValidProfile(Profile profile) {
        return Profile.isValidEmail(profile.getEmail()) && Profile.isValidName(profile.getFirstName()) && Profile.isValidName(profile.getFirstName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return Objects.equals(id, profile.id) && Objects.equals(firstName, profile.firstName) && Objects.equals(lastName, profile.lastName) && Objects.equals(email, profile.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.email, this.firstName, this.lastName);
    }


}
