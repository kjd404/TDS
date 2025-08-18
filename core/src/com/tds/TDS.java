package com.tds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.score.GdxPreferencesScoreRepository;
import com.tds.score.ScoreRepository;
import com.tds.screen.MenuScreen;
import com.tds.input.InputService;
import com.tds.input.InputHandler;

public class TDS extends Game {
    public SpriteBatch batch;
    public AssetManager assetManager;
    private int highScore;
    private final ScoreRepository scoreRepository;
    private final InputService inputService;

    public TDS() {
        this(new InputHandler(), new GdxPreferencesScoreRepository());
    }

    public TDS(ScoreRepository scoreRepository) {
        this(new InputHandler(), scoreRepository);
    }

    public TDS(InputService inputService) {
        this(inputService, new GdxPreferencesScoreRepository());
    }

    public TDS(InputService inputService, ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
        this.inputService = inputService;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("background.png", Texture.class);
        assetManager.load("virus.png", Texture.class);
        assetManager.load("Bullet.png", Texture.class);
        assetManager.finishLoading();

        // Retrieve any persisted high score on startup
        highScore = scoreRepository.getHighScore();
        setScreen(new MenuScreen(this, inputService));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        assetManager.dispose();
    }

    public int getHighScore() {
        return highScore;
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
