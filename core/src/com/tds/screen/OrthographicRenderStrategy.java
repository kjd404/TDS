package com.tds.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Render strategy using a standard orthographic camera and fit viewport.
 */
public class OrthographicRenderStrategy implements RenderStrategy {

    private final OrthographicCamera camera;
    private final Viewport viewport;

    public OrthographicRenderStrategy(float worldWidth, float worldHeight) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0f);
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void apply(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
