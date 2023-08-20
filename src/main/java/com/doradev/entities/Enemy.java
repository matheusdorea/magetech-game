package com.doradev.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.doradev.main.Game;
import com.doradev.world.Camera;
import com.doradev.world.World;

public class Enemy extends Entity {

    private int speed = 2;

    private BufferedImage[] enemy;

    private int frames = 0;
    private int targetFrames = 12;
    private int curAnimation = 0;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        enemy = new BufferedImage[2];
        enemy[0] = Game.spritesheet.getSprite(5*16, 16, 16, 16);
        enemy[1] = Game.spritesheet.getSprite(6*16, 16, 16, 16);
    }

    public void tick() {
        if (Game.rand.nextInt(100) < 30) {
            if (this.x < Game.player.getX() && World.isFree(this.x + speed, y)) {
                this.x += speed;
            } else if (this.x > Game.player.getX() && World.isFree(this.x - speed, y)) {
                this.x -= speed;
            }

            if (this.y < Game.player.getY() && World.isFree(this.x, y  + speed)) {
                this.y += speed;
            } else if (this.y > Game.player.getY() && World.isFree(this.x, y - speed)) {
                this.y -= speed;
            }
        }

        frames++;
        if (frames == targetFrames) {
            frames = 0;
            curAnimation++;
            if (curAnimation == enemy.length) {
                curAnimation = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(enemy[curAnimation], x - Camera.x, y - Camera.y, width, height, null);
    }
    
}
