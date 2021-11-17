package com.revature.app.util.screens;

import com.revature.app.util.AppState;
import com.revature.app.util.logging.Logger;
import com.revature.app.util.logging.types.LogPrinter;
import com.revature.app.util.types.StateAction;

/**
 * Key Differences between Abstract Classes and Interfaces:
 *
 *      - Abstract classes can have instance fields (that are not implicitly public, static, final)
 *      - Abstract classes can have methods with implementation that are not declared as default
 *      - Abstract class method stubs must be explicitly declared as "abstract"
 */
public abstract class Screen {

    protected Logger logger = Logger.getLogger(LogPrinter.FILE);
    protected String name;
    protected String route;
    protected StateAction action;
    protected AppState state;

    protected Screen(String name, String route, StateAction action, AppState state) {
        this.name = name;
        this.route = route;
        this.action = action;
        this.state = state;
    }

    public final String getName() {
        return this.name;
    }

    public final String getRoute() {
        return this.route;
    }

    public abstract void render();

}
