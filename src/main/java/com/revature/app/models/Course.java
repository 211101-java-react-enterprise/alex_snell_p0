package com.revature.app.models;

import java.util.Objects;

public class Course {

    private String id;
    private String subjectPrefix;
    private String subjectTile;
    private String level;
    private String name;
    private String description;
    private int maxSeats;
    private int currentSeats;

    public Course(String subjectPrefix, String subjectTile, String level, String name, String description, int maxSeats, int currentSeats) {
        this.subjectPrefix = subjectPrefix;
        this.subjectTile = subjectTile;
        this.level = level;
        this.name = name;
        this.description = description;
        this.maxSeats = maxSeats;
        this.currentSeats = currentSeats;
    }

    public Course(String id, String subjectPrefix, String subjectTile, String level, String name, String description, int maxSeats, int currentSeats) {
        this(subjectPrefix, subjectTile, level, name, description, maxSeats, currentSeats);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    public void setSubjectPrefix(String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
    }

    public String getSubjectTile() {
        return subjectTile;
    }

    public void setSubjectTile(String subjectTile) {
        this.subjectTile = subjectTile;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getCurrentSeats() {
        return currentSeats;
    }

    public void setCurrentSeats(int currentSeats) {
        this.currentSeats = currentSeats;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return maxSeats == course.maxSeats && currentSeats == course.currentSeats && id.equals(course.id) && subjectPrefix.equals(course.subjectPrefix) && subjectTile.equals(course.subjectTile) && level.equals(course.level) && name.equals(course.name) && description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subjectPrefix, subjectTile, level, name, description, maxSeats, currentSeats);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", subjectPrefix='" + subjectPrefix + '\'' +
                ", subjectTile='" + subjectTile + '\'' +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", maxSeats=" + maxSeats +
                ", currentSeats=" + currentSeats +
                '}';
    }


}
