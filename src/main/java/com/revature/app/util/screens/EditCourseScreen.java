package com.revature.app.util.screens;

import com.revature.app.models.Course;
import com.revature.app.util.AppState;
import com.revature.app.util.collections.List;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;

public class EditCourseScreen extends Screen {

    public EditCourseScreen(StateAction action, AppState state) {
        super("EditCourseScreen", "/editcourse", action, state);
    }

    @Override
    public void render() {

        List<Course> myCourses = this.state.courseService.findMyCourses();

        if (myCourses.isEmpty()) {
            System.out.println("You are not administering any courses!");
            System.out.println("Returning to dashboard");
            this.state.router.navigate("/dashboard");
            return;
        }
        System.out.println("Your courses: \n");
        for (int i = 0; i < myCourses.size(); i++) {
            Course course = myCourses.get(i);
            System.out.println("ID: " + i + " Course: " + course.getLevel() + " " + course.getProgram() + " " + course.getName());
        }
        Course targetCourse;
        System.out.println("Select course by Id to edit. When editing enter nothing to keep original value.");
        try {
            int coursesListIndex = Integer.parseInt(this.state.readLine());
            targetCourse = myCourses.get(coursesListIndex);
            System.out.println("Enter the new academic program course will be administered under");

            System.out.print("Program (E.g Psych, Bio, Crim) : ");
            String program = this.state.readLine();
            if (program.length() == 0) {
                program = targetCourse.getProgram();
            }
            System.out.println("Enter the new course number");
            System.out.print("Course Number: ");
            String level = this.state.readLine();
            if (level.length() == 0) {
                level = targetCourse.getLevel();
            }
            System.out.println("Enter the course title");
            System.out.print("Title: ");
            String name = this.state.readLine();
            if (name.length() == 0) {
                name = targetCourse.getName();
            }
            System.out.println("Enter a short course description");
            System.out.print("Description: ");
            String description = this.state.readLine();
            if (description.length() == 0) {
                description = targetCourse.getDescription();
            }
            Course updatedCourse = new Course(targetCourse.getId(), this.state.userService.getSessionUser(), program, level, name, description);
            this.state.courseService.updateCourse(updatedCourse);
            System.out.println("Course update succesful!");
            this.state.router.navigate("/dashboard");
        } catch (Exception e) {
            this.state.log(LogLevel.ERROR, e.getMessage());
            System.out.println("Invalid selection returning to dashboard...");
            this.state.router.navigate("/dashboard");
        }

        System.out.println("Returning to dashboard...");
        this.state.router.navigate("/dashboard");

    }

}
