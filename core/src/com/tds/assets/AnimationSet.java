package com.tds.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Container for character animations for each movement direction.
 */
public class AnimationSet {
    private final Animation<TextureRegion> up;
    private final Animation<TextureRegion> down;
    private final Animation<TextureRegion> left;
    private final Animation<TextureRegion> right;

    public AnimationSet(Animation<TextureRegion> up,
                        Animation<TextureRegion> down,
                        Animation<TextureRegion> left,
                        Animation<TextureRegion> right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Animation<TextureRegion> getUp() {
        return up;
    }

    public Animation<TextureRegion> getDown() {
        return down;
    }

    public Animation<TextureRegion> getLeft() {
        return left;
    }

    public Animation<TextureRegion> getRight() {
        return right;
    }
}
