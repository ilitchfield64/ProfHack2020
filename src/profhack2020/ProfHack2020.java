/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfHack2020;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import profhack2020.music;
import java.util.ArrayList;

/**
 *
 * @author Gavin
 */
public class ProfHack2020 extends JPanel implements KeyListener {
    
    final int SCREEN_WIDTH = 900;
    final int SCREEN_HEIGHT = 1000;
    
    Rectangle playerRect;
    Rectangle[] stars;
    Rectangle bulletBase;
    Rectangle bullet1;
    Rectangle bullet2;
    Rectangle enemyRect;
    
    Image ship;
    
    boolean enemeyOnField = false;
    
    int playerX = 0;
    int bulletY = 0;
    int bulletSpeed = 3;
    int clipSize = 3;
    int moveSpeed = 3;
    int starSpeed = 3;
    
    ArrayList<Rectangle> bullets1 = new ArrayList<Rectangle>(0);
    ArrayList<Rectangle> bullets2 = new ArrayList<Rectangle>(0);
    
    String filepath = "src/arcade/Catch the mystery 120 BPM.wav";
    music musicObject = new music();
    
    public Random Gen = new Random();
    
    Clock updateClock = Clock.systemDefaultZone();
    Clock temp = Clock.offset(updateClock, Duration.ofMillis(1));
    Instant update = temp.instant();
    
    
    public ProfHack2020(){
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        
        musicObject.playMusic(filepath);
        playerRect = new Rectangle((SCREEN_WIDTH / 2) - 25, SCREEN_HEIGHT - (SCREEN_HEIGHT / 4) - 25, 162, 240);
        enemyRect = new Rectangle(0, 0, 50, 50);
        bulletBase = new Rectangle(playerRect.x + (playerRect.width/2), playerRect.y, 5, 25);
        bullet1 = new Rectangle(bulletBase.x, bulletBase.y, bulletBase.width, bulletBase.height);
        bullet2 = new Rectangle(bulletBase.x - 8, bulletBase.y, bulletBase.width, bulletBase.height);
        stars = new Rectangle[100];
        for (int i = 0; i < stars.length; i++) {
            int x = Gen.nextInt(SCREEN_WIDTH);
            int y = Gen.nextInt(SCREEN_HEIGHT);
            stars[i] = new Rectangle(x, y, 5, 5);
        }
        try {
            ship = ImageIO.read(new File("src/arcade/ship.png"));
            

        } catch (IOException e) {
            print("Image missing");
        }
    }
    
    public void update() {
        playerRect.x += playerX;
        bulletY = bulletSpeed * -1;
        Boarders(playerRect);
        bulletBase.x = playerRect.x + (playerRect.width/2);
        bulletBase.y = playerRect.y;
        if(!enemeyOnField){
            spawnEnemy(enemyRect);
            for(int i = 0; i < 100; i ++){
                enemyRect.y ++;
            }
            for(int i = 0; i < 100; i ++){
                enemyRect.x ++;
            }
            for(int i = 0; i < 100; i ++){
                enemyRect.x --;
            }
            deleteEnemy(enemyRect);
        }
        
        if(!bullets1.isEmpty()){
            for(int i = 0; i < bullets1.size(); i ++){
                if(bullets1.get(i).y <= 0){
                    bullets1.remove(i);
                    bullets2.remove(i);
                    
                }else{
                    bullet1.y += bulletY;
                    bullet2.y += bulletY;
                    bullets1.set(i, bullet1);
                    bullets2.set(i, bullet2);
                }
            }
        }else{
            bullet1 = new Rectangle(bulletBase.x, bulletBase.y, bulletBase.width, bulletBase.height);
            bullet2 = new Rectangle(bulletBase.x - 8, bulletBase.y, bulletBase.width, bulletBase.height);
        }
        
        
        
        for (int i = 0; i < stars.length; i++) {
            int x = Gen.nextInt(SCREEN_WIDTH);
            int y = Gen.nextInt(700) * -1;
            starMove(stars[i], x, y);
        }
    }
    public void spawnEnemy(Rectangle b){
        b.x = Gen.nextInt(SCREEN_WIDTH);
        b.y = -50;
    }
    public void deleteEnemy(Rectangle b){
        b.x = Gen.nextInt(SCREEN_WIDTH);
        b.y = -50;
    }
    public void starMove(Rectangle Star, int x, int y) {
        Star.y += starSpeed;
        if (Star.y >= SCREEN_HEIGHT) {
            Star.y = y;
            Star.x = x;
        }

    }
    public void Boarders(Rectangle b) {

        if (b.x <= 0) {
            b.x += moveSpeed;
        }
        if (b.x >= (SCREEN_WIDTH - (b.width - 1))) {
            b.x -= moveSpeed;
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(Color.WHITE);
        for (int i = 0; i < stars.length; i++) {
            g.fillRect(stars[i].x, stars[i].y, stars[i].width, stars[i].height);
        }
        
        g.setColor(Color.RED);
        for(int i = 0; i < bullets1.size(); i ++){
            g.fillRect(bullets1.get(i).x, bullets1.get(i).y, bullets1.get(i).width, bullets1.get(i).height);
            g.fillRect(bullets2.get(i).x, bullets2.get(i).y, bullets2.get(i).width, bullets2.get(i).height);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(enemyRect.x, enemyRect.y, enemyRect.width, enemyRect.height);
        
        g.drawImage(ship, playerRect.x, playerRect.y, playerRect.width, playerRect.height, null);

        if (updateClock.instant().compareTo(update) >= 0) {
            resetUpdateClock();
            update();
        }
        repaint();
    }
    
    public void resetUpdateClock() {
        temp = Clock.offset(updateClock, Duration.ofMillis(4));
        update = temp.instant();
        //clock2 = clock2.plusSeconds(2);
    }
    
    public final void print(Object b) {
        System.out.println(b);
        //this makes printing easier
    }

    public static void main(String[] args) {
        ProfHack2020 game = new ProfHack2020();
        JFrame frame = new JFrame();
        frame.setTitle("Arcade");
        frame.add(game);
        frame.pack();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ke.getKeyCode() == KeyEvent.VK_A) {
            playerX = moveSpeed*-1;
        }
        if (ke.getKeyCode() == KeyEvent.VK_D) {
            playerX = moveSpeed;
        }
        if (ke.getKeyCode() == KeyEvent.VK_W) {
            bullets1.add(bulletBase);
            bullets2.add(bulletBase);
        }
        if (ke.getKeyCode() == KeyEvent.VK_E) {
            System.exit(0);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ke.getKeyCode() == KeyEvent.VK_A) {
            playerX = 0;
        }
        if (ke.getKeyCode() == KeyEvent.VK_D) {
            playerX = 0;
        }
    }
    
}
