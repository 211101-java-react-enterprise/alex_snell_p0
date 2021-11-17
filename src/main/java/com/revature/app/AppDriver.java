package com.revature.app;

import com.revature.app.util.AppState;
import com.revature.app.util.logging.types.LogLevel;
import com.revature.app.util.types.StateAction;

public class AppDriver {
    public static void main(String[] args) {
        AppState appState = AppState.initAppStateOnce();
        appState.start();

    }
}