package com.tds.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete {@link InputService} implementation backed by LibGDX input APIs.
 */
public class InputHandler extends InputAdapter implements InputService {

    private final Map<Action, Integer> bindings = new EnumMap<>(Action.class);
    private final Map<Action, Boolean> states = new EnumMap<>(Action.class);
    private final Map<Action, List<Runnable>> listeners = new EnumMap<>(Action.class);

    public InputHandler() {
        // Default bindings
        bindings.put(Action.MOVE_LEFT, Input.Keys.A);
        bindings.put(Action.MOVE_RIGHT, Input.Keys.D);
        bindings.put(Action.MOVE_UP, Input.Keys.W);
        bindings.put(Action.MOVE_DOWN, Input.Keys.S);
        bindings.put(Action.DASH, Input.Keys.SHIFT_LEFT);
        bindings.put(Action.FIRE, Input.Buttons.LEFT);
        bindings.put(Action.START, Input.Keys.ENTER);
        bindings.put(Action.QUIT, Input.Keys.ESCAPE);

        for (Action action : Action.values()) {
            states.put(action, false);
            listeners.put(action, new ArrayList<Runnable>());
        }
    }

    @Override
    public void bind(Action action, int code) {
        bindings.put(action, code);
    }

    @Override
    public void register(Action action, Runnable callback) {
        listeners.get(action).add(callback);
    }

    @Override
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

    @Override
    public Vector2 getPointer(Viewport viewport) {
        Vector2 coords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(coords);
        return coords;
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
