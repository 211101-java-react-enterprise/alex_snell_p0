package com.revature.app.daos;

import com.revature.app.models.Course;
import com.revature.app.util.collections.List;

public class CourseDAO implements EntityDAO<Course> {

    public List<Course> findAll() {
        return null;
    }

    public List<Course> findAllWhereIdIsOwner(String id) {
        return null;
    }

    public List<Course> findAllWhereIdIsRegistered(String id) {
        return null;
    }

    public List<Course> findAllWhereIdIsAvailableToRegister(String id) {
        return null;
    }

    public Course findByName(String name) {
        return null;
    }

    @Override
    public Course findById(String id) {
        return null;
    }



    @Override
    public Course save(Course newCourse) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Course update(Course updatedCourse) {
        return null;
    }

}
