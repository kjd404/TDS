package com.tds;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tds.assets.AnimationSet;
import com.tds.assets.AnimationSetFactory;
import com.tds.input.InputService;
import com.tds.score.ScoreRepository;
import com.tds.screen.GameScreen;
import com.tds.screen.RenderStrategy;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

public class WallTextureTest {
    private static class DummyTexture extends Texture {
        DummyTexture() {
            super(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        }
    }

    @Before
    public void setup() {
        if (Gdx.app == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationAdapter() {}, config);
        }
    }

    @Test
    public void requestsBrickTextureOnlyOnce() throws Exception {
        AssetManager manager = mock(AssetManager.class);
        when(manager.isLoaded(anyString(), eq(Texture.class))).thenReturn(true);
        when(manager.get(anyString(), eq(Texture.class))).thenAnswer(inv -> new DummyTexture());
        Texture brick = new DummyTexture();
        when(manager.get(eq("brick.jpg"), eq(Texture.class))).thenReturn(brick);

        InputService input = mock(InputService.class);
        RenderStrategy renderStrategy = mock(RenderStrategy.class);
        OrthographicCamera camera = new OrthographicCamera();
        when(renderStrategy.getCamera()).thenReturn(camera);
        Viewport viewport = mock(Viewport.class);
        when(renderStrategy.getViewport()).thenReturn(viewport);
        when(viewport.getWorldWidth()).thenReturn(800f);
        when(viewport.getWorldHeight()).thenReturn(600f);

        ScoreRepository repo = mock(ScoreRepository.class);
        TDS game = new TDS(() -> mock(SpriteBatch.class), manager, input, repo, renderStrategy);

        GameScreen screen = new GameScreen(game, input, renderStrategy);

        try (MockedStatic<AnimationSetFactory> factory = mockStatic(AnimationSetFactory.class)) {
            TextureRegion region = new TextureRegion(new DummyTexture());
            Animation<TextureRegion> anim = new Animation<>(0.1f, region);
            AnimationSet set = new AnimationSet(anim, anim, anim, anim);
            factory.when(() -> AnimationSetFactory.load(any(AssetManager.class), anyString()))
                    .thenReturn(set);

            screen.show();
        }

        verify(manager, times(1)).get("brick.jpg", Texture.class);

        Field f = GameScreen.class.getDeclaredField("walls");
        f.setAccessible(true);
        Wall[] walls = (Wall[]) f.get(screen);
        for (Wall w : walls) {
            assertSame(brick, w.getTexture());
        }
    }
}
