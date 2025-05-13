package com.gameapps.game;

import com.badlogic.gdx.utils.Array;

public class BossBulletSpawner {
    private GameSounds gameSounds;
    private TextureMain textureMain;

    public BossBulletSpawner(GameSounds gameSounds, TextureMain textureMain) {
        this.gameSounds = gameSounds;
        this.textureMain = textureMain;
    }

    public void spawnBossBullet(Array<BossBullet> bossBullets, float bossX, float bossY, float playerX, float playerY, int score) {
        float bulletWidth = 60f;
        float bulletHeight = 60f;

        float startX = bossX + 150;
        float startY = bossY + 150;
        

        float targetX = playerX;
        float targetY = playerY;

        float speed = 900f; 
        if (score >= 150) {
            speed = 1400;
        }
        
        BossBullet bullet = new BossBullet(
            startX - bulletWidth/2, 
            startY - bulletHeight/2,
            bulletWidth,
            bulletHeight,
            targetX,
            targetY,
            speed, 
            textureMain.bossBulletTexture
        );

        gameSounds.fireBossSound.play(0.2f);
        
        bossBullets.add(bullet);
    }
}
