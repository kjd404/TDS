package com.tds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.screen.MenuScreen;

public class TDS extends Game {
    public SpriteBatch batch;
    public AssetManager assetManager;
    private int highScore;

    @Override
    public void create () {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("background.png", Texture.class);
        assetManager.load("virus.png", Texture.class);
        assetManager.finishLoading();

        // Retrieve any persisted high score on startup
        highScore = ScoreManager.getHighScore();
        setScreen(new MenuScreen(this));
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
        ScoreManager.submitScore(score);
        highScore = ScoreManager.getHighScore();
    }

    /**
     * Reset the stored high score. Useful for testing.
     */
    public void resetHighScore() {
        ScoreManager.reset();
        highScore = 0;
    }
}
