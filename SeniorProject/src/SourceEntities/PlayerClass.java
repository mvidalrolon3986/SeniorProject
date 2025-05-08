package SourceEntities;

import Main.Game;
import Utilities.LoadSave;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import static Utilities.OtherMethods.*;
import static Utilities.ConstantVariables.PlayerConstants.*;

public class PlayerClass extends Entity {
    private BufferedImage[][] animations;
    private int animationTicker, animationIndex, animationSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private float fallingSpeed = 0f;
    private float jumpGravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float collisionFallSpeed = 0.5f * Game.SCALE;
    private boolean intheAir = false;

    public PlayerClass(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        inithitbox(x, y, 20*Game.SCALE, 28*Game.SCALE);

    }

    public void update() {
        updatePosition();
        updateAnimation();
        setAnimation();


    }


    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)(hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);

    }
    private void updateAnimation() {
        animationTicker++;
        if (animationTicker >= animationSpeed) {
            animationTicker = 0;
            animationIndex++;
            if (animationIndex >= GetCharacterSpriteAmount(playerAction)) {
                    animationIndex = 0;
                    attacking = false;


                }


            }
        }


    private void setAnimation() {
        int StartAni = playerAction;

        if (moving)
            playerAction = RUN;
        else
            playerAction = IDLE;
        if(intheAir){
            if(fallingSpeed <0)
                playerAction = JUMP;
            else
                playerAction = FALLING;

        }

        if (attacking)
            playerAction = ATTACK;

        if(StartAni != playerAction)
            resetanimationTicker();


    }

    private void resetanimationTicker() {
        animationTicker = 0;
        animationIndex = 0;



    }

    private void updatePosition() {


        moving = false;

        if (jump)
            jump();
        if (!left && !right && !intheAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!intheAir) {
            if (!IsEntityOnFloor(hitbox, lvlData)) {
                intheAir = true;
            }
        }

        if (intheAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + fallingSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += fallingSpeed;
                fallingSpeed += jumpGravity;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = GetEntityYPositionUnderRoofTileOrAboveFloorTile(hitbox, fallingSpeed);
                if (fallingSpeed > 0)
                    resetInAir();
                else
                    fallingSpeed = collisionFallSpeed;
                updateXPosition(xSpeed);
            }

        } else
            updateXPosition(xSpeed);
        moving = true;
    }

    private void jump() {
        if (intheAir)
            return;
        intheAir = true;
        fallingSpeed = jumpSpeed;

    }

    private void resetInAir() {
        intheAir = false;
        fallingSpeed = 0;

    }


    private void updateXPosition(float xSpeed) {
            if(CanMoveHere(hitbox.x +xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;

        }else{
            hitbox.x = GetEntityXPositionNextToWall(hitbox, xSpeed);

            }
    }



    private void loadAnimations() {
            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.Player_Atlas);

            animations = new BufferedImage[9][6];
            for (int a = 0; a < animations.length; a++)
                for (int i = 0; i < animations[a].length; i++)
                    animations[a][i] = img.getSubimage(i * 64, a * 40, 64, 40);


    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            intheAir = true;


    }




    public void resetBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;

    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;



    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }
}