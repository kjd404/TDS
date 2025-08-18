package com.tds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.screen.MenuScreen;

public class TDS extends Game {
    public SpriteBatch batch;
    public AssetManager assetManager;

    @Override
    public void create () {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("badlogic.jpg", Texture.class);
        assetManager.load("background.png", Texture.class);
        assetManager.load("virus.png", Texture.class);
        assetManager.finishLoading();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        assetManager.dispose();
    }
}
