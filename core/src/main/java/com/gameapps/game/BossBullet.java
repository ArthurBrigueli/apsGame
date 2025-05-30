package com.gameapps.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class BossBullet {
    public Rectangle rect;
    public Texture texture;
    public float speedX, speedY;  
    public boolean active;

    public BossBullet(float startX, float startY, float width, float height, 
                    float targetX, float targetY, float speed, Texture texture) {
        this.rect = new Rectangle(startX, startY, width, height);
        this.texture = texture;
        
 
        float dx = targetX - startX;
        float dy = targetY - startY;
        float distance = (float)Math.sqrt(dx*dx + dy*dy);
        

        this.speedX = (dx/distance) * speed;
        this.speedY = (dy/distance) * speed;
        this.active = true;
    }



    

    public void update(float deltaTime) {
        rect.x += speedX * deltaTime;
        rect.y += speedY * deltaTime;
    }
}