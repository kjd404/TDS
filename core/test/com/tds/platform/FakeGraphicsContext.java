package com.tds.platform;

public class FakeGraphicsContext implements GraphicsContext {
    private float deltaTime;
    private int width;
    private int height;

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float getDeltaTime() {
        return deltaTime;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
