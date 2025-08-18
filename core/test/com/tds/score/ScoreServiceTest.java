package com.tds.score;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.TDS;
import com.tds.GameBootstrap;
import com.tds.input.InputService;
import com.tds.screen.RenderStrategy;
import static org.mockito.Mockito.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for score submission and reset behaviour in {@link TDS} using an
 * in-memory {@link ScoreRepository} implementation.
 */
public class ScoreServiceTest {

    private static class InMemoryScoreRepository implements ScoreRepository {
        private int highScore;

        @Override
        public int getHighScore() {
            return highScore;
        }

        @Override
        public void submitScore(int score) {
            if (score > highScore) {
                highScore = score;
            }
        }

        @Override
        public void reset() {
            highScore = 0;
        }
    }

    @Test
    public void submitScoreUpdatesHighScore() {
        TDS game = new GameBootstrap()
                .withScoreRepository(new InMemoryScoreRepository())
                .withAssetManager(mock(AssetManager.class))
                .withSpriteBatch(mock(SpriteBatch.class))
                .withInputService(mock(InputService.class))
                .withRenderStrategy(mock(RenderStrategy.class))
                .bootstrap();
        game.submitScore(10);
        assertEquals(10, game.getHighScore());

        // submitting a lower score should not replace the high score
        game.submitScore(5);
        assertEquals(10, game.getHighScore());
    }

    @Test
    public void resetHighScoreClearsStoredValue() {
        TDS game = new GameBootstrap()
                .withScoreRepository(new InMemoryScoreRepository())
                .withAssetManager(mock(AssetManager.class))
                .withSpriteBatch(mock(SpriteBatch.class))
                .withInputService(mock(InputService.class))
                .withRenderStrategy(mock(RenderStrategy.class))
                .bootstrap();
        game.submitScore(42);
        game.resetHighScore();
        assertEquals(0, game.getHighScore());
    }
}

