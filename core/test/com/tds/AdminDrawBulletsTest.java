package com.tds;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tds.assets.AnimationSet;
import com.tds.input.InputService;
import com.tds.platform.FakeGraphicsContext;
import com.tds.weapons.ParticleSystem;
import org.junit.Test;

public class AdminDrawBulletsTest {
    private static class DummyTexture extends Texture {
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

    private AnimationSet createAnimations() {
        Texture texture = new DummyTexture();
        TextureRegion region = new TextureRegion(texture, 1, 1);
        Animation<TextureRegion> anim = new Animation<>(0.1f, region);
        return new AnimationSet(anim, anim, anim, anim);
    }

    private static class StubInputService extends com.badlogic.gdx.InputAdapter implements InputService {
        @Override
        public boolean isActionPressed(InputService.Action action) {
            return false;
        }

        @Override
        public void register(InputService.Action action, Runnable callback) {}

        @Override
        public void bind(InputService.Action action, int code) {}
    }

    private static class RecordingParticleSystem implements ParticleSystem {
        boolean drawn = false;

        @Override
        public void shoot(float secondsOfLife, float fireRate, float angle, float x, float y, float speed) {}

        @Override
        public void process(java.util.ArrayList<Virus> enemies) {}

        @Override
        public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
            drawn = true;
        }
    }

    @Test
    public void drawsBulletsThroughParticleSystem() {
        RecordingParticleSystem ps = new RecordingParticleSystem();
        FakeGraphicsContext graphics = new FakeGraphicsContext();
        Admin admin = new Admin(1, 3, 1, 10, createAnimations(), new StubInputService(), ps, graphics);
        admin.drawBullets(null);
        assertTrue(ps.drawn);
    }
}
