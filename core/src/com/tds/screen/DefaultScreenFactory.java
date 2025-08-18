package com.tds.screen;

import com.tds.TDS;
import com.tds.input.InputService;

/**
 * Default implementation that wires concrete dependencies.
 */
public class DefaultScreenFactory implements ScreenFactory {
    private final TDS game;
    private final InputService input;

    public DefaultScreenFactory(TDS game, InputService input) {
        this.game = game;
        this.input = input;
    }

    @Override
    public GameScreen createGameScreen() {
        return new GameScreen(game, input);
    }
}
