package com.revature.app.services;


import com.revature.app.daos.CourseDAO;
import com.revature.app.models.Course;
import com.revature.app.util.collections.List;

public class CourseService {
    private final CourseDAO courseDAO;
    private final UserService userService;

    public CourseService(CourseDAO courseDAO, UserService userService) {
        this.courseDAO = courseDAO;
        this.userService = userService;
    }

    public List<Course> findAllCourses() {
        return this.courseDao.findAll();
    }

    public List<Course> findMyCourses() {
        return null;
    }

    public void createNewCourse(Course newCourse) {

    }

}

