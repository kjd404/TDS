/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tds.assets.AnimationSet;
import com.tds.input.InputService;
import com.tds.input.InputService.Action;
import com.tds.platform.GraphicsContext;
import com.tds.weapons.ParticleSystem;
import java.util.ArrayList;

/**
 *
 * @author mattb
 */
public class Admin extends Entity {
    int lives;
    ParticleSystem bullets;
    float stateTime = 0;
    float oldX, oldY;

    private final GraphicsContext graphics;

    private final AnimationSet animations;
    private final InputService input;

    public Admin(
            float strength,
            int lives,
            float health,
            float speed,
            AnimationSet animations,
            InputService input,
            ParticleSystem bullets,
            GraphicsContext graphics) {
        super(
                health,
                speed,
                animations.getDown().getKeyFrame(0).getTexture(),
                animations.getDown().getKeyFrame(0).getRegionX(),
                animations.getDown().getKeyFrame(0).getRegionY(),
                animations.getDown().getKeyFrame(0).getRegionWidth(),
                animations.getDown().getKeyFrame(0).getRegionHeight());

        setRegion(animations.getDown().getKeyFrame(0));
        setScale(1f);

        this.lives = lives;
        this.animations = animations;
        this.bullets = bullets;
        this.input = input;
        this.graphics = graphics;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void processMovement(ArrayList<Virus> enemies, Viewport viewport) {
        oldX = getX();
        oldY = getY();
        this.setOriginCenter();
        Vector3 mouseWorld = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        float mouseX = mouseWorld.x;
        float mouseY = mouseWorld.y;

        boolean keyPressed = false;
        if (input.isActionPressed(Action.MOVE_LEFT)) {
            keyPressed = true;
            this.setX(this.getX() - graphics.getDeltaTime() * getSpeed());
        }
        if (input.isActionPressed(Action.MOVE_RIGHT)) {
            keyPressed = true;
            this.setX(this.getX() + graphics.getDeltaTime() * getSpeed());
        }
        if (input.isActionPressed(Action.MOVE_UP)) {
            keyPressed = true;
            this.setY(this.getY() + graphics.getDeltaTime() * getSpeed());
        }
        if (input.isActionPressed(Action.MOVE_DOWN)) {
            keyPressed = true;
            this.setY(this.getY() - graphics.getDeltaTime() * getSpeed());
        }

        float dirX = mouseX - getX() - getWidth() / 2;
        float dirY = mouseY - getY() - getHeight() / 2;
        double angle = Math.atan2(-dirX, dirY);
        if (input.isActionPressed(Action.FIRE))
            bullets.shoot(4, 0.1f, (float) Math.toDegrees(angle), getX() + 100, getY() + 100, 20);

        this.boundingCircle.setPosition(this.getX(), this.getY());

        if (keyPressed) {
            stateTime += graphics.getDeltaTime();
        } else {
            stateTime = 0;
        }

        float range = (float) (Math.PI / 4);
        Animation<TextureRegion> current = animations.getDown();
        if (Math.abs(angle - 0) < range) {
            current = animations.getUp();
        }
        if (Math.abs(angle - Math.PI / 2) < range) {
            current = animations.getLeft();
        }
        if (Math.abs(angle - Math.PI) < range) {
            current = animations.getDown();
        }
        if (Math.abs(angle + Math.PI / 2) < range) {
            current = animations.getRight();
        }

        TextureRegion region = current.getKeyFrame(stateTime, true);
        setRegion(region);

        bullets.process(enemies);
    }

    void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void drawBullets(SpriteBatch batch) {
        bullets.draw(batch);
    }

    public void wallCollison(Wall wall) {
        Rectangle r1 = wall.getBoundingRectangle();
        Rectangle r2 = this.getBoundingRectangle();

        if (r1.overlaps(r2)) {
            setX(oldX);
            setY(oldY);
        }
    }
}
