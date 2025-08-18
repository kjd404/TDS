package com.tds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.input.InputService;
import com.tds.score.ScoreRepository;
import com.tds.screen.DefaultScreenFactory;
import com.tds.screen.MenuScreen;
import com.tds.screen.RenderStrategy;
import com.tds.screen.ScreenFactory;

import java.util.function.Supplier;

/**
 * Main LibGDX game class wired via dependency injection.
 */
public class TDS extends Game {
    public SpriteBatch batch;
    public final AssetManager assetManager;
    private int highScore;
    private final ScoreRepository scoreRepository;
    private final InputService inputService;
    private final RenderStrategy renderStrategy;
    private final Supplier<SpriteBatch> batchSupplier;

    public TDS(
            Supplier<SpriteBatch> batchSupplier,
            AssetManager assetManager,
            InputService inputService,
            ScoreRepository scoreRepository,
            RenderStrategy renderStrategy
    ) {
        this.batchSupplier = batchSupplier;
        this.assetManager = assetManager;
        this.inputService = inputService;
        this.scoreRepository = scoreRepository;
        this.renderStrategy = renderStrategy;
    }

    @Override
    public void create() {
        batch = batchSupplier.get();
        assetManager.load("background.png", Texture.class);
        assetManager.load("virus.png", Texture.class);
        assetManager.load("Bullet.png", Texture.class);
        assetManager.finishLoading();

        // Retrieve any persisted high score on startup
        highScore = scoreRepository.getHighScore();
        ScreenFactory factory = new DefaultScreenFactory(this, inputService, renderStrategy);
        setScreen(new MenuScreen(this, inputService, factory));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (batch != null) {
            batch.dispose();
        }
        assetManager.dispose();
    }

    public int getHighScore() {
        return highScore;
    }

    public InputService getInputService() {
        return inputService;
    }

    public ScoreRepository getScoreRepository() {
        return scoreRepository;
    }

    public RenderStrategy getRenderStrategy() {
        return renderStrategy;
    }

    /**
     * Submit a score and refresh the cached high score.
     *
     * @param score score to compare with the stored high score
     */
    public void submitScore(int score) {
        scoreRepository.submitScore(score);
        highScore = scoreRepository.getHighScore();
    }

    /**
     * Reset the stored high score. Useful for testing.
     */
    public void resetHighScore() {
        scoreRepository.reset();
        highScore = 0;
    }
}

