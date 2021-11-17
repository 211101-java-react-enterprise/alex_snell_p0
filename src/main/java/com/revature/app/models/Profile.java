package com.revature.app.models;

import java.util.Objects;
import java.util.regex.Pattern;

public class Profile {

    private static final Pattern validName = Pattern.compile("^[A-z][A-z],'-.]+$");


    private String id;
    private String firstName;
    private String lastName ;

    public Profile() {
        this.firstName = "";
        this.lastName = "";
    }

    public Profile(String firstName, String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Profile(String id, String firstName, String lastName) {
        this(firstName, lastName);
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




    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        return Profile.validName.matcher(name).matches();
    }

    public static boolean isValidProfile(Profile profile) {
        return Profile.isValidName(profile.getFirstName()) && Profile.isValidName(profile.getLastName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return Objects.equals(id, profile.id) && Objects.equals(firstName, profile.firstName) && Objects.equals(lastName, profile.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName);
    }


}
