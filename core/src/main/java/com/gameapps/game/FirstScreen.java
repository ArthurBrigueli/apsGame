package com.gameapps.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FirstScreen implements Screen {
    private final Game game;
    private final GameScreen gameScreen;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;

    public FirstScreen(Game game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        

        background = new Texture(Gdx.files.internal("cenario1.png"));
        

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        

        TextButton startButton = new TextButton("INICIAR JOGO", buttonStyle);
        startButton.setSize(400, 100);
        startButton.setPosition(
            Gdx.graphics.getWidth()/2 - startButton.getWidth()/2,
            Gdx.graphics.getHeight()/2 - startButton.getHeight()/2
        );
        
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });
        
        stage.addActor(startButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        // Desenha o background
        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        
        // Desenha o título do jogo
        font.draw(batch, "APS RUNNING 2D", 
                 Gdx.graphics.getWidth()/2 - 50, 
                 Gdx.graphics.getHeight() - 50);
        batch.end();
        

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        if (background != null) {
            background.dispose();
        }
        stage.dispose();
    }
}