package com.tds.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Factory for creating {@link AnimationSet} instances from a JSON configuration.
 *
 * <p>The configuration file should contain entries for each direction (up, down,
 * left, right) listing the texture files for that animation and an optional
 * {@code frameDuration} field.</p>
 */
public final class AnimationSetFactory {
    private AnimationSetFactory() {}

    public static AnimationSet load(AssetManager manager, String configPath) {
        JsonValue root = new JsonReader().parse(Gdx.files.internal(configPath));
        float frameDuration = root.getFloat("frameDuration", 0.1f);

        Animation<TextureRegion> down = buildAnimation(manager, root.get("down"), frameDuration, false);
        Animation<TextureRegion> up = buildAnimation(manager, root.get("up"), frameDuration, false);
        Animation<TextureRegion> right = buildAnimation(manager, root.get("right"), frameDuration, false);
        Animation<TextureRegion> left = buildAnimation(manager, root.get("left"), frameDuration, true);

        return new AnimationSet(up, down, left, right);
    }

    private static Animation<TextureRegion> buildAnimation(
            AssetManager manager, JsonValue array, float frameDuration, boolean flipX) {
        Array<TextureRegion> regions = new Array<>();
        if (array != null) {
            for (JsonValue v : array) {
                String path = v.asString();
                if (!manager.isLoaded(path, Texture.class)) {
                    manager.load(path, Texture.class);
                }
            }
            manager.finishLoading();
            for (JsonValue v : array) {
                Texture tex = manager.get(v.asString(), Texture.class);
                TextureRegion region = new TextureRegion(tex);
                if (flipX) {
                    region.flip(true, false);
                }
                regions.add(region);
            }
        }
        return new Animation<>(frameDuration, regions, Animation.PlayMode.LOOP);
    }
}
