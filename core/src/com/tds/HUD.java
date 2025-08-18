/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    
    /**
     * @author KeisterBun
     */
    public HUD() {
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
    
    public void gameOver(SpriteBatch batch, BitmapFont pen) {
        pen.draw(batch, "GAME OVER", Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2);
        
    }
    
    /**
     * @author KeisterBun
     * @param batch
     * @param pen 
     */
    public void drawHud(SpriteBatch batch, BitmapFont pen) {
        pen.draw(batch, score.concat(Integer.toString(totalScore)), 20, Gdx.graphics.getHeight()-20);
        pen.draw(batch, level.concat(Integer.toString(currentLevel)), 20, Gdx.graphics.getHeight()-40);
        pen.draw(batch, lives.concat(Integer.toString(currentLives)), 20, Gdx.graphics.getHeight()-60);
        pen.draw(batch, highScoreLabel.concat(Integer.toString(highScore)), 20, Gdx.graphics.getHeight()-80);
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
