package com.revature.app.util.screens;

import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;

import com.revature.app.models.Course;
import com.revature.app.util.AppState;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;

public class CreateCourseScreen extends Screen {

    public CreateCourseScreen(StateAction action, AppState state) {
        super("CreateCourseScreen", "/createcourse", action, state);
    }

    @Override
    public void render() {
        System.out.println("Register new course to administer");
        System.out.println("Enter the academic program course will be administered under");

        System.out.print("Program (E.g Psych, Bio, Crim) : ");
        String program = this.state.readLine();

        System.out.println("Enter the course number");
        System.out.print("Course Number: ");
        String level = this.state.readLine();

        System.out.println("Enter the course program");
        System.out.print("Title: ");
        String name = this.state.readLine();

        System.out.println("Enter a short course description");
        System.out.print("Description: ");
        String description = this.state.readLine();

        Course newCourse = new Course(this.state.userService.getSessionUser(), program, level, name, description);

        try {
            this.state.courseService.createNewCourse(newCourse);
            System.out.println("Course creation succeeded!");
            this.state.router.navigate("/dashboard");
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            this.state.log(LogLevel.ERROR, e.getMessage());
        } catch (Exception e) {
            this.state.log(LogLevel.ERROR, e.getMessage());
        }
    }

}
