package com.tds.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tds.TDS;
import com.tds.input.InputService;
import com.tds.input.InputService.Action;

public class MenuScreen extends ScreenAdapter {
    private final TDS game;
    private final InputService input;
    private final ScreenFactory screenFactory;
    private BitmapFont font;

    public MenuScreen(TDS game, InputService input, ScreenFactory screenFactory) {
        this.game = game;
        this.input = input;
        this.screenFactory = screenFactory;
    }

    @Override
    public void show() {
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        font.draw(game.batch, "Press ENTER to Start", 100, 100);
        game.batch.end();

        if (input.isActionPressed(Action.START)) {
            game.setScreen(screenFactory.createGameScreen());
        }
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
