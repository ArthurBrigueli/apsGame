package com.gameapps.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Rectangle rectangle;
    
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> AtackAnimation;
    private Animation<TextureRegion> WalkAnimation;

    private float stateTime;
    private float velocityY;
    private boolean isJumping;
    private boolean isSlide;
    private float originalHeight;

    private int countJump = 0;


    private long runningSoundId = -1;
    private boolean isRunning;
    private Sound jumpSound, runSound;
    
    private final float GRAVITY = -2800;
    private final float JUMP_VELOCITY = 900;
    private final float GROUND_Y = 100;


    private boolean isTouchedByFire = false;
    private float touchFireTimer = 0f;
    private final float TOUCH_FIRE_DURATION = 0.1f; 



    private float baseHeight;  
    private float currentHeightModifier = 1f;



    //---
    private Rectangle hitbox; 
    private float hitboxYOffset;


    private boolean isJumpAnimationPlaying;

    private boolean isWalkRigthAnimationPlaying;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;



    private boolean isAtackAnimationPlaying;
    private float atackAnimationTimer;
    private final float ATACK_ANIMATION_DURATION = 0.5f;
    private boolean freeMode;



    private boolean isReturning = false;
    private float returnSpeed = 300f; 
    private float startReturnX; 
    private float returnProgress = 0f;
    private final float RETURN_DURATION = 0.5f; 

    private boolean lastDirectionLeft = false;


    public Player(float x, float y) {
        float tamPlayerW = Gdx.graphics.getWidth() / 7f;
        float tamPlayerH = Gdx.graphics.getHeight() / 7f;
        this.rectangle = new Rectangle(x, y, tamPlayerW, tamPlayerH);


        float hitboxWidth = tamPlayerW * 0.5f; 
        float hitboxHeight = tamPlayerH * 0.45f; 
        this.hitboxYOffset = tamPlayerH * 0.01f; 
        this.hitbox = new Rectangle(x, y + hitboxYOffset, hitboxWidth, hitboxHeight);


        this.velocityY = 0;
        this.isJumping = false;
        this.stateTime = 0f;
        this.isSlide = false;
        this.originalHeight = tamPlayerH;
        this.baseHeight = tamPlayerH;

        
        initializeAnimations();
    }


    public void startResetPlayer() {
        if (!isReturning) {
            isReturning = true;
            startReturnX = rectangle.x;
            returnProgress = 0f;
        }
    }



    public void atack() {
        isAtackAnimationPlaying = true;
        atackAnimationTimer = ATACK_ANIMATION_DURATION;
        stateTime = 0f; 
    }

    private void initializeAnimations() {
        
        initializeRunAnimation();
        initializeJumpAnimation();
        initializeAtackAnimation();
        initialWalkAnimation();
    }

    private void updateHitboxPosition() {
       
        hitbox.x = rectangle.x + (rectangle.width - hitbox.width) / 2; 
        hitbox.y = rectangle.y + hitboxYOffset;
    }


    private void initialWalkAnimation(){
        int frameColsWalk = 7;
        int frameRowsWalk = 1;

        Texture playerTextureSheetWalk = new Texture("player/Walk.png");

        TextureRegion[][] tmp = TextureRegion.split(
            playerTextureSheetWalk,
            playerTextureSheetWalk.getWidth() / frameColsWalk,
            playerTextureSheetWalk.getHeight() / frameRowsWalk
        );

        TextureRegion[] WalkFrames = new TextureRegion[frameColsWalk];
        for (int i = 0; i < frameColsWalk; i++) {
            WalkFrames[i] = tmp[0][i]; 
        }

        this.WalkAnimation = new Animation<>(0.1f, WalkFrames);
    }

    private void initializeAtackAnimation(){
        int frameColsAtack = 7;
        int frameRowsAtack = 1;

        Texture playerTextureSheetAtack = new Texture("player/Attack_2.png");
        TextureRegion[][] tmp = TextureRegion.split(
            playerTextureSheetAtack,
            playerTextureSheetAtack.getWidth() / frameColsAtack,
            playerTextureSheetAtack.getHeight() / frameRowsAtack
        );

        TextureRegion[] AtackFrames = new TextureRegion[frameColsAtack];
        for (int i = 0; i < frameColsAtack; i++) {
            AtackFrames[i] = tmp[0][i]; 
        }

        this.AtackAnimation = new Animation<>(0.1f, AtackFrames);
    }

    private void initializeJumpAnimation(){
        int frameColsJump = 8;
        int frameRowsJump = 1;

        Texture playerTextureSheetJump = new Texture("player/Jump.png");
        TextureRegion[][] tmp = TextureRegion.split(
            playerTextureSheetJump,
            playerTextureSheetJump.getWidth() / frameColsJump,
            playerTextureSheetJump.getHeight() / frameRowsJump
        );

        TextureRegion[] jumpFrames = new TextureRegion[frameColsJump];
        for (int i = 0; i < frameColsJump; i++) {
            jumpFrames[i] = tmp[0][i]; 
        }

        this.jumpAnimation = new Animation<>(0.1f, jumpFrames);
    }

    private void initializeRunAnimation() {
        
        int frameCols = 8;
        int frameRows = 1;


        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
        runSound = Gdx.audio.newSound(Gdx.files.internal("sounds/run.mp3"));

        Texture playerTextureSheet = new Texture("player/Run.png");


        TextureRegion[][] tmp = TextureRegion.split(
            playerTextureSheet,
            playerTextureSheet.getWidth() / frameCols,
            playerTextureSheet.getHeight() / frameRows
        );

        TextureRegion[] walkFrames = new TextureRegion[frameCols];
        for (int i = 0; i < frameCols; i++) {
            walkFrames[i] = tmp[0][i]; 
        }

        this.runAnimation = new Animation<>(0.1f, walkFrames);
    }


    public void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
        lastDirectionLeft = false; 
        rectangle.x += 5;
    }


    public void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
        lastDirectionLeft = true; 
        rectangle.x -= 5;
    }

    public void stopMovingRight() {
        isMovingRight = false;
    }
    
    public void stopMovingLeft() {
        isMovingLeft = false;
    }

    public void update(float deltaTime, boolean gameOver, boolean freeMode) {

        updatePhysics(deltaTime);
        this.freeMode = freeMode;
        
        stateTime += deltaTime;

        if (rectangle.y == GROUND_Y) {
            isJumpAnimationPlaying = false;
            isJumping = false;
        }


        if (isReturning) {
            returnProgress += deltaTime / RETURN_DURATION;
            
            
            rectangle.x = startReturnX + (210 - startReturnX) * Math.min(1, returnProgress);
            
            
            if (returnProgress >= 1f) {
                rectangle.x = 210; 
                isReturning = false;
                freeMode = false;
            }
        }






        if (isAtackAnimationPlaying) {
            atackAnimationTimer -= deltaTime;
            if (atackAnimationTimer <= 0) {
                isAtackAnimationPlaying = false;
            }
        }



        if (isTouchedByFire) {
            touchFireTimer -= deltaTime;
            if (touchFireTimer <= 0) {
                rectangle.height = originalHeight;
                isTouchedByFire = false;
                if (!isSlide) { 
                    currentHeightModifier = 1f;
                    updateHeight();
                }
            }
        }




        if (rectangle.y == GROUND_Y && !isSlide) {
            if (!isRunning && !gameOver) {
                runningSoundId = runSound.loop(0.5f);
                isRunning = true;
            }
        } else {
            if (isRunning) {
                runSound.stop(runningSoundId);
                isRunning = false;
            }
        }


    }

    public void touchFire() {
        if (!isTouchedByFire) {
            currentHeightModifier = 1.3f; 
            updateHeight();
            isTouchedByFire = true;
            touchFireTimer = TOUCH_FIRE_DURATION;
        }
    }

    private void updatePhysics(float deltaTime) {

        velocityY += GRAVITY * deltaTime;
        rectangle.y += velocityY * deltaTime;
        
        if (rectangle.y <= GROUND_Y) {
            rectangle.y = GROUND_Y;
            isJumping = false;
            velocityY = 0;
        }

        updateHitboxPosition();
    }

    public void slide(){
        if(!isSlide) {
            isSlide = true;
            currentHeightModifier = 0.5f; 
            updateHeight();
        }
    }

    public void closeSlide() {
        if (isSlide) {
            isSlide = false;
            currentHeightModifier = isTouchedByFire ? 1.5f : 1f;
            updateHeight();
        }
    }

    private void updateHeight() {
        rectangle.height = baseHeight * currentHeightModifier;
        hitbox.height = rectangle.height * 0.45f; 
        hitboxYOffset = rectangle.height * 0.01f; 
        updateHitboxPosition();
    }

    public void jump(boolean gameOver) {
        if(rectangle.y == GROUND_Y){
            countJump = 0;
        }



        if (countJump < 2 && !gameOver) {
            isJumpAnimationPlaying = true;
            stateTime = 0f;
            jumpSound.play(0.1f);
            velocityY = JUMP_VELOCITY;
            countJump++;
        }
    }

    

    public TextureRegion getCurrentFrame() {
        TextureRegion frame;
        
       
        if (isJumpAnimationPlaying && rectangle.y > GROUND_Y) {
            frame = new TextureRegion(jumpAnimation.getKeyFrame(stateTime, false));
            if (lastDirectionLeft) {
                frame.flip(true, false);
            }
            return frame;
        }
        
        if (freeMode) {
            if (isMovingLeft || isMovingRight) {
                frame = new TextureRegion(WalkAnimation.getKeyFrame(stateTime, true));
                if (isMovingLeft) {
                    frame.flip(true, false);
                    lastDirectionLeft = true;
                } else {
                    lastDirectionLeft = false;
                }
                return frame;
            } else {
                
                frame = new TextureRegion(WalkAnimation.getKeyFrames()[0]);
                if (lastDirectionLeft) {
                    frame.flip(true, false);
                }
                return frame;
            }
        }
    
       
        if (isAtackAnimationPlaying) {
            frame = new TextureRegion(AtackAnimation.getKeyFrame(stateTime, false));
            if (lastDirectionLeft) {
                frame.flip(true, false);
            }
            return frame;
        } else if (isMovingLeft || isMovingRight) {
            frame = new TextureRegion(WalkAnimation.getKeyFrame(stateTime, true));
            if (isMovingLeft) {
                frame.flip(true, false);
                lastDirectionLeft = true;
            } else {
                lastDirectionLeft = false;
            }
            return frame;
        } else {
            frame = new TextureRegion(runAnimation.getKeyFrame(stateTime, true));
            lastDirectionLeft = false;
            return frame;
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getX() {
        return rectangle.x;
    }

    public float getY() {
        return rectangle.y;
    }

    public float getWidth() {
        return rectangle.width;
    }

    public float getHeight() {
        return rectangle.height;
    }
    

    public boolean isJumping() {
        return isJumping;
    }

    public void dispose() {
        runAnimation = null;
        jumpSound.dispose();
        runSound.dispose();
        jumpAnimation = null;
        WalkAnimation = null;
    }
}


