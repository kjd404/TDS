package com.tds.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tds.Admin;
import com.tds.HUD;
import com.tds.TDS;
import com.tds.Virus;
import com.tds.Wall;
import com.tds.input.InputHandler;

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

    public GameScreen(TDS game) {
        this.game = game;
    }

    @Override
    public void show() {
        hud = new HUD();
        hud.setHighScore(game.getHighScore());
        background = game.assetManager.get("background.png", Texture.class);
        virusTexture = game.assetManager.get("virus.png", Texture.class);

        admin = new Admin(1, 3, 1, 300, game.assetManager.get("badlogic.jpg", Texture.class));
        float posx = Gdx.graphics.getWidth()/2 - admin.getWidth()/2;
        float posy = Gdx.graphics.getHeight()/2 - admin.getHeight()/2;
        admin.setPosition(posx, posy);
        admin.scale(.2f);

        virusList = new ArrayList<Virus>();
        level = 1;
        generateLevel(level);

        pen = new BitmapFont();
        pen.setColor(Color.YELLOW);

        walls = new Wall[4];

        // Direct all input events to the centralized input handler
        Gdx.input.setInputProcessor(InputHandler.getInstance());
        int gap = 200;
        int wallWidth = 50;
        int worldHeight = Gdx.graphics.getHeight();
        int worldWidth = Gdx.graphics.getWidth();
        Wall temp = new Wall();
        temp.setSize(worldWidth - gap, wallWidth);
        temp.setPosition(gap / 2, 0);
        walls[0] = temp;
        temp = new Wall();
        temp.setSize(worldWidth - gap, wallWidth);
        temp.setPosition(gap / 2, worldHeight - wallWidth);
        walls[1] = temp;
        temp = new Wall();
        temp.setSize(wallWidth, worldHeight - gap);
        temp.setPosition(0, gap / 2);
        walls[2] = temp;
        temp = new Wall();
        temp.setSize(wallWidth, worldHeight - gap);
        temp.setPosition(worldWidth - wallWidth, gap / 2);
        walls[3] = temp;
    }

    @Override
    public void render(float delta) {
        admin.processMovement(virusList);
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        hud.setCurrentLives(admin.getLives());

        for(Wall wall : walls){
            admin.wallCollison(wall);
        }

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        for(Virus v : virusList){
            v.move(admin.getX() + admin.getWidth()/2,
                    admin.getY() + admin.getHeight()/2);
            if( admin.getBoundingRectangle().overlaps(v.getBoundingRectangle()) ) {
                v.setStatus(false);
                admin.setPosition(Gdx.graphics.getWidth()/2,
                        Gdx.graphics.getHeight()/2);
                admin.setLives(admin.getLives()-1);
                if( admin.getLives() <= 0 ){
                    Gdx.app.exit();
                }
            }
        }

        admin.draw(game.batch);
        admin.drawBullets(game.batch);

        for(Virus v : virusList) {
            v.draw(game.batch);
        }

        hud.drawHud(game.batch, pen);

        game.batch.end();
        for(int i = virusList.size() - 1; i >= 0; i-- ) {
            if(virusList.get(i).getStatus() != true) {
                hud.setTotalScore(hud.getTotalScore() + 1);
                virusList.remove(virusList.get(i));
            }
        }

        if(virusList.size() == 0){
            game.submitScore(hud.getTotalScore());
            hud.setHighScore(game.getHighScore());
            level += 1;
            hud.incrementCurrentLevel();
            generateLevel(level);
        }
    }

    void generateLevel(int levelNumber){
        int numberVirus = levelNumber*2 + levelNumber;
        for(int i = 0; i < numberVirus; i++) {
            v1 = new Virus(virusTexture, levelNumber);
            virusList.add(v1);
        }
        Random rand = new Random();
        for(Virus v : virusList){
            int pos = rand.nextInt() % 4;
            switch(pos){
                case 0:
                    v.setPosition(40, 40);
                    break;
                case 1:
                    v.setPosition(40, Gdx.graphics.getHeight() - 40);
                    break;
                case 2:
                    v.setPosition(Gdx.graphics.getWidth() - 40, 40);
                    break;
                case 3:
                    v.setPosition(Gdx.graphics.getWidth() - 40,
                        Gdx.graphics.getHeight() -40);
                    break;
                default:
                    v.setPosition(Gdx.graphics.getWidth() - 40,
                        Gdx.graphics.getHeight() -40);
                    break;
            }
        }
    }
}
