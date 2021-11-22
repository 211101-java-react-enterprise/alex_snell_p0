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
        return this.courseDAO.findAllCourses();
    }

    public void deleteCourse(Course course) {
        if (!isCourseValid(course)) {
            throw new InvalidRequestException("Invalid course information values provided!");
        }

        this.courseDAO.deleteById(course.getId());
    }

    public void deleteRegistration(String userId, String courseId) {
        this.courseDAO.deleteRegistrationById(userId, courseId);
    }

    public void createRegistration(String userId, String courseId) {
        this.courseDAO.createRegistrationById(userId, courseId);
    }

    public void updateCourse(Course course) {

        if (!isCourseValid(course)) {
            throw new InvalidRequestException("Invalid course information values provided!");
        }

        Course updatedCourse = this.courseDAO.update(course);

        if (updatedCourse == null) {
            throw new ResourcePersistenceException("The course could not be persisted to the datasource!");
        }

    }

    public List<Course> findUnregisteredCourses() {
        return this.courseDAO.findRegistrationByNotUserId(this.userService.getSessionUser().getId());
    }

    public List<Course> findMyCourses() {
        switch (this.userService.getSessionUser().getRole()) {
            case "student":
                return this.courseDAO.findRegistrationsByUserId(this.userService.getSessionUser().getId());
            case "teacher":
                return this.courseDAO.findCoursesByCreatorId(this.userService.getSessionUser().getId());
            default:
                return new LinkedList<>();
        }
    }

    public void createNewCourse(Course course) {

        if (!this.isCourseValid(course)) {
            throw new InvalidRequestException("Invalid course information values provided!");
        }

        Course newCourse = this.courseDAO.save(course);

        if (newCourse == null) {
            throw new ResourcePersistenceException("The course could not be persisted to the datasource!");
        }

    }

    public boolean isCourseValid(Course course) {
        if (course == null) return false;
        if (course.getName() == null) return false;
        if (course.getProgram() == null) return false;
        if (course.getLevel() == null) return false;
        if (course.getDescription() == null) return false;
        return true;
    }


}

