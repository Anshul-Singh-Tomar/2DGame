package com.minorproject;

import object.OBJ_KEY;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font fn,congo;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message= "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");


    public UI(GamePanel gp){
        this.gp = gp;
        fn = new Font("Times New Roman",Font.PLAIN,40);
        congo = new Font("Algerian",Font.BOLD,25);

        OBJ_KEY key = new OBJ_KEY(gp);
        keyImage = key.image;
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        if(gameFinished){

            g2.setFont(fn);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x,y;

            text = "You Found the Treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize * 3);
            g2.drawString(text,x,y);

//            g2.setFont(congo);
//            g2.setColor(Color.YELLOW);
//
//            text = "Your Time is "+dFormat.format(playTime);
//            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
//            x = gp.screenWidth/2 - textLength/2;
//            y = gp.screenHeight/2 + (gp.tileSize * 4);
//            g2.drawString(text,x,y);

            g2.setFont(congo);
            g2.setColor(Color.YELLOW);

            text = "CONGRATULATIONS!!!!!!\n You Have Completed The Game";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize * 3);
            g2.drawString(text,x,y);

            gp.gameThread = null;
        }
        else{
            g2.setFont(fn);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("x "+ gp.player.hasKey,74,65);

            // PLAY TIME
//            playTime +=(double)1/60;
//            g2.drawString("Time:"+dFormat.format(playTime),gp.tileSize * 11 ,65);

            //MESSAGE
            if(messageOn){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize * 5);

                messageCounter++;

                if(messageCounter > 90){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
