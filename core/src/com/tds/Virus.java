/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

/**
 *
 * @author mattb
 */
public class Virus extends Entity{

    boolean alive;

    public Virus(Texture texture, int level) {
        super(1, 200, texture, 0, 0, 128, 128);
        Random rand = new Random();
        speed = 200 + level*20 + (rand.nextInt()%60 - 30);        
        setScale(0.75f);
        setHealth((int)Math.log10(level) + 1);

        alive = true;
        //setX(100);
        //setY(100);        
    }
    
    public Virus(Texture texture, float speed, int startx, int starty ) {
        super(1, speed, texture, 0, 0, 32, 32);
        setX(startx);
        setY(starty);
        alive = true;
    }
    
    /**
     * @author KeisterBun
     * @param adminX
     * @param adminY 
     */
    public void move(float adminX, float adminY){
        float dirX =  adminX - getX()-getWidth()/2;
        float dirY =  adminY - getY()-getHeight()/2;
        double angle = Math.atan2(-dirX, dirY);
        float x, y;
        
        x = (float)Math.cos(angle + Math.PI / 2) * getSpeed() * Gdx.graphics.getDeltaTime();
        y = (float)Math.sin(angle + Math.PI / 2) * getSpeed() * Gdx.graphics.getDeltaTime();
        this.translate(x, y);
    }
    
    public void setStatus(boolean status) {
        alive = status;
    }
    
    public boolean getStatus() {
        return alive;
    }
    
}
