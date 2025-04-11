package com.gameapps.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSounds {


    public Sound fireBossSound, fireHitBoss, backgroundSound ,deathBoss, colletcCoin, fireSound;
    

    public GameSounds(){
        loadSounds();
    }



    public void loadSounds(){
        colletcCoin = Gdx.audio.newSound(Gdx.files.internal("sounds/colletCoin.mp3"));
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/tiro.mp3"));
        fireBossSound = Gdx.audio.newSound(Gdx.files.internal("sounds/fireBoss.mp3"));
        fireHitBoss = Gdx.audio.newSound(Gdx.files.internal("sounds/fireHitBoss.mp3"));
        backgroundSound = Gdx.audio.newSound(Gdx.files.internal("sounds/backgroundSound.mp3"));
        deathBoss = Gdx.audio.newSound(Gdx.files.internal("sounds/deathBoss.mp3"));
    }



    public void dispose(){
        fireBossSound.dispose(); 
        fireHitBoss.dispose();
        backgroundSound.dispose();
        deathBoss.dispose();
        colletcCoin.dispose();
        fireSound.dispose();
    }



}
