package com.revature.app.util.screens;

import com.revature.app.models.User;
import com.revature.app.util.AppState;
import com.revature.app.util.types.StateAction;

public class DashboardScreen extends Screen {

    public DashboardScreen(StateAction action, AppState state) {
        super("DashboardScreen", "/dashboard", action, state);
    }

    @Override
    public void render() {

        User sessionUser = this.state.userService.getSessionUser();
        if (sessionUser == null) {
            System.out.println("You are not currently logged in! Navigating to Welcome Screen");
            this.state.router.navigate("/welcome");
            return;
        }

        while (this.state.userService.isSessionActive()) {
            System.out.printf("\n%s's Course Management Dashboard", sessionUser.getFirstName());
            System.out.printf("\nCourse View: %s\n", sessionUser.getRole());

            String menu;
            switch(sessionUser.getRole()) {
                case "student":
                    menu = "1) View and drop currently registered courses\n" +
                            "2) View and register for new courses\n" +
                            "3) View all registered courses\n" +
                            "4) Logout\n" +
                            "> ";
                    break;
                case "teacher":
                    menu = "1) View and edit currently administered courses\n" +
                            "2) Create new course\n" +
                            "3) View all administered courses\n" +
                            "4) View and delete currently administered courses\n" +
                            "5) Logout\n" +
                            "> ";
                    break;
                default:
                    menu = "No valid role's associated with your account!\n" +
                            "1) Logout\n" +
                            "> ";
            }


            System.out.print(menu);

            String userSelection = this.state.readLine();
            switch(sessionUser.getRole()) {
                case "student":
                    switch (userSelection) {
                        case "1":
                            System.out.println("View/drop registered courses");
                            this.state.router.navigate("/dropcourse");
                            break;
                        case "2":
                            System.out.println("View/join new courses");
                            this.state.router.navigate("/registercourse");
                            break;
                        case "3":
                            System.out.println("View registered courses");
                            this.state.router.navigate("/mycourses");
                            break;
                        case "4":
                            this.state.userService.logout();
                            break;
                        default:
                            System.out.println("You have made an invalid selection");
                    }
                    break;
                case "teacher":
                    switch (userSelection) {
                        case "1":
                            System.out.println("View/edit administered courses");
                            this.state.router.navigate("/editcourse");
                            break;
                        case "2":
                            System.out.println("Create new course");
                            this.state.router.navigate("/createcourse");
                            break;
                        case "3":
                            System.out.println("View all administered courses");
                            this.state.router.navigate("/mycourses");
                            break;
                        case "4":
                            System.out.println("View/delete currently administered courses");
                            this.state.router.navigate("/deletecourse");
                            break;
                        case "5":
                            this.state.userService.logout();
                            break;
                        default:
                            System.out.println("You have made an invalid selection");
                    }
                    break;
                default:
                    switch (userSelection) {
                        case "1":
                            this.state.userService.logout();
                            break;
                        default:
                            System.out.println("You have made an invalid selection");
                            break;
                    }
            }

        }

    }
}
