package com.tds.screen;

/**
 * Factory for creating LibGDX screens.
 */
public interface ScreenFactory {
    /**
     * Create a new {@link GameScreen} instance.
     *
     * @return fresh game screen
     */
    GameScreen createGameScreen();
}
