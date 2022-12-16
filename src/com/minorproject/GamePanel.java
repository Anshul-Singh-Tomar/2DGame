package com.minorproject;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

      //SCREEN SETTINGS
    final int originalTileSize=16;
    final int scale=3;

    public final int tileSize= originalTileSize * scale;
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD Settings
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;

    int FPS =60;
    //SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH= new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionCheker cChecker = new CollisionCheker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 1;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta=0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer=0;
//        int drawcount = 0;

        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta+=(currentTime - lastTime)/ drawInterval;
            timer+=(currentTime - lastTime);
            lastTime = currentTime;
            if(delta>=1){
                update();
                repaint();
                delta--;
//                drawcount++;
            }

//            if(timer>=1000000000){
//                System.out.println("FPS: "+drawcount);
//                drawcount = 0;
//                timer=0;
//            }
        }
    }

    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        //TILE
        tileM.draw(g2);

        //OBJECTS
        for(int i=0;i<obj.length;i++){
            if(obj[i]!=null){
                obj[i].draw(g2,this);
            }
        }

        //PLAYER
        player.draw(g2);

        //UI
        ui.draw(g2);

        //DEBUG
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time "+passed,10,400);
        }

        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){

        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
