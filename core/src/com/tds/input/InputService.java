package com.tds.input;

import com.badlogic.gdx.InputProcessor;

/**
 * Service interface for querying and configuring input actions.
 */
public interface InputService extends InputProcessor {

    /** High level actions available in the game. */
    enum Action {
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN,
        DASH,
        FIRE
    }

    /**
     * Returns whether the given action is currently pressed.
     */
    boolean isActionPressed(Action action);

    /**
     * Registers a callback to run when an action is triggered.
     */
    void register(Action action, Runnable callback);

    /**
     * Binds an action to a specific key or button code.
     */
    void bind(Action action, int code);
}
