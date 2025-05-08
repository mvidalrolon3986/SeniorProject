package Utilities;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {


    public static final String Player_Atlas = "CharacterSprite.png";
    public static final String Level_Atlas = "LevelSprites.png";
    public static final String First_Level = "FirstLevel.png";
    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename );
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;

    }

    public static int [][] GetLevelData(){
        int [][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(First_Level);


        for(int j = 0; j< img.getHeight(); j++)
            for( int i = 0; i <img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48 )
                    value = 0;
                levelData[j][i] = value;
            }
        return levelData;




    }

}
