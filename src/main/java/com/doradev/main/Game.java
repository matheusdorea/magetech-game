package com.doradev.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.doradev.entities.Entity;
import com.doradev.entities.Player;
import com.doradev.graphics.Spritesheet;
import com.doradev.world.World;

public class Game extends Canvas implements Runnable, KeyListener {
    //Variáveis de tamanho da tela
    public static final int SCALE = 3;
    public static final int WIDTH = 240 * SCALE;
    public static final int HEIGHT = 160 * SCALE;

    //Variável que controla o início e se o jogo deve continuar rodando
    public boolean isRunning;

    //objeto de thread
    public Thread thread;

    //objeto da spritesheet, entidades, jogador e mundo
    public static List<Entity> entities;
    public static Spritesheet spritesheet;
    public static Player player;
    public static World world;

    public static Random rand;

    //Método principal
    public static void main(String[] args) {
        Game game = new Game();
        game.initFrame();
        game.start();
    }

    //método construtor
    public Game() {
        rand = new Random();
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        spritesheet = new Spritesheet("/spritesheet.png");
        entities = new ArrayList<Entity>();

        player = new Player(0, 0, 64, 64, spritesheet.getSprite(0, 0, 16, 16));
        entities.add(player);

        world = new World("/map1.png"); 
    }

    //método que inicia janela
    public void initFrame() {
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setTitle("magetech");
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    //método de inicialização das threads
    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).tick();
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        //limpando tela
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //desenhando tiles
        world.render(g);

        //desenhando entidades
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(g);
        }

        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double time = System.currentTimeMillis();
        requestFocus();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;

                if (System.currentTimeMillis() - time >= 1000) {
                    System.out.println("fps: " + frames);
                    time += 1000;
                    frames = 0;
                }
            }
        }

        stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
            e.getKeyCode() == KeyEvent.VK_D) {
            player.setRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                    e.getKeyCode() == KeyEvent.VK_A) {
            player.setLeft(true);
            
        }

        if (e.getKeyCode() == KeyEvent.VK_UP ||
            e.getKeyCode() == KeyEvent.VK_W) {
            player.setUp(true);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN ||
                    e.getKeyCode() == KeyEvent.VK_S) {
            player.setDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
            e.getKeyCode() == KeyEvent.VK_D) {
            player.setRight(false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                    e.getKeyCode() == KeyEvent.VK_A) {
            player.setLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP ||
            e.getKeyCode() == KeyEvent.VK_W) {
            player.setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN ||
                    e.getKeyCode() == KeyEvent.VK_S) {
            player.setDown(false);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }


}