package com.revature.app.util.screens;

import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;

import com.revature.app.models.User;
import com.revature.app.util.AppState;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;

public class RegisterScreen extends Screen {

    public RegisterScreen(StateAction action, AppState state) {
        super("RegisterScreen", "/register", action, state);
    }

    @Override
    public void render() {
        System.out.println("Register new user");
        System.out.println("Enter your email to begin registration.");

        System.out.print("Email: ");
        String email = this.state.readLine();

        System.out.println("Enter an easy to remember password to secure your account with.");
        System.out.print("Password: ");
        String password = this.state.readLine();

        System.out.println("Enter your first and last name [Will be visible to faculty administrering registered courses.]");
        System.out.print("First Name: ");
        String firstName = this.state.readLine();
        System.out.print("Last Name: ");
        String lastName = this.state.readLine();

        User newUser = new User(email, password, firstName, lastName);

        try {
            this.state.userService.registerNewUser(newUser);
            System.out.println("Registration successful!");
            this.state.router.navigate("/login");
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            this.state.log(LogLevel.ERROR, e.getMessage());
        } catch (Exception e) {
            this.state.log(LogLevel.ERROR, e.getMessage());
        }
    }

}
