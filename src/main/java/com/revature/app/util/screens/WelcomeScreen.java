package com.revature.app.util.screens;

import com.revature.app.util.AppState;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;


public class WelcomeScreen extends Screen {

    public WelcomeScreen(StateAction action, AppState state) {
        super("WelcomeScreen", "/welcome", action, state);
    }

    @Override
    public void render() {

        System.out.print("\nWelcome to your CLI Course Registration!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n" +
                "> ");

        String userSelection = this.state.readLine();

        switch (userSelection) {
            case "1":
                this.state.router.navigate("/login");
                break;
            case "2":
                this.state.router.navigate("/register");
                break;
            case "3":
                this.state.log(LogLevel.INFO, "Exiting application...");
                this.state.stop();
                break;
            default:
                System.out.println("You have made an invalid selection");
        }

    }
}

