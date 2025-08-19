package com.tds.input;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Input;
import com.tds.input.InputService.Action;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;

/**
 * Tests for {@link InputHandler} verifying action binding and callbacks.
 */
public class InputHandlerTest {

    @Test
    public void reportsActionStateForCustomBinding() {
        InputHandler input = new InputHandler();
        input.bind(Action.MOVE_LEFT, Input.Keys.Z);
        assertFalse(input.isActionPressed(Action.MOVE_LEFT));
        input.keyDown(Input.Keys.Z);
        assertTrue(input.isActionPressed(Action.MOVE_LEFT));
        input.keyUp(Input.Keys.Z);
        assertFalse(input.isActionPressed(Action.MOVE_LEFT));
    }

    @Test
    public void invokesRegisteredCallbackOnAction() {
        InputHandler input = new InputHandler();
        AtomicBoolean called = new AtomicBoolean(false);
        input.bind(Action.MOVE_RIGHT, Input.Keys.X);
        input.register(Action.MOVE_RIGHT, () -> called.set(true));
        input.keyDown(Input.Keys.X);
        assertTrue(called.get());
    }
}
