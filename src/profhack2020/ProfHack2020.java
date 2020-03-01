/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profhack2020;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

/**
 *
 * @author Gavin O'Hanlon, Ian Litchfield, Jan Puzon, John Giles
 */
public class ProfHack2020 extends JPanel implements KeyListener {

    final int SCREEN_WIDTH = 600;
    final int SCREEN_HEIGHT = 800;
//Items on screen
    // Backgound
    Rectangle[] stars;
    // Midground boxes
    Rectangle bulletBase;
    Rectangle bullet1;
    Rectangle bullet2;

    ArrayList<Rectangle> bullets1 = new ArrayList<Rectangle>(0);
    ArrayList<Rectangle> bullets2 = new ArrayList<Rectangle>(0);

    // Foreground
    Rectangle playerRect; // Player hit box
    Rectangle enemyRect1; // Enemy Hitboxes
    Rectangle enemyRect2; // *
    Rectangle enemyRect3; // *
    Rectangle defaultEnemy;
    Rectangle enemyBullet1;
    Rectangle enemyBullet2;
    Rectangle enemyBullet3;
    
    Rectangle enemyAstroid1;
    Rectangle enemyAstroid2;
    Rectangle enemyAstroid3;

    Rectangle enemyBulletRect1;
    Rectangle enemyBulletRect2;
    Rectangle enemyBulletRect3;

    Image title;
    Image astroid;
    Image enemy;
    Image rocket;
    Image rocketLeft;
    Image rocketRight;
    Image fire1;
    Image fire2;
    Image fire3;
    Image fire4;
    Image fire5;
    Image fire6;
// Game state Variables   
    int score = 0; // Total enemies hit 
    boolean enemyHit = false; // Checks if the enemy was hit 
    boolean enemyOnField = false; // Checks if an enemy is present on the screen
    boolean shoot, special, left, right; // Input handling, used to smooth movement
    boolean spawnEnemy1 = false;
    boolean spawnEnemy2 = false;
    boolean spawnEnemy3 = false;
    boolean astroid1 = false;
    boolean astroid2 = false;
    boolean astroid3 = false;
    boolean startGame = false; // When false, Title Screen is displayed

    int starSpeed = 3; // Starfield Movement speed
    
    int Score = 0;
    int timerA = 0;
    int timer = 0;
    int timerF = 0;
    int tilt = 0;

// Player Variables
    int playerX = 0;
    int bulletY = 0;
    int bulletSpeed = 5; // Changed the speed to 6
    int astroidSpeed = 3;
    int clipSize = 3; // Special Move counter? 
    int moveSpeed = 3; // Player Movement Spee

// Imported files
    String filepath = "src/profhack2020/Catch the mystery 120 BPM.wav"; // BGM
    music musicObject = new music();

    public Random Gen = new Random();

    //boolean startGame = false;
// Clock
    Clock updateClock = Clock.systemDefaultZone();
    Clock temp = Clock.offset(updateClock, Duration.ofMillis(1));
    Instant update = temp.instant();

    public ProfHack2020() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        musicObject.playMusic(filepath); // Plays the background music
        playerRect = new Rectangle((SCREEN_WIDTH / 2) - 25, SCREEN_HEIGHT - (SCREEN_HEIGHT / 4) - 25, (SCREEN_WIDTH / 8), (SCREEN_WIDTH / 8)); // Initial start of the player
        // Enemies and attacks
        defaultEnemy = new Rectangle(-100, -50, 0, 0);
        enemyRect1 = new Rectangle(SCREEN_WIDTH / 16, -50, 50, 50);
        enemyRect2 = new Rectangle((SCREEN_WIDTH / 2) - (SCREEN_WIDTH / 32), -50, 50, 50);
        enemyRect3 = new Rectangle(SCREEN_WIDTH - (SCREEN_WIDTH / 8), -50, 50, 50);

        enemyBulletRect1 = new Rectangle(-100, 0, 5, 25);
        enemyBulletRect2 = new Rectangle(-100, 0, 5, 25);
        enemyBulletRect3 = new Rectangle(-100, 0, 5, 25);

        // Static debris
        enemyAstroid1 = new Rectangle(-100, 0, 50, 50);
        enemyAstroid2 = new Rectangle(-100, 0, 50, 50);
        enemyAstroid3 = new Rectangle(-100, 0, 50, 50);

        bulletBase = new Rectangle(playerRect.x + (playerRect.width / 2), playerRect.y, 5, 25);
        bullet1 = new Rectangle(bulletBase.x, bulletBase.y, bulletBase.width, bulletBase.height);
        bullet2 = new Rectangle(bulletBase.x - 8, bulletBase.y, bulletBase.width, bulletBase.height);
        stars = new Rectangle[100];

        for (int i = 0; i < stars.length; i++) { // Generates the random Stars on the background
            int x = Gen.nextInt(SCREEN_WIDTH);
            int y = Gen.nextInt(SCREEN_HEIGHT);
            stars[i] = new Rectangle(x, y, 5, 5);
        }
        try { // Images
            title = ImageIO.read(new File("src/profhack2020/title.gif"));

            astroid = ImageIO.read(new File("src/profhack2020/astroid.png"));
            enemy = ImageIO.read(new File("src/profhack2020/enemyTile.png"));
            rocket = ImageIO.read(new File("src/profhack2020/Rocket.png"));
            rocketRight = ImageIO.read(new File("src/profhack2020/RocketRight.png"));
            rocketLeft = ImageIO.read(new File("src/profhack2020/RocketLeft.png"));
            fire1 = ImageIO.read(new File("src/profhack2020/fire2.png"));
            fire2 = ImageIO.read(new File("src/profhack2020/fire3.png"));
            fire3 = ImageIO.read(new File("src/profhack2020/fire4.png"));
            fire4 = ImageIO.read(new File("src/profhack2020/fire5.png"));
            fire5 = ImageIO.read(new File("src/profhack2020/fire6.png"));
            fire6 = ImageIO.read(new File("src/profhack2020/fire7.png"));

        } catch (IOException e) {
            print("Image missing");
        }

    }

    public void update() {
        timerA++;
        timerF++;
        timer++;

        if (timer
                >= 210) {
            timer = 0;
        }

        if (timerF
                < 5) {
            timerF++;

        }
        if (timerF
                < 5) {
            timerF++;
        } else if (timerF >= 5 && timerF
                <= 10) {
            timerF++;
        }
        if (timerF
                >= 10) {
            timerF = 0;
        }
        // Astroids coming on screen
        if (astroid3) {
            if (enemyAstroid3.y >= SCREEN_HEIGHT + 50) {
                enemyAstroid3.y = -100;
                enemyAstroid3.x = Gen.nextInt((SCREEN_WIDTH - 50)) + 50;
                astroid3 = false;
            }
            enemyAstroid2.y += astroidSpeed;
        }
        if (astroid2) {
            if (enemyAstroid2.y >= SCREEN_HEIGHT + 50) {
                enemyAstroid2.y = -100;
                enemyAstroid2.x = Gen.nextInt((SCREEN_WIDTH - 50)) + 50;
                astroid2 = false;
            }
            enemyAstroid2.y += astroidSpeed;
        }
        if (astroid1) {
            if (enemyAstroid1.y >= SCREEN_HEIGHT + 50) {
                enemyAstroid1.y = -100;
                enemyAstroid1.x = Gen.nextInt((SCREEN_WIDTH - 50)) + 50;
                astroid1 = false;
            }
            enemyAstroid1.y += astroidSpeed;
        }

        if (timerA >= 500 && timerA
                <= 550) {
            astroid1 = true;
        }
        if (timerA
                == 500 + Gen.nextInt(
                        300)) {
            astroid2 = true;
        }
        if (timerA
                >= 1000) {
            timerA = 0;
        }

        playerMovement(); // Handles movement
        bulletY = bulletSpeed * -1;

        Boarders(playerRect);
        bulletBase.x = playerRect.x + (playerRect.width / 2);
        bulletBase.y = playerRect.y;
        if (enemyOnField) {
            if (!enemyHit) {
                deleteEnemy(enemyRect1);
            } else {

            }
        }
        // Bullet shooting timer and movement

        if (!bullets1.isEmpty()) {
            for (int i = 0; i < bullets1.size(); i++) {
                if (bullets1.get(i).y <= 0) {
                    bullets1.remove(i);
                    bullets2.remove(i);

                } else {
                    bullet1.y += bulletY;
                    bullet2.y += bulletY;
                    bullets1.set(i, bullet1);
                    bullets2.set(i, bullet2);
                }
            }
        } else {
            bullet1 = new Rectangle(bulletBase.x, bulletBase.y, bulletBase.width, bulletBase.height);
            bullet2 = new Rectangle(bulletBase.x - 8, bulletBase.y, bulletBase.width, bulletBase.height);
        }
        // Stars randoming
        for (int i = 0;
                i < stars.length;
                i++) {
            int x = Gen.nextInt(SCREEN_WIDTH);
            int y = Gen.nextInt(700) * -1;
            starMove(stars[i], x, y);
        }

        // This code handles the random generation of the Enemies
        if ((spawnEnemy()
                == 1) || spawnEnemy1) {
            enemyEntersScreen1(enemyRect1);
            int temp = Gen.nextInt(2);
            if (temp == 0) {
                enemyBulletRect1.x = enemyRect1.x;
                if (enemyBulletRect1.y < SCREEN_HEIGHT) {
                    enemyBulletRect1.y += bulletSpeed;
                } else {
                    enemyBulletRect1.y = enemyRect1.y;
                }
            }

            if (enemyBulletRect1.intersects(playerRect)) {
                gameOver();
            }
        }

        if (spawnEnemy()
                == 2 || spawnEnemy2) {
            enemyEntersScreen2(enemyRect2);
                        int temp = Gen.nextInt(2);
            if (temp == 0) {
                enemyBulletRect2.x = enemyRect2.x;
                if (enemyBulletRect2.y < SCREEN_HEIGHT) {
                    enemyBulletRect2.y += bulletSpeed;
                } else {
                    enemyBulletRect2.y = enemyRect2.y;
                }
            }

            if (enemyBulletRect2.intersects(playerRect)) {
                gameOver();
            }
        }

        if (spawnEnemy()
                == 3 || spawnEnemy3) {
            enemyEntersScreen3(enemyRect3);
                        int temp = Gen.nextInt(2);
            if (temp == 0) {
                enemyBulletRect2.x = enemyRect2.x;
                if (enemyBulletRect2.y < SCREEN_HEIGHT) {
                    enemyBulletRect2.y += bulletSpeed;
                } else {
                    enemyBulletRect2.y = enemyRect2.y;
                }
            }

            if (enemyBulletRect1.intersects(playerRect)) {
                gameOver();
            }
            
        }

        enemyHit();
        if (spawnEnemy1 == false){
            enemyBulletRect1.x = -100;
        }
                
        if (spawnEnemy2 == false){
            enemyBulletRect2.x = -100;
        }        
        if (spawnEnemy3 == false){
            enemyBulletRect2.x = -100;
        }
        playerHit();

    } /////// THE END OF UPDATE
// When ANYTHING intersects the player
    public void title(){
        
    }
    public void gameOver() {
        JOptionPane.showMessageDialog(null, "GAME OVER! Your Score was: " + Score);
        System.exit(0);
    }

    public void playerHit() {
        if (enemyAstroid1.intersects(playerRect)) {
            gameOver();
        }
        if (enemyAstroid2.intersects(playerRect)) {
            gameOver();

        }

    }

    // This decides what enemy goes where
    public int spawnEnemy() {
        if ((SCREEN_HEIGHT / 4) == Gen.nextInt(SCREEN_HEIGHT / 2)) {
            if (Gen.nextInt(4) == 1) {
                return (1);
            }
            if (Gen.nextInt(4) == 2) {
                return (2);
            }
            if (Gen.nextInt(4) == 3) {
                return (3);
            }
        }
        return 0;
    }

    public void leaveScreen(Rectangle enemyRect) {
        if (enemyRect.y > -75) {
            enemyRect.y--;
        }
    }

    // Enemy randomly will enter the screen
    public void enemyEntersScreen1(Rectangle enemyRect) {

        //System.out.println(spawnEnemy1);
        if (spawnEnemy1) {
            if (enemyRect.y <= 75) {
                enemyRect.y++;
            } else {
            }

        } else {
            enemyRect.x = Gen.nextInt((SCREEN_WIDTH - 100)) + 25;
           // int distance = Gen.nextInt(100) + 50;
            spawnEnemy1 = true;
        }
    }

    public void enemyEntersScreen2(Rectangle enemyRect) {

        //System.out.println(spawnEnemy2);
        if (spawnEnemy2) {
            if (enemyRect.y <= 75) {
                enemyRect.y++;
            } else {
            }

        } else {
            enemyRect.x = Gen.nextInt((SCREEN_WIDTH - 100)) + 25;
            int distance = Gen.nextInt(100) + 50;
            spawnEnemy2 = true;
        }
    }

    public void enemyEntersScreen3(Rectangle enemyRect) {

        //System.out.println(spawnEnemy3);
        if (spawnEnemy3) {
            if (enemyRect.y <= 50) {
                enemyRect.y++;
            } else {
            }

        } else {
            enemyRect.x = Gen.nextInt((SCREEN_WIDTH - 100)) + 25;
            int distance = Gen.nextInt(100) + 50;
            spawnEnemy3 = true;
        }
    }

    // Moves enemy off the screen when deaded and set spawn false
    public void enemyKilled(Rectangle enemyRect, Rectangle bullet) {
        enemyRect.x = defaultEnemy.x;
        enemyRect.y = defaultEnemy.y;
        //bullet.y = defaultEnemy.y;
        score++;

    }

    // Checks if the ememy is hit by bullet
    public void enemyHit() {
        if (enemyRect1.intersects(bullet1) || enemyRect1.intersects(bullet2)) {
            enemyKilled(enemyRect1, enemyBullet1);
            spawnEnemy1 = false;
            Score += 1;
        }
        if (enemyRect2.intersects(bullet1) || enemyRect2.intersects(bullet2)) {
            enemyKilled(enemyRect2, enemyBullet2);
            spawnEnemy2 = false;
            Score += 1;
        }
        if (enemyRect3.intersects(bullet1) || enemyRect3.intersects(bullet2)) {
            enemyKilled(enemyRect3, enemyBullet3);
            spawnEnemy3 = false;
            Score += 1;
        }
    }

    public void deleteEnemy(Rectangle b) {
        // Add lines for a death animation

        b.x = Gen.nextInt(SCREEN_WIDTH - 100);
        b.y = -50;

    }

    // This allows the starfield to move
    public void starMove(Rectangle Star, int x, int y) {
        Star.y += starSpeed;
        if (Star.y >= SCREEN_HEIGHT) {
            Star.y = y;
            Star.x = x;
        }

    }

    // Creates the borders preventing the player from leaving the screen
    public void Boarders(Rectangle b) {

        if (b.x <= 0) {
            b.x += moveSpeed;
        }
        if (b.x >= (SCREEN_WIDTH - (b.width - 1))) {
            b.x -= moveSpeed;
        }
    }

    // Graphics
    public void paint(Graphics g) { // Graphics Routine  **Why not make submethods that handle layers??**
        super.paint(g);
        // Draws the Background
        g.setColor(Color.BLACK); // Set Background color
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT); // Fills Background with black

        g.setColor(Color.WHITE);
        for (int i = 0; i < stars.length; i++) { // This randomizes stars on the background
            g.fillRect(stars[i].x, stars[i].y, stars[i].width, stars[i].height);
        }

        // Draws the Bullets
        g.setColor(Color.RED);
        for (int i = 0; i < bullets1.size(); i++) {
            g.fillRect(bullets1.get(i).x, bullets1.get(i).y, bullets1.get(i).width, bullets1.get(i).height);
            g.fillRect(bullets2.get(i).x, bullets2.get(i).y, bullets2.get(i).width, bullets2.get(i).height);
        }
        //Drawing Enemy Bullets
        g.fillRect(enemyBulletRect1.x+ 22, enemyBulletRect1.y, enemyBulletRect1.width, enemyBulletRect1.height);
        g.fillRect(enemyBulletRect2.x+ 22, enemyBulletRect2.y, enemyBulletRect2.width, enemyBulletRect2.height);
        g.fillRect(enemyBulletRect3.x+ 22, enemyBulletRect3.y, enemyBulletRect3.width, enemyBulletRect3.height);

        // Drawing the astroid
        g.drawImage(astroid, enemyAstroid1.x, enemyAstroid1.y, enemyAstroid1.width, enemyAstroid1.height, null);
        g.drawImage(astroid, enemyAstroid2.x, enemyAstroid2.y, enemyAstroid2.width, enemyAstroid2.height, null);

        // Enemy Draw Routines
        g.drawImage(enemy, enemyRect1.x, enemyRect1.y, enemyRect1.width, enemyRect1.height, null); // Draws enemy 1s box
        g.drawImage(enemy, enemyRect2.x, enemyRect2.y, enemyRect2.width, enemyRect2.height, null); // Draws enemy 2s box
        g.drawImage(enemy, enemyRect3.x, enemyRect3.y, enemyRect3.width, enemyRect3.height, null); // Draws enemy 3s box

        // Draws the Player on the screen
        g.drawImage(rocket, playerRect.x, playerRect.y, playerRect.width, playerRect.height, null); // Draws the player image

        // Draws flashing rectangle
        if (timerF <= 5) {
            g.setColor(Color.red);
            //g.fillRect(200, 150, 200, 150); // Rectangle
            if (timerF >= 10 && timerF <= 15) {
                g.setColor(Color.red);
                //g.fillRect(200, 150, 200, 150); // Rectangle
            }
        }

        // Fire Animation
        if (timer <= 35) {
            g.drawImage(fire1, playerRect.x + 22, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 36 && timer <= 70) {
            g.drawImage(fire2, playerRect.x + 22, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 71 && timer <= 105) {
            g.drawImage(fire3, playerRect.x + 22, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 106 && timer <= 140) {
            g.drawImage(fire4, playerRect.x + 22, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 141 && timer <= 175) {
            g.drawImage(fire5, playerRect.x + 22, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 176 && timer <= 210) {
            g.drawImage(fire6, playerRect.x + 22, playerRect.y + 73, 10, 15, null);
        }

        if (timer <= 35) {
            g.drawImage(fire6, playerRect.x + 43, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 36 && timer <= 70) {
            g.drawImage(fire5, playerRect.x + 43, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 71 && timer <= 105) {
            g.drawImage(fire4, playerRect.x + 43, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 106 && timer <= 140) {
            g.drawImage(fire3, playerRect.x + 43, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 141 && timer <= 175) {
            g.drawImage(fire2, playerRect.x + 43, playerRect.y + 73, 10, 15, null);
        }
        if (timer >= 176 && timer <= 210) {
            g.drawImage(fire1, playerRect.x + 43, playerRect.y + 73, 10, 15, null);
        }

        // Draws the ship tilting depending on which way the player is moving
        if (tilt == 0) {
            g.drawImage(rocket, playerRect.x, playerRect.y, playerRect.width, playerRect.height, null);
        }
        if (tilt == -1) {
            g.drawImage(rocketLeft, playerRect.x, playerRect.y, playerRect.width, playerRect.height, null);
        }
        if (tilt == 1) {
            g.drawImage(rocketRight, playerRect.x, playerRect.y, playerRect.width, playerRect.height, null);
        }

        if (updateClock.instant().compareTo(update) >= 0) { //updates clock cycle
            resetUpdateClock();
            update();
        }

        repaint(); // Repaints the screen

    }

    // Resets the clock
    public void resetUpdateClock() {
        temp = Clock.offset(updateClock, Duration.ofMillis(4));
        update = temp.instant();
        //clock2 = clock2.plusSeconds(2);
    }

    public final void print(Object b) {
        System.out.println(b);
        //this makes printing easier
    }

    public void astroidAttack(Rectangle b) {

    }

    public void playerMovement() { // This method will handle movement speed

        if (left) { // This moves player left
            // System.out.println("Left");
            playerRect.x = playerRect.x - moveSpeed;
        }
        if (right) { // This moves player right
            //  System.out.println("Right");
            playerRect.x = playerRect.x + moveSpeed;

        }
        playerRect.x += playerX;

    }

    public static void main(String[] args) {
        ProfHack2020 game = new ProfHack2020();
        JFrame frame = new JFrame();
        frame.setTitle("ProfHacks2020: Space Shooter");
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
    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_S:
                special = false;
                break;
            case KeyEvent.VK_W:
                shoot = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                tilt = 0;
                // System.out.println("A Released");
                break;
            case KeyEvent.VK_D:
                right = false;
                tilt = 0;
                //  System.out.println("D released");
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_S:
                special = true;
                break;
            case KeyEvent.VK_W:
                shoot = true;
                bullets1.add(bulletBase);
                bullets2.add(bulletBase);
                break;
            case KeyEvent.VK_A:
                left = true;
                tilt = -1;
                //  System.out.println("A Pressed");
                break;
            case KeyEvent.VK_D:
                right = true;
                tilt = 1;
                // System.out.println("D Pressed");
                break;
            case KeyEvent.VK_ESCAPE: // Emergency Esc from program
                System.exit(0);
        }
    }

}

