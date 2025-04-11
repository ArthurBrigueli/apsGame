package com.gameapps.game;


import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gameapps.game.GUI.TextDeathBoss;
import com.gameapps.game.GUI.VictoryPlateGui;

public class GameScreen implements Screen{


    private final Game game;
    private SpriteBatch batch;
    private Texture obstacleTexture, coinTexture, enemyTexture, backgroundTexture, threeTexture;
    private BitmapFont font;
    private Array<GameObject> objects;
    private long lastObjectTime;
    private int poluicao;
    private int ambientacao;
    
    private float currentObjectSpeed;
    private final float objectSpeed = 600;
    private float atualSpeed = objectSpeed;
    


    private float minSpawnTime = 0.8f;
    private float maxSpawnTime = 1f;
    private long nextSpawnDelay;
    private int score = 0;
    private float chao1, chao2, fundo1, fundo2, longe1, longe2;
    private boolean gameOver = false;
    private Stage stage;
    private TextButton restartButton;
    private int dinheiro = 0;
    private long lastMoneyUpdate = 0;
    
    private boolean activeBoss = false;


    private ShapeRenderer shapeRenderer;


    boolean wasCPressedLastFrame = false;
    private ObjectSpawner objectSpawner;
    




    //bullet
    private Array<Bullet> bullets;
    private float bulletSpeed = 600f; 

    private Texture whitePixelTexture;





    private Texture currentBossTextTexture;
    private float bossTextTimer = 0;
    private final float BOSS_TEXT_DURATION = 3f; 
    private final float BOSS_TEXT_SCALE = 0.5f;



    //movimento do boss

    private float BOSS_MOVE_SPEED = 150f; // Velocidade de movimento do boss



    private final float BOSS_SHOOT_INTERVAL = 2f; // Intervalo entre disparos
    private float bossShootTimer = 0f;
    private Array<BossBullet> bossBullets;



    private int currentBossLevel = 0;
    //plate victory
    private boolean showVictoryPlate = false;
    private float victoryPlateTimer = 0;
    private final float VICTORY_PLATE_DURATION = 3f;
    VictoryPlateGui victoryPlateGui;


    private final TextureMain textureMain;
    private final GameSounds gameSounds;

    private Texture bossTexture;

    private boolean showHitboxes = false;
    TextDeathBoss textDeathBoss;

    private int currentBossLevelDeath;
    private boolean killBoss;
    private Texture plateDeathBossTexture;



    private Texture chaoTexture;
    private final float speedIncreaseAmount = 100f;
    private final float maxSpeed = 800f;
    private final int speedIncreaseInterval = 5;


    private final float speedIncreaseAmountFundo = 30f;
    private final float maxSpeedFundo = 400f;
    private final int speedIncreaseIntervalFundo = 3;
    private float currentObjectSpeedFundo;
    private final float objectSpeedFundo = 50;
    private float atualSpeedFundo = objectSpeedFundo;



    private Texture fundoLongeTexture;
    private final float speedIncreaseAmountFundoLonge = 10f;
    private final float maxSpeedFundoLonge = 200f;
    private final int speedIncreaseIntervalFundoLonge = 3;
    private float currentObjectSpeedFundoLonge;
    private final float objectSpeedFundoLonge = 20;
    private float atualSpeedFundoLonge = objectSpeedFundoLonge;


    Player player;
    BossBulletSpawner bossBulletSpawner;
    private boolean freeMode = false;


    private boolean showTutorial = false;

    public GameScreen(Game game, TextureMain textureMain, GameSounds gameSounds) {
        this.game = game;
        this.textureMain = textureMain;
        this.gameSounds = gameSounds;
    }


    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show() called");
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        currentObjectSpeed = objectSpeed;
        currentObjectSpeedFundo = atualSpeedFundo;
        currentObjectSpeedFundoLonge = atualSpeedFundoLonge;

        float tamPlayerW = Gdx.graphics.getWidth() / 7f;
        float tamPlayerH = Gdx.graphics.getHeight() / 7f;

        player = new Player(210, 110);

        chaoTexture = textureMain.chaoTexture;
        fundoLongeTexture = textureMain.fundoLongeTexture;
        
        backgroundTexture = textureMain.backgroundTexture1;
        bossTexture = textureMain.enemyTrashBossTexture;


        bossBullets = new Array<>();
        bossBulletSpawner = new BossBulletSpawner(gameSounds, textureMain);



        //bullet
        bullets = new Array<>();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixelTexture = new Texture(pixmap);
        pixmap.dispose();
        shapeRenderer = new ShapeRenderer();


        //plate victory
        victoryPlateGui = new VictoryPlateGui(batch);

        //text death boss
        textDeathBoss = new TextDeathBoss(batch);

        textDeathBoss.create();




        objectSpawner = new ObjectSpawner(textureMain.obstacleTexture, textureMain.coinTexture, textureMain.enemyTexture, textureMain.threeTexture, minSpawnTime, maxSpawnTime, bossTexture);


        objects = new Array<>();
        spawnObject();
        


        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("reloadButton.png")));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("reloadButton.png")));
        restartButton = new TextButton("", textButtonStyle);

        float buttonWidth = 300;
        float buttonHeight = 150;
        restartButton.setSize(buttonWidth, buttonHeight);
        restartButton.setPosition(
            Gdx.graphics.getWidth() / 2 - restartButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - restartButton.getHeight() / 2
        );
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartGame();
            }
        });

        stage.addActor(restartButton);

        fundo1 = 0;
        fundo2 = Gdx.graphics.getWidth();

        chao1 = 0;
        chao2 = Gdx.graphics.getWidth();

        longe1 = 0;
        longe2 = Gdx.graphics.getWidth();

        gameSounds.backgroundSound.loop(0.2f);
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();


        batch.draw(fundoLongeTexture, longe1, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(fundoLongeTexture, longe2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch.draw(backgroundTexture, fundo1, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(backgroundTexture, fundo2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch.draw(chaoTexture, chao1, 0, Gdx.graphics.getWidth(), 110);
        batch.draw(chaoTexture, chao2, 0, Gdx.graphics.getWidth(), 110);



        

        

        for (Bullet bullet : bullets) {
            if (bullet.active) {
                batch.draw(bullet.texture, bullet.rect.x, bullet.rect.y, bullet.rect.width, bullet.rect.height);
            }
        }


        for (BossBullet bullet : bossBullets) {
            if (bullet.active) {
                // Calcula a rotação baseada na direção do movimento
                float rotation = (float)Math.toDegrees(Math.atan2(bullet.speedY, bullet.speedX));
                
                batch.draw(
                    bullet.texture,
                    bullet.rect.x,
                    bullet.rect.y,
                    bullet.rect.width/2,  // originX (ponto de rotação no centro)
                    bullet.rect.height/2, // originY
                    bullet.rect.width,
                    bullet.rect.height,
                    1f,  // scaleX
                    1f,  // scaleY
                    rotation,
                    0,   // srcX
                    0,   // srcY
                    bullet.texture.getWidth(),  // srcWidth
                    bullet.texture.getHeight(), // srcHeight
                    false, // flipX
                    false  // flipY
                );
            }
        }

        if(killBoss){
            objects.clear();
            textDeathBoss.render(currentBossLevel, plateDeathBossTexture);

            if(currentBossLevel != 5){
                freeMode = true;


                if(player.getHitbox().overlaps(textDeathBoss.getHitbox())) {
                    float textureWidth = 30;
                    float textureHeight = 30;
                    float x = player.getX() + (player.getWidth() / 2f) - (textureWidth / 2f);
                    float y = player.getY() + player.getHeight() - 60;
                    
                    batch.draw(textureMain.pressKeyInterativeTexture, x, y, textureWidth, textureHeight);
                
                    if(Gdx.input.isKeyJustPressed(Input.Keys.E)){

                        killBoss = false;
                        freeMode = false;
                        player.startResetPlayer();
                        
                        
                    }
                }
            }

            

        }


        //condição para desenhar a placa do nome do boss
        if (bossTextTimer > 0 && currentBossTextTexture != null) {

            float scaledWidth = currentBossTextTexture.getWidth() * BOSS_TEXT_SCALE;
            float scaledHeight = currentBossTextTexture.getHeight() * BOSS_TEXT_SCALE;
            
            float x = (Gdx.graphics.getWidth() - scaledWidth) / 2;
            float y = Gdx.graphics.getHeight() - scaledHeight - 20; 
            batch.draw(currentBossTextTexture, 
                      x, y, 
                      scaledWidth, scaledHeight);
        }


        if (showVictoryPlate) {
            // Centraliza a placa na tela
            victoryPlateGui.showVictoryPlate();
        }




        //desenhar a barra de vida do boss
        for (GameObject obj : objects) {
            if(obj.isEnemytrashBoss) {

                batch.setColor(Color.DARK_GRAY);
                batch.draw(whitePixelTexture, obj.rect.x, obj.rect.y + obj.rect.height + 5, 
                         obj.rect.width, 10);
                
                float healthPercent = (float)obj.health / obj.maxHealth;
                batch.setColor(Color.RED);
                batch.draw(whitePixelTexture, obj.rect.x, obj.rect.y + obj.rect.height + 5, 
                         obj.rect.width * healthPercent, 10);
                
                batch.setColor(Color.WHITE);
            }
        }

        if (chao1 + Gdx.graphics.getWidth() <= 0) {
            chao1 = chao2 + Gdx.graphics.getWidth();
        }

        if (chao2 + Gdx.graphics.getWidth() <= 0) {
            chao2 = chao1 + Gdx.graphics.getWidth();
        }


        if (fundo1 + Gdx.graphics.getWidth() <= 0) {
            fundo1 = fundo2 + Gdx.graphics.getWidth();
        }

        if (fundo2 + Gdx.graphics.getWidth() <= 0) {
            fundo2 = fundo1 + Gdx.graphics.getWidth();
        }


        if (longe1 + Gdx.graphics.getWidth() <= 0) {
            longe1 = longe2 + Gdx.graphics.getWidth();
        }

        if (longe2 + Gdx.graphics.getWidth() <= 0) {
            longe2 = longe1 + Gdx.graphics.getWidth();
        }


        
        TextureRegion currentFrame = player.getCurrentFrame();
        batch.draw(currentFrame, player.getX(), player.getY(), player.getWidth(), player.getHeight());

        for (GameObject obj : objects) {
            batch.draw(obj.texture, obj.rect.x, obj.rect.y, obj.rect.width, obj.rect.height);
        }

        font.draw(batch, "Pontos: " + score, 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Poluição: " + poluicao, 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Ambientação: " + ambientacao, 20, Gdx.graphics.getHeight() - 80);
        font.draw(batch, "Dinheiro: $" + dinheiro, 20, Gdx.graphics.getHeight() - 110);

        if (gameOver) {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        if (showTutorial) {
            float buttonWidth = 30f;
            float buttonHeight = 30f;
            float yOffset = -20f; // Ajuste a altura aqui
            
            // Botão ESQUERDO (A)
            float leftX = player.getX() + 25f; // 20px da borda esquerda do player
            float rightX = player.getX() + player.getWidth() - 65f; // 70px da borda direita (50 do botão + 20 de margem)
            float yPos = player.getY() + player.getHeight() + yOffset;
            
            batch.draw(textureMain.pressKeyLeftTexture, leftX, yPos, buttonWidth, buttonHeight);
            batch.draw(textureMain.pressKeyRightTexture, rightX, yPos, buttonWidth, buttonHeight);
        }

        

        batch.end();



        
        if (showHitboxes) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    
            // Hitbox de renderização (sprite completa)
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(player.getRectangle().x, player.getRectangle().y, 
                            player.getRectangle().width, player.getRectangle().height);
            
            // Hitbox real de colisão
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(player.getHitbox().x, player.getHitbox().y, 
                            player.getHitbox().width, player.getHitbox().height);
            
            shapeRenderer.end();
        }
    }


    private void spawnBoss(int min, int max, int bossLevel) {

        currentBossLevel = bossLevel;
        killBoss = false;
        

        switch(bossLevel) {
            case 1:
                System.out.print("boss 1");
                bossTexture = textureMain.enemyTrashBossTexture;
                currentBossTextTexture = textureMain.bossTextTexture1;
                break;
            case 2:
                System.out.print("boss 2");
                bossTexture = textureMain.enemyTrashBossTexture;
                currentBossTextTexture = textureMain.bossTextTexture2;
                break;
            case 3:
                System.out.print("boss 3");
                bossTexture = textureMain.enemyTrashBossTextureFinal;
                currentBossTextTexture = textureMain.bossTextTexture3;
                break;
        }

        GameObject obj = objectSpawner.spawnBoss(min, max, bossTexture);
        objects.add(obj);
        activeBoss = true;
        
        
        
        bossTextTimer = BOSS_TEXT_DURATION;
    }


    private void restartGame() {
        player = new Player(210, 110);
        objects.clear();
        gameOver = false;
        score = 0;
        ambientacao = 0;
        poluicao = 0;
        dinheiro = 0;
        bossShootTimer = 0f;
        currentObjectSpeed = objectSpeed;
        atualSpeed = objectSpeed;
        activeBoss = false;
        showVictoryPlate = false;
        spawnObject();
        killBoss = false;
        showTutorial = false;
    }

    private void spawnObject() {
        GameObject obj = objectSpawner.spawnObject(activeBoss);
        if (obj != null) { 
            objects.add(obj);
            lastObjectTime = TimeUtils.nanoTime();
            nextSpawnDelay = objectSpawner.getNextSpawnDelay();
        }
    }


    private void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump(gameOver);
        }

        


        if (gameOver) {
            gameSounds.backgroundSound.pause();
        } else {
            gameSounds.backgroundSound.resume();
        }

        if (bossTextTimer > 0) {
            bossTextTimer -= deltaTime;
            if (bossTextTimer <= 0) {
                currentBossTextTexture = null;
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            showHitboxes = !showHitboxes; // Alterna entre mostrar e esconder hitboxes
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(freeMode){
                player.moveRight();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D) == false) {
            player.stopMovingRight();
        }

        if (showTutorial) {
            // Desativa APENAS no momento em que A ou D forem pressionados
            if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                showTutorial = false;
            }
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(freeMode){
                player.moveLeft();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A) == false) {
            player.stopMovingLeft();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {

            player.atack();
            
            float bulletWidth = 100f;
            float bulletHeight = 100f;
            Bullet bullet = new Bullet(
                player.getX() + player.getWidth(), 
                player.getY() + player.getHeight() / 2 - bulletHeight / 2, 
                bulletWidth,
                bulletHeight,
                bulletSpeed,
                textureMain.bulletTexture
            );
            gameSounds.fireSound.play(0.3f);
            bullets.add(bullet);
        }


        // Atualização das bullets do boss
        Iterator<BossBullet> bossBulletIter = bossBullets.iterator();
        while (bossBulletIter.hasNext()) {
            BossBullet bullet = bossBulletIter.next();
            bullet.update(deltaTime);
            
            // Remover bullets que saíram da tela
            if (bullet.rect.x + bullet.rect.width < 0 || 
                bullet.rect.x > Gdx.graphics.getWidth() ||
                bullet.rect.y + bullet.rect.height < 0 || 
                bullet.rect.y > Gdx.graphics.getHeight()) {
                bossBulletIter.remove();
            }
            
            // Verificar colisão com o jogador
            if (bullet.active && bullet.rect.overlaps(player.getHitbox())) {
                bullet.active = false; 
                player.touchFire();
                dinheiro = Math.max(0, dinheiro - 10);
                ambientacao = Math.max(0, ambientacao - 1);
                poluicao = Math.max(0, poluicao + 5);
                bossBulletIter.remove(); 
            }
        }

        // Disparo do boss
        if (activeBoss && !freeMode) {
            bossShootTimer -= deltaTime;
            if (bossShootTimer <= 0) {
                for (GameObject obj : objects) {
                    if (obj.isEnemytrashBoss && obj.health > 0) {
                        bossBulletSpawner.spawnBossBullet(
                            bossBullets, 
                            obj.rect.x, 
                            obj.rect.y,
                            player.getX() + player.getWidth()/2,
                            player.getY() + player.getHeight()/2,
                            score
                        );
                        bossShootTimer = BOSS_SHOOT_INTERVAL;
                        break;
                    }
                }
            }
        }
        
        // Atualiza e remove bolinhas inativas
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update(deltaTime);
        
            if (bullet.rect.x > Gdx.graphics.getWidth() || !bullet.active) {
                bulletIter.remove();
            }
        }





        for (Bullet bullet : bullets) {
            for (GameObject obj : objects) {
                if (obj.isEnemytrashBoss && bullet.rect.overlaps(obj.rect)) {
                    gameSounds.fireHitBoss.play(0.1f);
                    bullet.active = false;
                    obj.health -= 10;
                    if(obj.health <= 0) {

                        if(currentBossLevel == 1){
                            showTutorial = true;
                        }

                        gameSounds.deathBoss.play(0.3f);
                        objects.removeValue(obj, true);
                        activeBoss = false;

                        dinheiro += 50;
                        score += 10;
                        ambientacao += 5;
                        killBoss = true;

                        if(currentBossLevel == 3){
                            new Thread(() -> {
                                try {
                                    Thread.sleep(4000); 
                                    killBoss = false;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                    
                        

                        currentBossLevelDeath = currentBossLevel;

                        if (currentBossLevel == 3 && obj.health <= 0) {
                            showVictoryPlate = true;
                            victoryPlateTimer = VICTORY_PLATE_DURATION;
                            
                            objects.clear();
                            activeBoss = false;

                            if(currentBossLevel == 3){
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(4000); 
                                        gameOver = true;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            }

                            
                        }

                    }
                    break;
                }
            }
        }

        if(score == 20 && !activeBoss){
            System.out.println("Boss ativado");
            objects.clear();
            spawnBoss(100, 100, 1);
            activeBoss = true;
        }

        if(score == 80 && !activeBoss){
            System.out.println("Boss ativado");
            objects.clear();
            spawnBoss(200, 200, 2);
            activeBoss = true;
        }

        if(score == 150 && !activeBoss){
            System.out.println("Boss ativado");
            objects.clear();
            spawnBoss(300, 300, 3);
            activeBoss = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            player.slide();
            currentObjectSpeed = atualSpeed + 100; 
        } else {
            player.closeSlide();
            currentObjectSpeed = atualSpeed;
        }

        if (TimeUtils.nanoTime() - lastObjectTime > nextSpawnDelay) {
            spawnObject();
        }

        if (TimeUtils.nanoTime() - lastMoneyUpdate > 1_000_000_000 && !gameOver ) {
            dinheiro += ambientacao;
            dinheiro = Math.max(0, dinheiro - poluicao);
            lastMoneyUpdate = TimeUtils.nanoTime();
        }

        if (gameOver) {
            return;
        }

        if (score < 0) {
            gameOver = true;
            return;
        }

        if (score >= 160) {
            backgroundTexture = textureMain.backgroundTexture4;
        } else if (score >= 90) {
            backgroundTexture = textureMain.backgroundTexture3;
        } else if (score >= 30) {
            backgroundTexture = textureMain.backgroundTexture2;
        } else {
            backgroundTexture = textureMain.backgroundTexture1;
        }

        player.update(deltaTime, gameOver, killBoss);
        for (GameObject obj:objects){
            if(obj.isEnemytrashBoss && obj.health > 0){
                obj.rect.y += BOSS_MOVE_SPEED * deltaTime;
                if (obj.rect.y > 300 || obj.rect.y < 50) {
                    BOSS_MOVE_SPEED *= -1;
                }
            }
        }



        if (!freeMode) { // Só move se NÃO estiver no freeMode
            // Movimento do chão
            chao1 -= currentObjectSpeed * deltaTime;
            chao2 -= currentObjectSpeed * deltaTime;
        
            // Movimento do fundo
            fundo1 -= currentObjectSpeedFundo * deltaTime;
            fundo2 -= currentObjectSpeedFundo * deltaTime;
        
            // Movimento do fundo distante
            longe1 -= currentObjectSpeedFundoLonge * deltaTime;
            longe2 -= currentObjectSpeedFundoLonge * deltaTime;
        
            // Movimento dos objetos
            for (GameObject obj : objects) {
                if (!obj.isEnemytrashBoss) {
                    obj.rect.x -= currentObjectSpeed * deltaTime;
                }
            }
        }else{
            objects.clear();
        }

        Iterator<GameObject> iter = objects.iterator();
        while (iter.hasNext()) {
            GameObject obj = iter.next();

            if (obj.isEnemytrashBoss) {
                continue;
            }


            if (obj.rect.overlaps(player.getHitbox())) {
                if (obj.isCoin) {
                    gameSounds.colletcCoin.play(0.3f);
                    score = Math.max(0, score + 10);
                    ambientacao = Math.max(0, ambientacao + 1);
                    poluicao = Math.max(0, poluicao - 1);
                    iter.remove();

                    if (ambientacao % speedIncreaseInterval == 0 && atualSpeed < maxSpeed) {
                        atualSpeed = objectSpeed + (ambientacao / speedIncreaseInterval) * speedIncreaseAmount;
                    }


                    if (ambientacao % speedIncreaseIntervalFundo == 0 && atualSpeedFundo < maxSpeedFundo) {
                        atualSpeedFundo = objectSpeedFundo + (ambientacao / speedIncreaseIntervalFundo) * speedIncreaseAmountFundo;
                        currentObjectSpeedFundo = atualSpeedFundo; // Atualiza a velocidade atual do fundo
                    }

                    if (ambientacao % speedIncreaseIntervalFundoLonge == 0 && atualSpeedFundoLonge < maxSpeedFundoLonge) {
                        atualSpeedFundoLonge = objectSpeedFundoLonge + (ambientacao / speedIncreaseIntervalFundoLonge) * speedIncreaseAmountFundoLonge;
                        currentObjectSpeedFundoLonge = atualSpeedFundoLonge; // Atualiza a velocidade atual do fundo
                    }

                } else if (obj.isEnemy) {
                    ambientacao = Math.max(0, ambientacao - 1);
                    poluicao = Math.max(0, poluicao + 1);
                    iter.remove();
                } else if(obj.isThree){
                    System.out.println("Game Over! Pontuação: " + score);
                    gameOver = true;
                    return;
                } else if(obj.isEnemytrashBoss){
                    return;
                }else {
                    
                    System.out.println("Game Over! Pontuação: " + score);
                    gameOver = true;
                    return;
                }
            }

            if (obj.rect.x + obj.rect.width < 0) iter.remove();
        }

        if (TimeUtils.nanoTime() - lastObjectTime > nextSpawnDelay && !activeBoss && !freeMode) {
            spawnObject();
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        gameSounds.backgroundSound.pause();
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        //gameSounds.backgroundSound.resume();
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        pause();
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        obstacleTexture.dispose();
        coinTexture.dispose();
        enemyTexture.dispose();
        threeTexture.dispose();
        bossTexture.dispose();
        backgroundTexture.dispose();
        stage.dispose();
        player.dispose();
        whitePixelTexture.dispose();
        victoryPlateGui.dispose();
        textureMain.dispose();
        plateDeathBossTexture.dispose();
        chaoTexture.dispose();
        fundoLongeTexture.dispose();
        gameSounds.dispose();
        
    }
}


class GameObject {
    Rectangle rect;
    Texture texture;
    boolean isCoin;
    boolean isEnemy;
    boolean isThree;
    boolean isEnemytrashBoss;
    int health; 
    int maxHealth;

    public GameObject(Rectangle rect, Texture texture, boolean isCoin, boolean isEnemy, 
                     boolean isThree, boolean isEnemytrashBoss, int health, int maxHealth) {
        this.rect = rect;
        this.texture = texture;
        this.isCoin = isCoin;
        this.isEnemy = isEnemy;
        this.isThree = isThree;
        this.isEnemytrashBoss = isEnemytrashBoss;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public GameObject(Rectangle rect, Texture texture, boolean isCoin, boolean isEnemy, 
                     boolean isThree, boolean isEnemytrashBoss) {
        this(rect, texture, isCoin, isEnemy, isThree, isEnemytrashBoss, 
             isEnemytrashBoss ? 100 : 0,  // health: 100 para boss, 0 para outros
             isEnemytrashBoss ? 100 : 0); // maxHealth
    }
}
