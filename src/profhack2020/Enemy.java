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
public class Enemy extends Sprite{
    
    public Enemy(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width);
        hitPoints = 1;
    }
    
}
