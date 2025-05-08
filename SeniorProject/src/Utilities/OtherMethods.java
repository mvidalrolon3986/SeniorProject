package Utilities;

import Main.Game;

import java.awt.geom.Rectangle2D;

public class OtherMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!Solid(x, y, lvlData))
            if (!Solid(x + width, y + height, lvlData))
                if (!Solid(x + width, y, lvlData))
                    if (!Solid(x, y + height, lvlData))
                        return true;
        return false;


    }

    private static boolean Solid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;


        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        else
            return false;


    }

    public static float GetEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            int tileXPosition = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPosition + xOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;

        }


    }

    public static float GetEntityYPositionUnderRoofTileOrAboveFloorTile(Rectangle2D.Float hitbox, float fallingSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (fallingSpeed > 0) {

            int tileYPosition = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPosition + yOffset - 1;
        } else {

            return currentTile * Game.TILES_SIZE;


        }
    }
public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int [][] lvlData) {
    if (!Solid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
        if (!Solid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
            return false;

    return true;


}
}