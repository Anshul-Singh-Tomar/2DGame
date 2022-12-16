package entity;

import com.minorproject.GamePanel;
import com.minorproject.KeyHandler;
import com.minorproject.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey=0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp,KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2 );

        solidArea = new Rectangle();
        solidArea.x=1;
        solidArea.y=1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width=46;
        solidArea.height=46;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX=gp.tileSize * 23;
        worldY=gp.tileSize * 21;
        speed=4;
        direction="down";
    }
    public void getPlayerImage(){
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage Image = null;

        try{
            Image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/"+imageName+".png"));
            Image = uTool.scaleImage(Image,gp.tileSize,gp.tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return Image;
    }
    public void update(){
        if(!moving) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }
                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickupObject(objIndex);
            }
        }
            if(moving == true){
                //IF COLLISION == FALSE, PLAYER CAN MOVE
                if(!collisionOn){
                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                spriteCounter++;
                if(spriteCounter>12){
                    if(spriteNum==1){
                        spriteNum=2;
                    }
                    else if(spriteNum==2){
                        spriteNum=1;
                    }
                    spriteCounter=0;
                }
                pixelCounter +=speed;
                if(pixelCounter == 48){
                    moving = false;
                    pixelCounter= 0;
                }
            }
    }

    public void pickupObject(int i){

        if( i != 999){
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i]= null;
                    gp.ui.showMessage("You Found A Key!");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSE(3);
                        gp.obj[i] = null;
                        gp.ui.showMessage("Door Unlocked");
                        hasKey--;
                    }
                    else{
                        gp.ui.showMessage("Key Is Required");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed +=4;
                    gp.obj[i]=null;
                    gp.ui.showMessage("Power Up!!!!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image = null;
        switch (direction){
            case "up":
                if(spriteNum==1){
                    image=up1;
                }
                if(spriteNum==2){
                    image=up2;
                }if(spriteNum==1){
                    image=up1;
                }
                if(spriteNum==2){
                    image=up2;
                }
                break;
            case "down":
                if(spriteNum==1){
                    image=down1;
                }
                if(spriteNum==2){
                    image=down2;
                }
                break;
            case "left":
                if(spriteNum==1){
                    image=left1;
                }
                if(spriteNum==2){
                    image=left2;
                }
                break;
            case "right":
                if(spriteNum==1){
                    image=right1;
                }
                if(spriteNum==2){
                    image=right2;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,null);
    }
}




