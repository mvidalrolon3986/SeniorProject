package GameLevel;

import Main.Game;
import Utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelHandler {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level level1;



    public LevelHandler(Game game) {
        this.game = game;
        importLevelSprites();
        level1 = new Level(LoadSave.GetLevelData());
    }

    private void importLevelSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.Level_Atlas);
        levelSprite = new BufferedImage[48];
        for(int x = 0; x < 4; x++)
            for( int i = 0; i < 12; i++){
                int index = x*12+i;
                levelSprite[index] = img.getSubimage(i*32, x*32, 32, 32);
            }
    }


    public void draw(Graphics g){

        for (int x = 0; x<Game.TILES_IN_HEIGHT; x++)
            for (int i = 0; i<Game.TILES_IN_WIDTH; i++){
                int index = level1.getSpriteIndexData(i, x);
                g.drawImage(levelSprite[index],Game.TILES_SIZE * i, Game.TILES_SIZE * x, Game.TILES_SIZE, Game.TILES_SIZE, null);



            }




        }
        public void update(){






    }

    public Level getCurrentLevel(){
        return level1;
    }
}