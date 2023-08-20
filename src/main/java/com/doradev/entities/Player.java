package com.doradev.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.doradev.main.Game;
import com.doradev.world.Camera;
import com.doradev.world.World;

public class Player extends Entity {
    private boolean right = false;
    private boolean left = false;
    private boolean down = false;
    private boolean up = false;

    private int speed = 3;

    private BufferedImage[] playerRight;
    private BufferedImage[] playerLeft;
    
    private boolean moved;
    private int right_dir = 1, left_dir = 0;
    private int dir = right_dir;

    private int curAnimation = 0;
    private int curFrames = 0;
    private int targetFrames = 10;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        playerRight = new BufferedImage[3];
        playerLeft = new BufferedImage[3];

        for (int i = 0; i < playerRight.length; i++) {
            playerRight[i] = Game.spritesheet.getSprite(16*i, 0, 16, 16);
        }

        for (int i = 0; i < playerLeft.length; i++) {
            playerLeft[i] = Game.spritesheet.getSprite(16*i, 16, 16, 16);
        }

    }

    public void tick() {
        moved = false;
        if (right && World.isFree(this.getX() + speed, this.getY())) {
            dir = right_dir;
            moved = true;
            x+=speed;
        }
        if (left && World.isFree(this.getX()-speed, this.getY())) {
            dir = left_dir;
            moved = true;
            x-=speed;
        }

        if (down && World.isFree(this.getX(), this.getY()+speed)) {
            moved = true;
            y+=speed;
        }
        if (up && World.isFree(this.getX(), this.getY()-speed)) {
            moved = true;
            y-=speed;
        }

        if (moved) {
            curFrames++;
            if (curFrames == targetFrames) {
                curFrames = 0;
                curAnimation++;
                if (curAnimation == playerRight.length) {
                    curAnimation = 1;
                }
            }
        } else {
            curAnimation = 0;
        }

        //movendo camera
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 64 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 64 - Game.HEIGHT);
    }

    public void render(Graphics g) {
        if (dir == right_dir) {
            g.drawImage(playerRight[curAnimation], this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight(), null);
        } else if (dir == left_dir) {
            g.drawImage(playerLeft[curAnimation], this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight(), null);
        }
    }

    public void setRight(boolean _right) {
        this.right = _right;
    }

    public void setLeft(boolean _left) {
        this.left = _left;
    }

    public void setUp(boolean _up) {
        this.up = _up;
    }

    public void setDown(boolean _down) {
        this.down = _down;
    }
    
}
