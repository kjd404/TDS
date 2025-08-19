package com.tds.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tds.Admin;
import com.tds.HUD;
import com.tds.TDS;
import com.tds.Virus;
import com.tds.Wall;
import com.tds.assets.AnimationSet;
import com.tds.assets.AnimationSetFactory;
import com.tds.input.InputService;
import com.tds.platform.GdxGraphicsContext;
import com.tds.platform.GraphicsContext;
import com.tds.weapons.ParticleSystem;
import com.tds.weapons.ParticleSystemFactory;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {
    private final TDS game;
    private HUD hud;
    private Admin admin;
    private Texture background;
    private BitmapFont pen;
    private Texture virusTexture;
    private ArrayList<Virus> virusList;
    private Wall[] walls;
    private int level;
    private Virus v1;
    private boolean gameOver;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final RenderStrategy renderStrategy;
    private final InputService input;
    private final GraphicsContext graphics;

    public GameScreen(TDS game, InputService input, RenderStrategy renderStrategy) {
        this.game = game;
        this.input = input;
        this.renderStrategy = renderStrategy;

        this.graphics = new GdxGraphicsContext();
        camera = (OrthographicCamera) renderStrategy.getCamera();
        viewport = renderStrategy.getViewport();
    }

    @Override
    public void show() {
        hud = new HUD(graphics);
        hud.setHighScore(game.getHighScore());
        background = game.assetManager.get("background.png", Texture.class);
        virusTexture = game.assetManager.get("virus.png", Texture.class);

        AnimationSet animations = AnimationSetFactory.load(game.assetManager, "playerModel.json");
        Texture bulletTexture = game.assetManager.get("Bullet.png", Texture.class);
        ParticleSystemFactory psFactory = new ParticleSystemFactory(graphics);
        ParticleSystem bullets = psFactory.create(bulletTexture);
        admin = new Admin(1, 3, 1, 300, animations, input, bullets, graphics);
        float posx = viewport.getWorldWidth() / 2 - admin.getWidth() / 2;
        float posy = viewport.getWorldHeight() / 2 - admin.getHeight() / 2;
        admin.setPosition(posx, posy);

        virusList = new ArrayList<Virus>();
        level = 1;
        generateLevel(level);

        pen = new BitmapFont();
        pen.setColor(Color.YELLOW);

        walls = new Wall[4];

        // Direct all input events to the centralized input handler
        Gdx.input.setInputProcessor(input);
        int gap = 200;
        int wallWidth = 50;
        float worldHeight = viewport.getWorldHeight();
        float worldWidth = viewport.getWorldWidth();

        Texture wallTexture = game.assetManager.get("brick.jpg", Texture.class);

        Wall temp = new Wall(wallTexture);
        temp.setSize(worldWidth - gap, wallWidth);
        temp.setPosition(gap / 2f, 0);
        walls[0] = temp;
        temp = new Wall(wallTexture);
        temp.setSize(worldWidth - gap, wallWidth);
        temp.setPosition(gap / 2f, worldHeight - wallWidth);
        walls[1] = temp;
        temp = new Wall(wallTexture);
        temp.setSize(wallWidth, worldHeight - gap);
        temp.setPosition(0, gap / 2f);
        walls[2] = temp;
        temp = new Wall(wallTexture);
        temp.setSize(wallWidth, worldHeight - gap);
        temp.setPosition(worldWidth - wallWidth, gap / 2f);
        walls[3] = temp;
        gameOver = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (gameOver) {
            renderStrategy.apply(game.batch);
            game.batch.begin();
            game.batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            hud.gameOver(game.batch, pen);
            game.batch.end();
            return;
        }

        admin.processMovement(virusList, viewport);
        hud.setCurrentLives(admin.getLives());

        for (Wall wall : walls) {
            admin.wallCollison(wall);
        }

        renderStrategy.apply(game.batch);
        game.batch.begin();
        game.batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        for (Virus v : virusList) {
            v.move(admin.getX() + admin.getWidth() / 2, admin.getY() + admin.getHeight() / 2);
            if (admin.getBoundingRectangle().overlaps(v.getBoundingRectangle())) {
                v.setStatus(false);
                admin.setPosition(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
                admin.setLives(admin.getLives() - 1);
                if (admin.getLives() <= 0) {
                    game.submitScore(hud.getTotalScore());
                    hud.setHighScore(game.getHighScore());
                    gameOver = true;
                }
            }
        }

        admin.draw(game.batch);
        admin.drawBullets(game.batch);

        for (Virus v : virusList) {
            v.draw(game.batch);
        }

        hud.drawHud(game.batch, pen);

        game.batch.end();
        for (int i = virusList.size() - 1; i >= 0; i--) {
            if (virusList.get(i).getStatus() != true) {
                hud.setTotalScore(hud.getTotalScore() + 1);
                virusList.remove(virusList.get(i));
            }
        }

        if (virusList.size() == 0) {
            game.submitScore(hud.getTotalScore());
            hud.setHighScore(game.getHighScore());
            level += 1;
            hud.incrementCurrentLevel();
            generateLevel(level);
        }
    }

    @Override
    public void resize(int width, int height) {
        renderStrategy.resize(width, height);
    }

    void generateLevel(int levelNumber) {
        int numberVirus = levelNumber * 2 + levelNumber;
        for (int i = 0; i < numberVirus; i++) {
            v1 = new Virus(virusTexture, levelNumber, graphics);
            virusList.add(v1);
        }
        Random rand = new Random();
        float worldHeight = viewport.getWorldHeight();
        float worldWidth = viewport.getWorldWidth();
        for (Virus v : virusList) {
            int pos = rand.nextInt() % 4;
            switch (pos) {
                case 0:
                    v.setPosition(40, 40);
                    break;
                case 1:
                    v.setPosition(40, worldHeight - 40);
                    break;
                case 2:
                    v.setPosition(worldWidth - 40, 40);
                    break;
                case 3:
                    v.setPosition(worldWidth - 40, worldHeight - 40);
                    break;
                default:
                    v.setPosition(worldWidth - 40, worldHeight - 40);
                    break;
            }
        }
    }
}
