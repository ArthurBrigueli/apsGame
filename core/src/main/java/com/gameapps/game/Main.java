package com.gameapps.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {
    @Override
    public void create() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        TextureMain textureMain = new TextureMain();
        GameSounds gameSounds = new GameSounds();
        
        GameScreen gameScreen = new GameScreen(this, textureMain, gameSounds);
        FirstScreen firstScreen = new FirstScreen(this, gameScreen);
        
        setScreen(firstScreen);
    }
}