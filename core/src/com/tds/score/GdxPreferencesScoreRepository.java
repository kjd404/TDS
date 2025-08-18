package com.tds.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * {@link ScoreRepository} implementation backed by LibGDX {@link Preferences}.
 */
public class GdxPreferencesScoreRepository implements ScoreRepository {

    private static final String PREFS_NAME = "com.tds.highscores";
    private static final String KEY_HIGH_SCORE = "highScore";

    private Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    @Override
    public int getHighScore() {
        return getPrefs().getInteger(KEY_HIGH_SCORE, 0);
    }

    @Override
    public void submitScore(int score) {
        Preferences prefs = getPrefs();
        int current = prefs.getInteger(KEY_HIGH_SCORE, 0);
        if (score > current) {
            prefs.putInteger(KEY_HIGH_SCORE, score);
            prefs.flush();
        }
    }

    @Override
    public void reset() {
        Preferences prefs = getPrefs();
        prefs.putInteger(KEY_HIGH_SCORE, 0);
        prefs.flush();
    }
}

