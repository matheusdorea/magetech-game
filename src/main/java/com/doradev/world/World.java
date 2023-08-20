package com.doradev.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.doradev.entities.Enemy;
import com.doradev.entities.Entity;
import com.doradev.entities.Potion;
import com.doradev.main.Game;

public class World {
    private static Tile[] tiles;
    public static int WIDTH;
    public static int HEIGHT;
    public static final int TILE_SIZE = 64;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth()*map.getHeight()];
            tiles = new Tile[map.getWidth()*map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int i = 0; i < map.getWidth(); i++) {
                for (int j = 0; j < map.getHeight(); j++) {
                    //renderizando chão no mapa todo(floor)
                    tiles[i + (j*map.getWidth())] = new Floor(i*64, j*64, 64, 64, Tile.TILE_FLOOR);
                    if (pixels[i + (j*map.getWidth())] == 0xFFFFFFFF) {
                        //wall
                        tiles[i + (j*map.getWidth())] = new Wall(i*64, j*64, 64, 64, Tile.TILE_WALL);
                    } else if (pixels[i + (j*map.getWidth())] == 0xFF0026FF) {
                        //player
                        Game.player.setX(i*64);
                        Game.player.setY(j*64);
                    } else if (pixels[i + (j*map.getWidth())] == 0xFFFF00DC) {
                        //life potion
                        Game.entities.add(new Potion(i*64 +16, j*64 + 16, 32, 32, Entity.LIFEPOTION_EN));
                    } else if (pixels[i + (j*map.getWidth())] == 0xFFFFD800) {
                        //mana potion
                        Game.entities.add(new Potion(i*64, j*64, 64, 64, Entity.MANAPOTION_EN));
                    } else if (pixels[i + (j*map.getWidth())] == 0xFFFF6A00) {
                        //staff
                        Game.entities.add(new Potion(i*64, j*64, 64, 64, Entity.STAFF_EN));
                    } else if (pixels[i + (j*map.getWidth())] == 0xFFFF0000) {
                        //Enemy
                        Game.entities.add(new Enemy(i*64, j*64, 64, 64, Entity.ENEMY_EN));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFree(int xnext, int ynext) {
        int x1 = xnext / TILE_SIZE;
        int y1 = ynext / TILE_SIZE;

        int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = ynext / TILE_SIZE;

        int x3 = xnext / TILE_SIZE;
        int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1*World.WIDTH)] instanceof Wall) ||
                (tiles[x2 + (y2*World.WIDTH)] instanceof Wall) ||
                (tiles[x3 + (y3*World.WIDTH)] instanceof Wall) || 
                (tiles[x4 + (y4*World.WIDTH)] instanceof Wall));
    }

    public void render(Graphics g) {
        int xstart = Camera.x/64;
        int ystart = Camera.y/64;

        int xfinal = xstart + (Game.WIDTH/64) + 64;
        int yfinal = ystart + (Game.HEIGHT/64) + 64;

        for (int i = xstart; i <= xfinal; i++) {
            for (int j = ystart; j <= yfinal; j++) {
                if (i < 0 || j < 0 || i >= WIDTH || j >= HEIGHT)
                    continue;
                tiles[i + (j*WIDTH)].render(g);
            }
        }
    }
}
