package com.tds;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.input.InputService;
import com.tds.score.ScoreRepository;
import com.tds.screen.RenderStrategy;
import org.junit.Test;

/**
 * Integration-style test verifying that {@link GameBootstrap} wires dependencies into {@link TDS}.
 */
public class GameBootstrapTest {

    @Test
    public void bootstrapWiresDependencies() {
        // Ensure LibGDX globals are initialised
        new HeadlessApplication(new ApplicationAdapter() {}, new HeadlessApplicationConfiguration());

        SpriteBatch batch = mock(SpriteBatch.class);
        AssetManager assetManager = mock(AssetManager.class);
        InputService input = mock(InputService.class);
        ScoreRepository repo = mock(ScoreRepository.class);
        RenderStrategy strategy = mock(RenderStrategy.class);
        GL20 gl = mock(GL20.class);
        Gdx.gl20 = gl;
        Gdx.gl = gl;

        TDS game = new GameBootstrap()
                .withSpriteBatch(batch)
                .withAssetManager(assetManager)
                .withInputService(input)
                .withScoreRepository(repo)
                .withRenderStrategy(strategy)
                .bootstrap();

        game.create();

        assertSame(batch, game.batch);
        assertSame(assetManager, game.assetManager);
        assertSame(input, game.getInputService());
        assertSame(repo, game.getScoreRepository());
        assertSame(strategy, game.getRenderStrategy());

        // cleanup
        game.dispose();
    }
}
