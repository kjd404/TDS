/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import com.tds.platform.GraphicsContext;

/**
 *
 * @author Gabe
 */
public class ParticleSystem {
    float vx, vy;
    float rateTimer = 0;
    Texture texture;
    ArrayList<Entity> particles = new ArrayList<Entity>();
    private final GraphicsContext graphics;

    public ParticleSystem(Texture pTex, GraphicsContext graphics) {
        texture = pTex;
        this.graphics = graphics;
    }
    
    void shoot(float secondsOfLife, float fireRate, float angle, float x, float y, float speed) {
        if(rateTimer > fireRate) {
            Entity e = new Entity(secondsOfLife, speed, texture, 0, 0, 64, 64);;
            e.setTexture(texture);
            e.vx = (float)Math.cos(Math.toRadians(angle) + (float)(Math.PI / 2)) * speed;
            e.vy = (float)Math.sin(Math.toRadians(angle) + (float)(Math.PI / 2)) * speed;
            e.health = secondsOfLife;
            e.setRotation(angle);
            e.setPosition(x, y);
            particles.add(e);
            rateTimer = 0;
        }
    }
    
    void process(ArrayList<Virus> enemies) {
        float time = graphics.getDeltaTime();
        rateTimer += time;
        for(int i = particles.size() - 1; i >= 0; i--) {
            particles.get(i).translate(particles.get(i).vx, particles.get(i).vy);
            particles.get(i).health -= time;
            if(particles.get(i).health <= 0) {
                particles.remove(i);
            }
            try{
            for(Virus e: enemies) {
                if(e.getBoundingRectangle().overlaps(particles.get(i).getBoundingRectangle())){
                    e.setHealth(e.getHealth() - 1);
                    if(e.getHealth() <= 0){
                        e.alive = false;
                    }
                    particles.get(i).health = 0;
                }
            }
            } catch(Exception t){
                        
                    }
        }
    }
    
    void draw(SpriteBatch batch) {
        for(Entity e : particles) {
            e.draw(batch);
        }
    }

}
