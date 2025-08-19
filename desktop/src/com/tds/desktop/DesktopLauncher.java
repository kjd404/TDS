package com.tds.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tds.GameBootstrap;
import com.tds.TDS;
import com.tds.screen.OrthographicRenderStrategy;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        DisplayMode mode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        int width = mode.width;
        int height = mode.height;
        config.setWindowedMode(width, height);
        TDS game = new GameBootstrap()
                .withRenderStrategy(new OrthographicRenderStrategy(width, height))
                .bootstrap();
        new Lwjgl3Application(game, config);
    }
}
