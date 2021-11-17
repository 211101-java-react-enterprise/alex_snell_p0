package com.revature.app.services;


import com.revature.app.daos.CourseDAO;
import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;
import com.revature.app.models.Course;
import com.revature.app.util.collections.LinkedList;
import com.revature.app.util.collections.List;

public class CourseService {
    private final CourseDAO courseDAO;
    private final UserService userService;

    public CourseService(CourseDAO courseDAO, UserService userService) {
        this.courseDAO = courseDAO;
        this.userService = userService;
    }

    public List<Course> findAllCourses() {
        return this.courseDAO.findAll();
    }

    public List<Course> findMyCourses() {
        switch (this.userService.getSessionUser().getRole()) {
            case "student":
                return this.courseDAO.findRegistrationByUserId(this.userService.getSessionUser().getId());
            case "teacher":
                return this.courseDAO.findCoursesByCreatorId(this.userService.getSessionUser().getId());
            default:
                return new LinkedList<>();
        }
    }

    public void createNewCourse(Course course) {

        if (!isCourseValid(course)) {
            throw new InvalidRequestException("Invalid course information values provided!");
        }

        Course newCourse = this.courseDAO.save(course);

        if (newCourse == null) {
            throw new ResourcePersistenceException("The course could not be persisted to the datasource!");
        }

    }

    public boolean isCourseValid(Course course) {
        if (course == null) return false;
        if (course.getName() == null || course.getName().trim().equals("")) return false;
        if (course.getProgram() == null || course.getProgram().trim().equals("")) return false;
        if (course.getLevel() == null || course.getName().trim().equals("")) return false;
        if (course.getDescription() == null || course.getName().trim().equals("")) return false;
        return (course.getCreator() == null);
    }


}

