/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profhack2020;

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
    Image ship;
// Game state Variables   
    int score = 0; // Total enemies hit 
    boolean enemyHit = false; // Checks if the enemy was hit 
    boolean enemyOnField = false; // Checks if an enemy is present on the screen
    boolean shoot, special, left, right; // Input handling, used to smooth movement

    int starSpeed = 3; // Starfield Movement speed
    
// Player Variables
    int playerX = 0;
    int bulletY = 0;
    int bulletSpeed = 5; // Changed the speed to 6
    int clipSize = 3; // Special Move counter? 
    int moveSpeed = 3; // Player Movement Spee
  

// Imported files
    String filepath = "src/profhack2020/Catch the mystery 120 BPM.wav"; // BGM
    music musicObject = new music(); 
    
    public Random Gen = new Random();
    
// Clock
    Clock updateClock = Clock.systemDefaultZone();
    Clock temp = Clock.offset(updateClock, Duration.ofMillis(1));
    Instant update = temp.instant();
    
    
    public ProfHack2020(){
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        
        musicObject.playMusic(filepath); // Plays the background music
        playerRect = new Rectangle((SCREEN_WIDTH / 2) - 25, SCREEN_HEIGHT - (SCREEN_HEIGHT / 4) - 25, (SCREEN_WIDTH/8), (SCREEN_WIDTH/8)); // Initial start of the player
        // These will exist off screen 
        enemyRect1 = new Rectangle(SCREEN_WIDTH / 16 , 50 , 50, 50); 
        enemyRect2 = new Rectangle((SCREEN_WIDTH / 2) - (SCREEN_WIDTH / 32), 50 , 50, 50);
        enemyRect3 = new Rectangle(SCREEN_WIDTH - (SCREEN_WIDTH / 8),50, 50, 50);
        
        bulletBase = new Rectangle(playerRect.x + (playerRect.width/2), playerRect.y, 5, 25);
        bullet1 = new Rectangle(bulletBase.x, bulletBase.y, bulletBase.width, bulletBase.height);
        bullet2 = new Rectangle(bulletBase.x - 8, bulletBase.y, bulletBase.width, bulletBase.height);
        stars = new Rectangle[100];
        
        for (int i = 0; i < stars.length; i++) { // Generates the random Stars on the background
            int x = Gen.nextInt(SCREEN_WIDTH);
            int y = Gen.nextInt(SCREEN_HEIGHT);
            stars[i] = new Rectangle(x, y, 5, 5);
        }
        try { // 
            ship = ImageIO.read(new File("src/profhack2020/ship2.png"));
            

        } catch (IOException e) {
            print("Image missing");
        }
    }
    
    public void update() {
        playerMovement(); // Handles movement
        bulletY = bulletSpeed * -1;
        Boarders(playerRect);
        bulletBase.x = playerRect.x + (playerRect.width/2);
        bulletBase.y = playerRect.y;
        if(enemyOnField){
            if(!enemyHit) {
            deleteEnemy(enemyRect1);
            }else{
                
            }
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
    
    // This removes the enemy from the screen 
    public void deleteEnemy(Rectangle b){
        // Add lines for a death animation
        
        b.x = Gen.nextInt(SCREEN_WIDTH);
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
        for(int i = 0; i < bullets1.size(); i ++){
            g.fillRect(bullets1.get(i).x, bullets1.get(i).y, bullets1.get(i).width, bullets1.get(i).height);
            g.fillRect(bullets2.get(i).x, bullets2.get(i).y, bullets2.get(i).width, bullets2.get(i).height);
        }
        
        // Enemy Draw Routines
        g.setColor(Color.LIGHT_GRAY); // sets enemy color
        g.fillRect(enemyRect1.x, enemyRect1.y, enemyRect1.width, enemyRect1.height); // Draws enemy 1s box
        g.fillRect(enemyRect2.x, enemyRect2.y, enemyRect2.width, enemyRect2.height); // Draws enemy 2s box
        g.fillRect(enemyRect3.x, enemyRect3.y, enemyRect3.width, enemyRect3.height); // Draws enemy 3s box
        
        // Draws the Player on the screen
        g.drawImage(ship, playerRect.x, playerRect.y, playerRect.width, playerRect.height, null); // Draws the player image

        if (updateClock.instant().compareTo(update) >= 0) { //updates clock cycle
            resetUpdateClock();
            update();
        }
        repaint(); // Repaints the screen
        
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
    
    public void playerMovement(){ // This method will handle movement speed
        
        if(left){ // This moves player left
           // System.out.println("Left");
            playerRect.x = playerRect.x - moveSpeed;
        }
        if(right){ // This moves player right
          //  System.out.println("Right");
            playerRect.x = playerRect.x + moveSpeed;
        
        }
        playerRect.x += playerX;

    }
    public void enemyEnterScreen(){
        
    }
    public void enemyLeaveScreen(){
        
    }
    public void enemyShooting(){
        
    }    
    public void enemyKilled(){
        
    }
    public void enemy(){ // This will handle all the enemy structure
        if(!enemyOnField){
            enemyEnterScreen();
        }else{
            if (!enemyHit){
                enemyShooting();
                if (enemyHit){
                    enemyKilled();
                }
            }
        }
       
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
                   // System.out.println("A Released");
                    break;
                case KeyEvent.VK_D:
                    right = false;
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
              //  System.out.println("A Pressed");
                break;
            case KeyEvent.VK_D:
                right = true;
               // System.out.println("D Pressed");
                break;
            case KeyEvent.VK_ESCAPE: // Emergency Esc from program
                System.exit(0);
        }
    }
    
}
