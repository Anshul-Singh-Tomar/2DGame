package entity;

import com.minorproject.GamePanel;

import java.util.Random;

public class NPC_oldMan extends Entity{

    public NPC_oldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }
    public void getImage(){
        up1 = setup("npc/oldman_up_1");
        up2 = setup("npc/oldman_up_2");
        down1 = setup("npc/oldman_down_1");
        down2 = setup("npc/oldman_down_2");
        left1 = setup("npc/oldman_left_1");
        left2 = setup("npc/oldman_left_2");
        right1 = setup("npc/oldman_right_1");
        right2 = setup("npc/oldman_right_2");
    }

    public void setDialogue(){

        dialogue[0] = "Hello, Traveler";
        dialogue[1] = "There is some treasure around \n here on this island";
        dialogue[2] = "There will be 3 doors on the way to\n the treasure, You need to find 3 keys \n to those doors";
        dialogue[3] = "Good Luck";

    }

    public void setAction(){

        actionLockCounter ++;

        if(actionLockCounter == 50){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i<=25){
                direction = "up";
            }
            if(i>25 && i<=50){
                direction = "down";
            }
            if(i>50 && i<=75){
                direction ="left";
            }
            if(i>75 && i<=100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak(){
        super.speak();
    }
}
