package com.revature.app.models;

import java.util.Objects;

public class Course {

    private String id;
    private String level;
    private String program;
    private String name;
    private String description;
    private User creator;

    public Course() {};

    public Course(User creator, String program,String level, String name, String description) {
        this.program = program;
        this.level = level;
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public Course(String id, User creator, String creatorId, String program, String level, String name, String description) {
        this.id = id;
        this.program = program;
        this.level = level;
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProgram() {
        return this.program;
    }

    public void setProgram(String program) {
        this.program = program;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return this.name.equals(course.name) && this.description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, name, description);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
