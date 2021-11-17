package com.revature.app.util;

import com.revature.app.daos.CourseDAO;
import com.revature.app.daos.UserDAO;
import com.revature.app.services.CourseService;
import com.revature.app.services.UserService;
import com.revature.app.util.logging.Logger;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.logging.types.LogPrinter;
import com.revature.app.util.screens.*;
import com.revature.app.util.types.StateAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AppState {

    private StateAction stateAction;
    private final Logger logger;
    public final ScreenRouter router;
    private final BufferedReader reader;
    public final UserDAO userDAO;
    public final CourseDAO courseDAO;
    public final UserService userService;
    public final CourseService courseService;

    public StateAction getStateAction() {
        return stateAction;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public UserService getUserService() {
        return userService;
    }

    public void log(LogLevel level, String message) {
        this.logger.log(level, message);
    }

    public void navigate(String screen) {
        if (this.stateAction == StateAction.LOOP) {
            this.router.navigate(screen);
        }
    }

    public String readLine() {
        if (this.stateAction.equals(StateAction.LOOP)) {
            try {
                return this.reader.readLine();
            } catch (IOException e) {
                this.log(LogLevel.FATAL, e.getMessage());
                this.dispatch(StateAction.EXIT);
            }
        }
        return null;
    }

    private AppState() {

        this.stateAction = StateAction.LOOP;
        this.logger = Logger.getLogger(LogPrinter.CONSOLE);
        this.userDAO = new UserDAO();
        this.courseDAO = new CourseDAO();
        this.userService = new UserService(this.userDAO);
        this.courseService = new CourseService(this.courseDAO, this.userService);
        this.router = new ScreenRouter();
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        logger.log(LogLevel.INFO, "Initializing application");

        router.addScreen(new WelcomeScreen(StateAction.EXIT, this));
        router.addScreen(new RegisterScreen(StateAction.EXIT, this));
        router.addScreen(new LoginScreen(StateAction.EXIT, this));
        router.addScreen(new DashboardScreen(StateAction.EXIT, this));
        router.addScreen(new MyCoursesScreen(StateAction.EXIT, this));
        router.addScreen(new CreateCourseScreen(StateAction.EXIT, this));
    }

    private static int guard = 0;

    public static AppState initAppStateOnce() {
        if (AppState.guard == 0) {
            AppState.guard = 1;
            return new AppState();
        }
        return null;
    }

    public void dispatch(StateAction stateAction) {
        if (stateAction != null) {
            this.reducer(stateAction);
        }
    }

    private void reducer(StateAction stateAction) {
        if (stateAction != null) {
            if (!this.stateAction.equals(stateAction)) {
                switch (stateAction) {
                    case LOOP:
                        this.stateAction = StateAction.LOOP;
                        this.start();
                        break;
                    case EXIT:
                        this.stateAction = StateAction.EXIT;
                        break;
                }
            }
        }
    }

    public void start () {
        try {
            while (this.stateAction.equals(StateAction.LOOP)) {
                this.router.navigate("/welcome");
            }
        } catch (Exception e) {
            this.log(LogLevel.ERROR, e.toString());
        }
        this.log(LogLevel.INFO, "Main loop exited without issue, finishing shutdown");
    }

    public void stop() {
        this.stateAction = StateAction.EXIT;
        this.log(LogLevel.INFO, "program state changed to EXIT for normal shutdown");
    }
}
