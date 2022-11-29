package object;

import com.minorproject.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Door extends SuperObject{

    GamePanel gp;
    public OBJ_Door(GamePanel gp){

        this.gp = gp;
        name = "Door";
        try{
//            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("object/key.png"));
            File file = new File("res/object/door.png");
            FileInputStream fis = new FileInputStream(file);
            image = ImageIO.read(fis);
            uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision=true;
    }

}
