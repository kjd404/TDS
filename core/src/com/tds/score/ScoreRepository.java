package com.tds.score;

/**
 * Abstraction for persisting and retrieving high score data.
 */
public interface ScoreRepository {

    /**
     * Retrieve the stored high score.
     *
     * @return current high score or {@code 0} if none stored
     */
    int getHighScore();

    /**
     * Submit a score. Implementations should persist the score only if it
     * exceeds the currently stored value.
     *
     * @param score score to compare against the stored high score
     */
    void submitScore(int score);

    /**
     * Reset the stored high score.
     */
    void reset();
}
