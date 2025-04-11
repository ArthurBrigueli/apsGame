package com.gameapps.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    public Rectangle rect;
    public Texture texture;
    public float speed;
    public boolean active;

    public Bullet(float x, float y, float width, float height, float speed, Texture texture) {
        this.rect = new Rectangle(x, y, width, height);
        this.texture = texture;
        this.speed = speed;
        this.active = true;
    }

    public void update(float deltaTime) {
        rect.x += speed * deltaTime; 
    }
}