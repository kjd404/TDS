package com.tds.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Centralized input handler that maps high level actions to concrete keys or
 * buttons. Consumers can query the state of an action or register callbacks
 * that fire when an action is triggered.
 */
public class InputHandler extends InputAdapter {

    /** High level actions available in the game. */
    public enum Action {
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN,
        DASH,
        FIRE
    }

    private static final InputHandler INSTANCE = new InputHandler();

    private final Map<Action, Integer> bindings = new EnumMap<>(Action.class);
    private final Map<Action, Boolean> states = new EnumMap<>(Action.class);
    private final Map<Action, List<Runnable>> listeners = new EnumMap<>(Action.class);

    private InputHandler() {
        // Default bindings
        bindings.put(Action.MOVE_LEFT, Input.Keys.A);
        bindings.put(Action.MOVE_RIGHT, Input.Keys.D);
        bindings.put(Action.MOVE_UP, Input.Keys.W);
        bindings.put(Action.MOVE_DOWN, Input.Keys.S);
        bindings.put(Action.DASH, Input.Keys.SHIFT_LEFT);
        bindings.put(Action.FIRE, Input.Buttons.LEFT);

        for (Action action : Action.values()) {
            states.put(action, false);
            listeners.put(action, new ArrayList<Runnable>());
        }
    }

    /**
     * @return global {@link InputHandler} instance.
     */
    public static InputHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Binds an action to a specific key or button.
     */
    public void bind(Action action, int code) {
        bindings.put(action, code);
    }

    /**
     * Registers a callback to run when an action is triggered.
     */
    public void register(Action action, Runnable callback) {
        listeners.get(action).add(callback);
    }

    /**
     * Returns whether an action is currently active.
     */
    public boolean isActionPressed(Action action) {
        Boolean value = states.get(action);
        return value != null && value.booleanValue();
    }

    @Override
    public boolean keyDown(int keycode) {
        handlePress(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        handleRelease(keycode);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        handlePress(button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        handleRelease(button);
        return false;
    }

    private void handlePress(int code) {
        for (Map.Entry<Action, Integer> entry : bindings.entrySet()) {
            if (entry.getValue() == code) {
                states.put(entry.getKey(), true);
                for (Runnable r : listeners.get(entry.getKey())) {
                    r.run();
                }
            }
        }
    }

    private void handleRelease(int code) {
        for (Map.Entry<Action, Integer> entry : bindings.entrySet()) {
            if (entry.getValue() == code) {
                states.put(entry.getKey(), false);
            }
        }
    }
}
