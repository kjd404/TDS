package com.tds.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import com.tds.Virus;

/**
 * Represents a system responsible for handling and rendering projectiles.
 */
public interface ParticleSystem {
    void shoot(float secondsOfLife, float fireRate, float angle, float x, float y, float speed);
    void process(ArrayList<Virus> enemies);
    void draw(SpriteBatch batch);
}
