package com.gameapps.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TextDeathBoss {


    private SpriteBatch batch;
    private Texture plateDeathBoss1;
    private Texture plateDeathBoss2;
    private Texture plateDeathBoss3;
    private Rectangle hitbox;

    public TextDeathBoss(SpriteBatch batch){
        this.batch = batch;
        this.plateDeathBoss1 = new Texture("cartafinal1.jpg");
        this.plateDeathBoss2 = new Texture("cartafinal2.jpg");
        this.plateDeathBoss3 = new Texture("cartafinal3.jpg");
        this.hitbox = new Rectangle();
    }
    


    public void create(){
        
    }



    public void render(int lvl, Texture plateDeathBossTexture){

        


        switch (lvl) {
            case 1:
                plateDeathBossTexture = plateDeathBoss1;
                break;
            case 2:
                plateDeathBossTexture = plateDeathBoss2;
                break;
            case 3:
                plateDeathBossTexture = plateDeathBoss3;
                break;
        
            default:
                break;

        }


        float plateWidth = 300; 
        float plateHeight = 450;
        float x = (Gdx.graphics.getWidth() - plateWidth) / 2;
        float y = (Gdx.graphics.getHeight() - plateHeight) / 2.5f;

        if(lvl == 3){
            y = (Gdx.graphics.getHeight() - plateHeight) - 270;
        }

        hitbox.set(x, y, plateWidth, plateHeight);
        
        batch.draw(plateDeathBossTexture, x, y, plateWidth, plateHeight);
        
    }


    public Rectangle getHitbox() {
        return hitbox;
    }



}
