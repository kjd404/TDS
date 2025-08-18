package com.tds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Handles persistent storage of high scores.
 */
public final class ScoreManager {
    private static final String PREFS_NAME = "com.tds.highscores";
    private static final String KEY_HIGH_SCORE = "highScore";

    private ScoreManager() {
        // Utility class
    }

    /**
     * Retrieve the stored high score.
     *
     * @return current high score or 0 if none stored
     */
    public static int getHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        return prefs.getInteger(KEY_HIGH_SCORE, 0);
    }

    /**
     * Submit a score. If the score is higher than the stored value it will be saved.
     *
     * @param score score to compare against the stored high score
     */
    public static void submitScore(int score) {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        int current = prefs.getInteger(KEY_HIGH_SCORE, 0);
        if (score > current) {
            prefs.putInteger(KEY_HIGH_SCORE, score);
            prefs.flush();
        }
    }

    /**
     * Reset the high score. Intended for development and testing.
     */
    public static void reset() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger(KEY_HIGH_SCORE, 0);
        prefs.flush();
    }
}
