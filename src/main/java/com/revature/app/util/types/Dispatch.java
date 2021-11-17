package com.revature.app.util.types;

import java.util.function.Consumer;

public interface Dispatch {
    void dispatch(Action a,Consumer<StateAction> c);
}
