
package com.gameapps.game;

import com.badlogic.gdx.graphics.Texture;

public class TextureMain {

    
    public Texture obstacleTexture;
    public Texture coinTexture;
    public Texture enemyTexture;
    public Texture threeTexture;
    public Texture enemyTrashBossTexture;
    public Texture enemyTrashBossTextureFinal;


    public Texture backgroundTexture1;
    public Texture backgroundTexture2;
    public Texture backgroundTexture3;
    public Texture backgroundTexture4;


    public Texture bossTextTexture1;
    public Texture bossTextTexture2;
    public Texture bossTextTexture3;

    public Texture bossBulletTexture;
    public Texture bulletTexture;


    public Texture chaoTexture;
    public Texture fundoLongeTexture;

    public Texture pressKeyInterativeTexture;
    
    public Texture pressKeyLeftTexture;
    public Texture pressKeyRightTexture;

    public Texture bossDevilTexture;




    public TextureMain(){
        loadTexture();
    }


    private void loadTexture(){



        bossDevilTexture = new Texture("bossDevil/idle/devil_idle_0001.png");



        obstacleTexture = new Texture("obstaculo.png");
        coinTexture = new Texture("coin.png");
        enemyTexture = new Texture("enemy.png");
        threeTexture = new Texture("threeEnemy.png");

        enemyTrashBossTexture = new Texture("enemytrashBoss.png");
        enemyTrashBossTextureFinal = new Texture("enemytrashBossFinal.png");



        backgroundTexture1 = new Texture("fundoperto1.png");
        backgroundTexture2 = new Texture("fundoperto2.png");
        backgroundTexture3 = new Texture("fundoperto3.png");
        backgroundTexture4 = new Texture("fundoperto.png");

        chaoTexture = new Texture("chao.png");
        fundoLongeTexture = new Texture("fundolonge.jpg");



        bossTextTexture1 = new Texture("nameBossRonaldo.png"); 
        bossTextTexture2 = new Texture("nameBossBluePen.png"); 
        bossTextTexture3 = new Texture("nameBossX5bss.png"); 



        bossBulletTexture = new Texture("bulletBoss.png");
        bulletTexture = new Texture("player/tiro.png");


        pressKeyInterativeTexture = new Texture("iconPressKeyboard/keyE.png");

        pressKeyLeftTexture = new Texture("iconPressKeyboard/keyLeft.png");
        pressKeyRightTexture = new Texture("iconPressKeyboard/keyRight.png");





    }



    public void dispose() {
        obstacleTexture.dispose();
        coinTexture.dispose();
        enemyTexture.dispose();
        threeTexture.dispose();
        enemyTrashBossTexture.dispose();
        enemyTrashBossTextureFinal.dispose();

        bossTextTexture1.dispose();
        bossTextTexture2.dispose();
        bossTextTexture3.dispose();


        bossBulletTexture.dispose();
        bulletTexture.dispose();

        chaoTexture.dispose();
        fundoLongeTexture.dispose();

        pressKeyInterativeTexture.dispose();

        pressKeyLeftTexture.dispose();
        pressKeyRightTexture.dispose();


        //depose devil boss
        bossDevilTexture.dispose();
    }
    


    


}
