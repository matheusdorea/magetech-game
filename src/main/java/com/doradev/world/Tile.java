package com.doradev.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.doradev.main.Game;

public class Tile {
    //pegando sprites de chão e parede do spritesheet
    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(16*3, 0, 16, 16);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16*4, 0, 16, 16);
    
    private int x;
    private int y;
    private int width;
    private int height;

    private BufferedImage sprite;

    public Tile(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.x, y  - Camera.y, width, height, null);
    }
}
