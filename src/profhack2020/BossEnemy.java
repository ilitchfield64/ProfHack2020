/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profhack2020;

/**
 *
 * @author Ian
 */
public class BossEnemy extends Enemy{
    
    public BossEnemy(int xPos, int yPos, int height, int width,String name,int health) {
        super(xPos, yPos, height, width);
        hitPoints = health;
    }
    
}
