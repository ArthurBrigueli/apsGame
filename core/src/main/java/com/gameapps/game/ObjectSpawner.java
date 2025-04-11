package com.gameapps.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class ObjectSpawner {
    private Texture obstacleTexture;
    private Texture coinTexture;
    private Texture enemyTexture;
    private Texture threeTexture;
    private Texture bossTexture;
    private float minSpawnTime;
    private float maxSpawnTime;
    private Sound spawBossSound;

    
    public ObjectSpawner(Texture obstacleTexture, Texture coinTexture, Texture enemyTexture, Texture threeTexture, float minSpawnTime, float maxSpawnTime, Texture bossTexture) {
        this.obstacleTexture = obstacleTexture;
        this.coinTexture = coinTexture;
        this.enemyTexture = enemyTexture;
        this.threeTexture = threeTexture;
        this.minSpawnTime = minSpawnTime;
        this.maxSpawnTime = maxSpawnTime;
        this.bossTexture = bossTexture;

        spawBossSound = Gdx.audio.newSound(Gdx.files.internal("sounds/spawBoss.mp3"));
    }

    public GameObject spawnBoss(int health, int maxHealth, Texture bossTexture){
        spawBossSound.play(0.5f);
        float spawnX = Gdx.graphics.getWidth() / 1.4f;
        float spawnY = 100;
        GameObject obj;
        float tamW = Gdx.graphics.getWidth() / 5f;
        float tamH = Gdx.graphics.getHeight() / 3f;


        obj = new GameObject(new Rectangle(spawnX, spawnY, tamW, tamH), bossTexture, false, false, false, true, health, maxHealth);


        return obj;

    }

    public GameObject spawnObject(boolean activeBoss) {
        if(activeBoss){
            return null;
        }
        
        boolean isCoin = MathUtils.randomBoolean();
        boolean isEnemy = MathUtils.randomBoolean();
        boolean isThree = MathUtils.randomBoolean();
        Texture texture;
        GameObject obj;

        float spawnX = Gdx.graphics.getWidth();
        float spawnY = 100;

        float tamW = Gdx.graphics.getWidth() / 25f;
        float tamH = Gdx.graphics.getHeight() / 20f;


        float tamWA = Gdx.graphics.getWidth() / 8f;
        float tamHA = Gdx.graphics.getHeight() / 12f;

        if (isCoin) {
            texture = coinTexture;
            obj = new GameObject(new Rectangle(spawnX, spawnY, tamW, tamH), texture, true, false, false, false);
        } else if (isEnemy) {
            texture = enemyTexture;
            obj = new GameObject(new Rectangle(spawnX, spawnY, tamW, tamH), texture, false, true, false, false);
        }else if(isThree){
            texture = threeTexture;
            obj = new GameObject(new Rectangle(spawnX, 150, tamWA, tamHA), texture, false, false, true, false);
        } else {
            texture = obstacleTexture;
            obj = new GameObject(new Rectangle(spawnX, spawnY, tamW, tamH), texture, false, false, false, false);
        }

        return obj;
    }

    public long getNextSpawnDelay() {
        return (long) (MathUtils.random(minSpawnTime, maxSpawnTime) * 1_000_000_000);
    }
}