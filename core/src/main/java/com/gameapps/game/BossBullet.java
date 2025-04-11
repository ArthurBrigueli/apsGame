package com.gameapps.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class BossBullet {
    public Rectangle rect;
    public Texture texture;
    public float speedX, speedY;  // Velocidade nos eixos X e Y
    public boolean active;

    public BossBullet(float startX, float startY, float width, float height, 
                    float targetX, float targetY, float speed, Texture texture) {
        this.rect = new Rectangle(startX, startY, width, height);
        this.texture = texture;
        
        // Calcula direção para o alvo (player)
        float dx = targetX - startX;
        float dy = targetY - startY;
        float distance = (float)Math.sqrt(dx*dx + dy*dy);
        
        // Normaliza e define a velocidade
        this.speedX = (dx/distance) * speed;
        this.speedY = (dy/distance) * speed;
        this.active = true;
    }



    

    public void update(float deltaTime) {
        rect.x += speedX * deltaTime;
        rect.y += speedY * deltaTime;
    }
}