package com.gameapps.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VictoryPlateGui {


    SpriteBatch batch;
    private Texture victoryPlateTexture;
    

    public VictoryPlateGui(SpriteBatch batch) {
        this.batch = batch;
        this.victoryPlateTexture = new Texture("victoryPlate.png");
    }




    public void showVictoryPlate(){
        float plateWidth = 300;
        float plateHeight = 150;
        float x = (Gdx.graphics.getWidth() - plateWidth) / 2;
        float y = (Gdx.graphics.getHeight() - plateHeight) - 100;
        
        batch.draw(victoryPlateTexture, x, y, plateWidth, plateHeight);
    }





    public void dispose(){
        if (victoryPlateTexture != null) {
            victoryPlateTexture.dispose();
        }
    }



   


}
