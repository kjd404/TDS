package com.tds.weapons;

import static org.junit.Assert.*;

import com.tds.Virus;
import com.tds.platform.FakeGraphicsContext;
import java.util.ArrayList;
import org.junit.Test;

public class ProjectileSystemTest {
    private static class DummyTexture extends com.badlogic.gdx.graphics.Texture {
        public DummyTexture() {
            super();
        }

        @Override
        public int getWidth() {
            return 1;
        }

        @Override
        public int getHeight() {
            return 1;
        }

        @Override
        public int getDepth() {
            return 0;
        }

        @Override
        public com.badlogic.gdx.graphics.TextureData getTextureData() {
            return null;
        }

        @Override
        public boolean isManaged() {
            return false;
        }

        @Override
        protected void reload() {}
    }

    @Test
    public void removesParticlesWhenLifeExpires() {
        FakeGraphicsContext graphics = new FakeGraphicsContext();
        graphics.setDeltaTime(1f);
        ProjectileSystem system = new ProjectileSystem(new DummyTexture(), graphics);
        system.process(new ArrayList<Virus>());
        system.shoot(1f, 0.5f, 0f, 0f, 0f, 0f);
        assertEquals(1, system.particles.size());
        system.process(new ArrayList<Virus>());
        assertEquals(0, system.particles.size());
    }
}
