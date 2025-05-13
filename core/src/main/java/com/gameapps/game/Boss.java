package com.gameapps.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Boss {
    private Animation<TextureRegion> animationIdle;
    private Animation<TextureRegion> animationAttack;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private boolean isAttacking = false;
    private boolean attackCompleted = false;
    private final float ATTACK_DURATION = 1.5f; 
    private boolean attackRequested = false;

    public Boss() {
        loadAnimations();
        stateTime = 0f;
        currentAnimation = animationIdle;
    }

    private void loadAnimations() {

        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i <= 36; i++) {
            String framePath = String.format("bossDevil/idle/devil_idle_%04d.png", i);
            idleFrames.add(new TextureRegion(new Texture(Gdx.files.internal(framePath))));
        }
        animationIdle = new Animation<>(0.07f, idleFrames, Animation.PlayMode.LOOP);


        Array<TextureRegion> attackFrames = new Array<>();

        for (int i = 16; i >= 1; i--) {
            String framePath = String.format("bossDevil/Intro/devil_ph1_intro_%04d.png", i);
            attackFrames.add(new TextureRegion(new Texture(Gdx.files.internal(framePath))));
        }

        for (int i = 1; i <= 16; i++) {
            String framePath = String.format("bossDevil/Intro/devil_ph1_intro_%04d.png", i);
            attackFrames.add(new TextureRegion(new Texture(Gdx.files.internal(framePath))));
        }
        animationAttack = new Animation<>(0.07f, attackFrames, Animation.PlayMode.NORMAL);
    }

    public void startAttack() {
        if (!isAttacking) {
            isAttacking = true;
            attackCompleted = false;
            currentAnimation = animationAttack;
            stateTime = 0f; 
        }
    }

    public void update(float delta) {
        stateTime += delta;

        if (attackRequested && !isAttacking) {
            isAttacking = true;
            attackCompleted = false;
            attackRequested = false;
            currentAnimation = animationAttack;
            stateTime = 0f;
        }
        
        if (isAttacking && currentAnimation.isAnimationFinished(stateTime)) {
            isAttacking = false;
            attackCompleted = true;
            currentAnimation = animationIdle;
            stateTime = 0f;
        }
    }

    public void resetAttackState() {
        attackCompleted = false;
        attackRequested = false;
    }
    

    public TextureRegion getCurrentFrame() {
        return currentAnimation.getKeyFrame(stateTime, false);
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isAttackCompleted() {
        return attackCompleted;
    }

    public void requestAttack() {
        if (!isAttacking && !attackRequested) {
            attackRequested = true;
        }
    }

    public void dispose() {
        for (TextureRegion region : animationIdle.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : animationAttack.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }
}