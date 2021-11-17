package com.revature.app.util.screens;

import com.revature.app.models.Course;
import com.revature.app.util.AppState;
import com.revature.app.util.collections.List;
import com.revature.app.util.types.StateAction;

public class MyCoursesScreen extends Screen {

    public MyCoursesScreen(StateAction action, AppState state) {
        super("MyCoursesScreen", "/mycourses", action, state);
    }

    @Override
    public void render() {
        List<Course> myCourses = this.state.courseService.findMyCourses();

        switch (this.state.getUserService().getSessionUser().getRole()) {
            case "student":
                if (myCourses.isEmpty()) {
                    System.out.println("You have not registered to any courses!");
                    System.out.println("Returning to dashboard");
                    this.state.router.navigate("/dashboard");
                    return;
                }
                System.out.println("Your courses: \n");
                for (int i = 0; i < myCourses.size(); i++) {
                    Course course = myCourses.get(i);
                    System.out.println("Course: " + course.getName());
                    System.out.println("Teacher: " + course.getCreator().getFirstName() + " " + course.getCreator().getLastName() + "\n");
                    System.out.println("Description: " + course.getDescription() + "\n");
                }
                break;
            case "teacher":
                if (myCourses.isEmpty()) {
                    System.out.println("You not administering any courses!");
                    System.out.println("Returning to dashboard");
                    this.state.router.navigate("/dashboard");
                    return;
                }
                System.out.println("Your courses: \n");
                for (int i = 0; i < myCourses.size(); i++) {
                    Course course = myCourses.get(i);
                    System.out.println("Course: " + course.getName());
                    System.out.println("Description: " + course.getDescription() + "\n");
                }
                break;

        }

        System.out.println("Returning to dashboard...");
        this.state.router.navigate("/dashboard");

    }

}
