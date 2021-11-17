package com.revature.app.util;

import com.revature.app.util.screens.Screen;
import com.revature.app.util.collections.LinkedList;

public class ScreenRouter {

    private final LinkedList<Screen> screens;

    public ScreenRouter() {
        screens = new LinkedList<Screen>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public void navigate(String route) {
        for (int i = 0; i < screens.size(); i++) {
            Screen thisScreen = screens.get(i);
            if (thisScreen.getRoute().equals(route)) {
                thisScreen.render();
            }
        }
    }
}
