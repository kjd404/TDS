package com.tds.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.tds.platform.GraphicsContext;

/**
 * Factory for creating {@link ParticleSystem} instances.
 */
public class ParticleSystemFactory {
    private final GraphicsContext graphics;

    public ParticleSystemFactory(GraphicsContext graphics) {
        this.graphics = graphics;
    }

    public ParticleSystem create(Texture texture) {
        return new ProjectileSystem(texture, graphics);
    }
}
