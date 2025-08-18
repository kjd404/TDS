package com.tds.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tds.Admin;
import com.tds.Virus;
import com.tds.assets.AnimationSet;
import com.tds.platform.FakeGraphicsContext;
import com.tds.weapons.ParticleSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
import com.tds.input.InputService.Action;

/**
 * Verifies that {@link Admin#processMovement(java.util.ArrayList, com.badlogic.gdx.utils.viewport.Viewport)}
 * reacts to input provided via {@link InputService}.
 */
public class AdminInputServiceTest {

    private static class StubInputService extends com.badlogic.gdx.InputAdapter implements InputService {
        private final EnumSet<Action> pressed = EnumSet.noneOf(Action.class);

        @Override
        public boolean isActionPressed(Action action) {
            return pressed.contains(action);
        }

        @Override
        public void register(Action action, Runnable callback) {
            // no-op
        }

        @Override
        public void bind(Action action, int code) {
            // no-op
        }
    }

    @Before
    public void setup() {
        if (Gdx.app == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationAdapter(){}, config);
        }
        Gdx.input = new MockInput();
        Gdx.files = new TestFiles();
    }

    private AnimationSet createAnimations() {
        Texture texture = new DummyTexture();
        TextureRegion region = new TextureRegion(texture, 1, 1);
        Animation<TextureRegion> anim = new Animation<>(0.1f, region);
        return new AnimationSet(anim, anim, anim, anim);
    }

    private static class DummyTexture extends com.badlogic.gdx.graphics.Texture {
        public DummyTexture() { super(); }

        @Override
        public int getWidth() { return 1; }

        @Override
        public int getHeight() { return 1; }

        @Override
        public int getDepth() { return 0; }

        @Override
        public com.badlogic.gdx.graphics.TextureData getTextureData() { return null; }

        @Override
        public boolean isManaged() { return false; }

        @Override
        protected void reload() { }
    }

    private static class NoopParticleSystem implements ParticleSystem {
        @Override public void shoot(float secondsOfLife, float fireRate, float angle, float x, float y, float speed) {}
        @Override public void process(java.util.ArrayList<Virus> enemies) {}
        @Override public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {}
    }

    private static class TestFiles implements Files {
        @Override
        public FileHandle getFileHandle(String fileName, FileType type) {
            return new FileHandle(fileName);
        }

        @Override
        public FileHandle classpath(String path) {
            return new FileHandle(path);
        }

        @Override
        public FileHandle internal(String path) {
            return new FileHandle("core/assets/" + path);
        }

        @Override
        public FileHandle external(String path) {
            return new FileHandle(path);
        }

        @Override
        public FileHandle absolute(String path) {
            return new FileHandle(path);
        }

        @Override
        public FileHandle local(String path) {
            return new FileHandle(path);
        }

        @Override
        public String getExternalStoragePath() {
            return "";
        }

        @Override
        public boolean isExternalStorageAvailable() {
            return false;
        }

        @Override
        public String getLocalStoragePath() {
            return "";
        }

        @Override
        public boolean isLocalStorageAvailable() {
            return true;
        }
    }

    @Test
    public void movesLeftWhenActionPressed() {
        StubInputService input = new StubInputService();
        input.pressed.add(Action.MOVE_LEFT);
        FakeGraphicsContext graphics = new FakeGraphicsContext();
        graphics.setDeltaTime(1f);
        Admin admin = new Admin(1, 3, 1, 10, createAnimations(), input, new NoopParticleSystem(), graphics);
        ScreenViewport viewport = new ScreenViewport(new OrthographicCamera());
        viewport.setWorldSize(100, 100);
        viewport.getCamera().position.set(0,0,0);
        viewport.getCamera().update();
        admin.setPosition(5, 0);
        admin.processMovement(new ArrayList<Virus>(), viewport);
        assertEquals(-5f, admin.getX(), 0.001f);
    }
}
