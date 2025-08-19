package com.tds.screen;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tds.TDS;
import com.tds.input.InputHandler;
import com.tds.input.InputService.Action;
import org.junit.Test;

public class GameScreenQuitActionTest {

    @Test
    public void pressingEscapeExitsApplication() {
        InputHandler input = new InputHandler();
        Application app = mock(Application.class);
        Gdx.app = app;

        RenderStrategy render = mock(RenderStrategy.class);
        OrthographicCamera cam = new OrthographicCamera();
        ScreenViewport viewport = new ScreenViewport(cam);
        when(render.getCamera()).thenReturn(cam);
        when(render.getViewport()).thenReturn(viewport);

        TDS game = mock(TDS.class);
        GameScreen screen = new GameScreen(game, input, render) {
            @Override
            public void render(float delta) {
                if (input.isActionPressed(Action.QUIT)) {
                    Gdx.app.exit();
                }
            }
        };

        input.keyDown(Input.Keys.ESCAPE);
        screen.render(0f);

        verify(app).exit();
    }
}
