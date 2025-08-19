package com.tds;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.input.InputService;
import com.tds.score.ScoreRepository;
import com.tds.screen.RenderStrategy;
import org.junit.Before;
import org.junit.Test;

public class TDSAssetLoadingTest {
    @Before
    public void setup() {
        if (Gdx.app == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationAdapter() {}, config);
            GL20 gl = mock(GL20.class);
            Gdx.gl20 = gl;
            Gdx.gl = gl;
        }
    }

    @Test
    public void loadsBrickTextureOnCreate() {
        AssetManager manager = mock(AssetManager.class);
        ScoreRepository repo = mock(ScoreRepository.class);
        when(repo.getHighScore()).thenReturn(0);
        TDS game = new TDS(
                () -> mock(SpriteBatch.class), manager, mock(InputService.class), repo, mock(RenderStrategy.class));
        game.create();
        verify(manager).load("brick.jpg", Texture.class);
    }
}
