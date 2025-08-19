package com.tds.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

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
        FIRE,
        START,
        QUIT
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

    /**
     * Returns the current pointer position in world coordinates using the given
     * {@link Viewport} for the screen-to-world projection.
     *
     * @param viewport the viewport used to unproject the pointer
     * @return the pointer position in world space
     */
    Vector2 getPointer(Viewport viewport);
}
