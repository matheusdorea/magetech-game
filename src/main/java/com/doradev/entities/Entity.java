package com.doradev.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.doradev.main.Game;
import com.doradev.world.Camera;

public class Entity {
    //estities's sprites
    public static BufferedImage LIFEPOTION_EN = Game.spritesheet.getSprite(8*16, 0, 32, 32);
    public static BufferedImage MANAPOTION_EN = Game.spritesheet.getSprite(5*16, 0, 16, 16);
    public static BufferedImage STAFF_EN = Game.spritesheet.getSprite(3*16, 16, 16, 16);
    public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(5*16, 16, 16, 16);

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y,this.getWidth(), this.getHeight(), null);
    }
}
