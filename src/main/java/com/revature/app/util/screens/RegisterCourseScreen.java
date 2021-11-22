package com.revature.app.util.screens;

import com.revature.app.models.Course;
import com.revature.app.util.AppState;
import com.revature.app.util.collections.List;
import com.revature.app.util.types.StateAction;

public class RegisterCourseScreen extends Screen {

    public RegisterCourseScreen(StateAction action, AppState state) {
        super("RegisterCourseScreen", "/registercourse", action, state);
    }

    @Override
    public void render() {
        List<Course> myCourses = this.state.courseService.findAllCourses();
        if (myCourses.isEmpty()) {
            System.out.println("There are no available unregistered courses!");
            System.out.println("Returning to dashboard");
            this.state.router.navigate("/dashboard");
            return;
        }
        System.out.println("Available courses: \n");
        for (int i = 0; i < myCourses.size(); i++) {
            Course course = myCourses.get(i);
            System.out.println("ID: " + i + " Course: " + course.getLevel() + " " + course.getProgram() + " " + course.getName());
        }
        Course targetCourse;
        try {
            System.out.println("Enter a course ID to register for the course or nothing to return to the Dashboard.");
            int coursesListIndex = Integer.parseInt(this.state.readLine());
            targetCourse = myCourses.get(coursesListIndex);
            this.state.courseService.createRegistration(this.state.userService.getSessionUser().getId(), targetCourse.getId());
            System.out.println("Registered course and returning to dashboard.");
            this.state.router.navigate("/dashboard");
        } catch (Exception e) {
            System.out.println("Returning to dashboard...");
            this.state.router.navigate("/dashboard");
        }
    }
}