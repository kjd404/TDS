package com.tds.platform;

import com.badlogic.gdx.Gdx;

public class GdxGraphicsContext implements GraphicsContext {
    @Override
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public int getWidth() {
        return Gdx.graphics.getWidth();
    }

    @Override
    public int getHeight() {
        return Gdx.graphics.getHeight();
    }
}
