/*
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tds.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.Entity;
import com.tds.Virus;
import com.tds.platform.GraphicsContext;
import java.util.ArrayList;

/**
 *
 * @author Gabe
 */
public class ProjectileSystem implements ParticleSystem {
    private static class Particle extends Entity {
        float vx, vy;

        Particle(float health, float speed, Texture texture) {
            super(health, speed, texture, 0, 0, 64, 64);
        }
    }

    float rateTimer = 0;
    Texture texture;
    ArrayList<Particle> particles = new ArrayList<Particle>();
    private final GraphicsContext graphics;

    public ProjectileSystem(Texture pTex, GraphicsContext graphics) {
        texture = pTex;
        this.graphics = graphics;
    }

    @Override
    public void shoot(float secondsOfLife, float fireRate, float angle, float x, float y, float speed) {
        if (rateTimer > fireRate) {
            Particle e = new Particle(secondsOfLife, speed, texture);
            e.setTexture(texture);
            e.vx = (float) Math.cos(Math.toRadians(angle) + (float) (Math.PI / 2)) * speed;
            e.vy = (float) Math.sin(Math.toRadians(angle) + (float) (Math.PI / 2)) * speed;
            e.setHealth(secondsOfLife);
            e.setRotation(angle);
            e.setPosition(x, y);
            particles.add(e);
            rateTimer = 0;
        }
    }

    @Override
    public void process(ArrayList<Virus> enemies) {
        float time = graphics.getDeltaTime();
        rateTimer += time;
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.translate(p.vx, p.vy);
            p.setHealth(p.getHealth() - time);
            if (p.getHealth() <= 0) {
                particles.remove(i);
                continue;
            }
            for (Virus e : enemies) {
                if (e.getBoundingRectangle().overlaps(p.getBoundingRectangle())) {
                    e.setHealth(e.getHealth() - 1);
                    if (e.getHealth() <= 0) {
                        e.setStatus(false);
                    }
                    p.setHealth(0);
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (Particle e : particles) {
            e.draw(batch);
        }
    }
}
