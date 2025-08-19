package com.tds;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import org.junit.Test;

public class EntityCollisionTest {
    private static class DummyTexture extends Texture {
        public DummyTexture() {
            super();
        }

        @Override
        public int getWidth() {
            return 128;
        }

        @Override
        public int getHeight() {
            return 128;
        }

        @Override
        public int getDepth() {
            return 0;
        }

        @Override
        public TextureData getTextureData() {
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
    public void collisionsUseExpectedRadii() {
        Texture texture = new DummyTexture();
        Entity e1 = new Entity(1, 0, texture, 0, 0, 64, 64);
        Entity e2 = new Entity(1, 0, texture, 0, 0, 64, 64);

        e1.getBoundingCircle().setPosition(0, 0);
        e2.getBoundingCircle().setPosition(64, 0);
        assertFalse(e1.checkCollision(e2));

        e1.setSize(128, 128);
        e1.getBoundingCircle().setPosition(0, 0);
        e2.getBoundingCircle().setPosition(90, 0);
        assertTrue(e1.checkCollision(e2));
    }
}
