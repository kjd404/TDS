package com.tds.screen;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.TDS;
import com.tds.input.InputHandler;
import com.tds.input.InputService;
import com.tds.score.ScoreRepository;
import org.junit.Before;
import org.junit.Test;

public class MenuScreenTest {

    private static class StubScoreRepository implements ScoreRepository {
        private int high;

        @Override
        public int getHighScore() {
            return high;
        }

        @Override
        public void submitScore(int score) {
            high = Math.max(high, score);
        }

        @Override
        public void reset() {
            high = 0;
        }
    }

    private static class StubGame extends TDS {
        Screen lastScreen;

        StubGame(InputService input, ScoreRepository repo) {
            super(() -> mock(SpriteBatch.class), mock(AssetManager.class), input, repo, mock(RenderStrategy.class));
        }

        @Override
        public void setScreen(Screen screen) {
            lastScreen = screen;
        }
    }

    private static class FakeFactory implements ScreenFactory {
        boolean called;
        GameScreen produced;
        private final TDS game;
        private final InputService input;

        FakeFactory(TDS game, InputService input) {
            this.game = game;
            this.input = input;
        }

        @Override
        public GameScreen createGameScreen() {
            called = true;
            produced = new GameScreen(game, input, new OrthographicRenderStrategy(800, 600));
            return produced;
        }
    }

    private static class TestInput extends MockInput {
        @Override
        public boolean isKeyJustPressed(int keycode) {
            return keycode == Input.Keys.ENTER;
        }
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

    @Before
    public void setup() {
        if (Gdx.app == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationAdapter() {}, config);
        }
        Gdx.files = new TestFiles();
        GL20 gl = (GL20) java.lang.reflect.Proxy.newProxyInstance(
                GL20.class.getClassLoader(), new Class[] {GL20.class}, (proxy, method, args) -> {
                    Class<?> r = method.getReturnType();
                    if (r.equals(Boolean.TYPE)) return false;
                    if (r.equals(Integer.TYPE)) return 0;
                    if (r.equals(Float.TYPE)) return 0f;
                    if (r.equals(Double.TYPE)) return 0d;
                    if (r.equals(Long.TYPE)) return 0L;
                    return null;
                });
        Gdx.gl20 = gl;
        Gdx.gl = gl;
    }

    private static class TestableMenuScreen extends MenuScreen {
        private final TDS g;
        private final ScreenFactory f;

        TestableMenuScreen(TDS g, InputService i, ScreenFactory f) {
            super(g, i, f);
            this.g = g;
            this.f = f;
        }

        @Override
        public void render(float delta) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                g.setScreen(f.createGameScreen());
            }
        }
    }

    @Test
    public void pressingEnterRequestsNewScreen() {
        InputService inputService = new InputHandler();
        StubGame game = new StubGame(inputService, new StubScoreRepository());
        FakeFactory factory = new FakeFactory(game, inputService);
        MenuScreen menu = new TestableMenuScreen(game, inputService, factory);

        Gdx.input = new TestInput();
        menu.render(0f);

        assertTrue(factory.called);
        assertSame(factory.produced, game.lastScreen);
    }
}
