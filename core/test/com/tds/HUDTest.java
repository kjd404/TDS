package com.tds;

import org.junit.Test;
import static org.junit.Assert.*;
import com.tds.platform.FakeGraphicsContext;

public class HUDTest {
    @Test
    public void testIncrementCurrentLevel() {
        HUD hud = new HUD(new FakeGraphicsContext());
        int level = hud.getCurrentLevel();
        hud.incrementCurrentLevel();
        assertEquals(level + 1, hud.getCurrentLevel());
    }

    @Test
    public void testSetTotalScore() {
        HUD hud = new HUD(new FakeGraphicsContext());
        hud.setTotalScore(42);
        assertEquals(42, hud.getTotalScore());
    }
}
