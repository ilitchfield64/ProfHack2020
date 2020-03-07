/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profhack2020;

import java.awt.Rectangle;

/**
 *
 * @author Ian
 */
public class Player extends Sprite{
    
    public Player(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width);
        hitPoints= 100;
    }
        public void Boarders(Rectangle sprite, int moveSpeed, final int SCREEN_WIDTH) {

        if (sprite.x <= 0) {
            sprite.x += moveSpeed;
        }
        if (sprite.x >= (SCREEN_WIDTH - (sprite.width - 1))) {
            sprite.x -= moveSpeed;
        }
    }
}
