package com.minorproject;

import object.OBJ_KEY;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font fn,congo,maruMonica;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message= "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");


    public UI(GamePanel gp){
        this.gp = gp;
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/maruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT,is);
        }
        catch(FontFormatException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
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

        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //PLAY STATE
        if(gp.gameState == gp.playState){
            //DO play stuff later
        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

        //DIAlOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
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

            // No. Of Keys
            if(gp.gameState == gp.playState){
                g2.setFont(fn);
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
                g2.drawString("x "+ gp.player.hasKey,74,65);

            }
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

    public void drawTitleScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "2-D Adventure Game";
        int x = getXForCenter(text);
        int y = gp.tileSize * 3;

        //SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+5,y+5);

        //MAIN COLOUR
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //BLUE BOY IMAGE
        x= gp.screenWidth/2-gp.tileSize;
        y+=gp.tileSize*1.5;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize *2,gp.tileSize*2,null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        text = "NEW GAME";
        x = getXForCenter(text);
        y +=gp.tileSize *4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }
//        text = "LOAD GAME";
//        x = getXForCenter(text);
//        y +=gp.tileSize;
//        g2.drawString(text,x,y);
//        if(commandNum == 1){
//            g2.drawString(">",x-gp.tileSize,y);
//        }
        text = "QUIT";
        x = getXForCenter(text);
        y +=gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenter(text),y = gp.screenHeight/2;
        g2.drawString(text , x,y);
    }

    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x+=gp.tileSize;
        y+=gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawSubWindow(int x, int y , int width , int height){

        Color c = new Color(0,0,0,210 ); // Create a new color
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXForCenter(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
