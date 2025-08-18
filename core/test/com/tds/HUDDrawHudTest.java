package com.tds;

import static org.junit.Assert.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tds.platform.FakeGraphicsContext;
import org.junit.Before;
import org.junit.Test;

public class HUDDrawHudTest {
    private static class RecordingFont extends BitmapFont {
        final java.util.List<Float> ys = new java.util.ArrayList<>();

        RecordingFont() {
            super(new BitmapFontData(), new TextureRegion(new DummyTexture()), false);
        }

        @Override
        public com.badlogic.gdx.graphics.g2d.GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
            ys.add(y);
            return null;
        }
    }

    private static class DummyTexture extends com.badlogic.gdx.graphics.Texture {
        public DummyTexture() {
            super();
        }

        @Override
        public int getWidth() {
            return 1;
        }

        @Override
        public int getHeight() {
            return 1;
        }

        @Override
        public int getDepth() {
            return 0;
        }

        @Override
        public com.badlogic.gdx.graphics.TextureData getTextureData() {
            return null;
        }

        @Override
        public boolean isManaged() {
            return false;
        }

        @Override
        protected void reload() {}
    }

    @Before
    public void setup() {
        if (Gdx.app == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationAdapter() {}, config);
        }
    }

    @Test
    public void positionsHudRelativeToScreenHeight() {
        FakeGraphicsContext graphics = new FakeGraphicsContext();
        graphics.setSize(800, 600);
        HUD hud = new HUD(graphics);
        RecordingFont font = new RecordingFont();
        hud.drawHud(null, font);
        assertEquals(4, font.ys.size());
        assertEquals(580f, font.ys.get(0), 0.001f);
        assertEquals(560f, font.ys.get(1), 0.001f);
        assertEquals(540f, font.ys.get(2), 0.001f);
        assertEquals(520f, font.ys.get(3), 0.001f);
        font.dispose();
    }
}
