package com.tds.screen;

import com.tds.TDS;
import com.tds.input.InputService;

/**
 * Default implementation that wires concrete dependencies.
 */
public class DefaultScreenFactory implements ScreenFactory {
    private final TDS game;
    private final InputService input;
    private final RenderStrategy renderStrategy;

    public DefaultScreenFactory(TDS game, InputService input, RenderStrategy renderStrategy) {
        this.game = game;
        this.input = input;
        this.renderStrategy = renderStrategy;
    }

    @Override
    public GameScreen createGameScreen() {
        return new GameScreen(game, input, renderStrategy);
    }
}
