package com.tds.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Strategy interface for configuring render state.
 */
public interface RenderStrategy {
    /**
     * @return camera used for rendering
     */
    Camera getCamera();

    /**
     * @return viewport controlling the camera
     */
    Viewport getViewport();

    /**
     * Update the camera and apply its projection to the batch.
     *
     * @param batch sprite batch to configure
     */
    void apply(SpriteBatch batch);

    /**
     * Update the viewport on window resize.
     *
     * @param width  new viewport width
     * @param height new viewport height
     */
    void resize(int width, int height);
}
