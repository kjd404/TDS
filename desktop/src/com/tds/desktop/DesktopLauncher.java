package com.tds.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.tds.TDS;


public class DesktopLauncher {
        public static void main (String[] arg) {
                Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
                DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
                config.setFullscreenMode(displayMode);
                new Lwjgl3Application(new TDS(), config);
        }
}
