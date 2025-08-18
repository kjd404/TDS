/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tds;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tds.platform.GraphicsContext;

/**
 *
 * @author mattb
 */
public class HUD {
    private int totalScore;
    private int currentLevel;
    private int currentLives;
    private int highScore;
    private final String level = "Level: ";
    private final String score = "Score: ";
    private final String lives = "Lives: ";
    private final String highScoreLabel = "High Score: ";
    private final GraphicsContext graphics;

    /**
     * @author KeisterBun
     */
    public HUD(GraphicsContext graphics) {
        this.graphics = graphics;
        totalScore = 0;
        currentLevel = 1;
    }

    /**
     * @author KeisterBun
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @author KeisterBun
     * @return int
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * @author KeisterBun
     */
    public void incrementCurrentLevel() {
        currentLevel++;
    }

    /**
     * @author KeisterBun
     * @param newTotal
     */
    public void setTotalScore(int newTotal) {
        totalScore = newTotal;
    }

    public void gameOver(Batch batch, BitmapFont pen) {
        pen.draw(batch, "GAME OVER", graphics.getHeight() / 2f, graphics.getWidth() / 2f);
    }

    /**
     * @author KeisterBun
     * @param batch
     * @param pen
     */
    public void drawHud(Batch batch, BitmapFont pen) {
        pen.draw(batch, score.concat(Integer.toString(totalScore)), 20, graphics.getHeight() - 20);
        pen.draw(batch, level.concat(Integer.toString(currentLevel)), 20, graphics.getHeight() - 40);
        pen.draw(batch, lives.concat(Integer.toString(currentLives)), 20, graphics.getHeight() - 60);
        pen.draw(batch, highScoreLabel.concat(Integer.toString(highScore)), 20, graphics.getHeight() - 80);
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
