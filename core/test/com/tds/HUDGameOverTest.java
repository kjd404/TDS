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

public class HUDGameOverTest {
    private static class RecordingFont extends BitmapFont {
        final java.util.List<Float> xs = new java.util.ArrayList<>();
        final java.util.List<Float> ys = new java.util.ArrayList<>();

        RecordingFont() {
            super(new BitmapFontData(), new TextureRegion(new DummyTexture()), false);
        }

        @Override
        public com.badlogic.gdx.graphics.g2d.GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
            xs.add(x);
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
    public void drawsGameOverCentered() {
        FakeGraphicsContext graphics = new FakeGraphicsContext();
        graphics.setSize(800, 600);
        HUD hud = new HUD(graphics);
        RecordingFont font = new RecordingFont();
        hud.gameOver(null, font);
        assertEquals(3, font.xs.size());
        assertEquals(400f, font.xs.get(0), 0.001f);
        assertEquals(300f, font.ys.get(0), 0.001f);
        assertEquals(400f, font.xs.get(1), 0.001f);
        assertEquals(280f, font.ys.get(1), 0.001f);
        assertEquals(400f, font.xs.get(2), 0.001f);
        assertEquals(260f, font.ys.get(2), 0.001f);
        font.dispose();
    }
}
