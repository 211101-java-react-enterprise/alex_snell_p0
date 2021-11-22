package com.revature.app.util.screens;

import com.revature.app.models.Course;
import com.revature.app.util.AppState;
import com.revature.app.util.collections.List;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;

public class DeleteCourseScreen extends Screen {

        public DeleteCourseScreen(StateAction action, AppState state) {
            super("DeleteCourseScreen", "/deletecourse", action, state);
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
            try {
                System.out.println("Enter a course ID to delete the course or nothing to return to the Dashboard.");
                int coursesListIndex = Integer.parseInt(this.state.readLine());
                targetCourse = myCourses.get(coursesListIndex);
                this.state.courseService.deleteCourse(targetCourse);
                System.out.println("Deleted course and returning to dashboard.");
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
