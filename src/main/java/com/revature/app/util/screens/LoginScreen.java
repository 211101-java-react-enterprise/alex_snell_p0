package com.revature.app.util.screens;

import com.revature.app.exceptions.AuthenticationException;
import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.util.AppState;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;


public class LoginScreen extends Screen {

    public LoginScreen(StateAction action, AppState state) {
        super("LoginScreen", "/login", action, state);
    }

    @Override
    public void render()  {

        System.out.println("\nPlease provide your credentials to log into your account.");
        System.out.print("Email: ");
        String email = this.state.readLine();
        System.out.print("Password: ");
        String password = this.state.readLine();

        try {
            this.state.userService.authenticateUser(email, password);
            this.state.log(LogLevel.INFO, String.format("Successful authentication of user: %s at %d", email, System.currentTimeMillis()));
            this.state.router.navigate("/dashboard");
        } catch (InvalidRequestException | AuthenticationException e) {
            System.out.println(e.getMessage());
        }

    }

}
