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
public class Sprite {
    protected Rectangle sprite;
    protected final Rectangle defaultSprite;
    protected int pointsOnHit;
    protected int hitPoints;
    protected boolean onScreen = false;
    protected int spriteID = 0;
      
    public Sprite(int xPos,int yPos, int height, int width ){
        sprite = new Rectangle(xPos, yPos, height, width);
        defaultSprite = sprite;
        ++spriteID;
    }
    public void moveLeft(int moveSpeed){
        sprite.x -= moveSpeed;
    }
    public void moveRight(int moveSpeed){
        sprite.x += moveSpeed;
    }
    public void moveUp(int moveSpeed){
        sprite.y -= moveSpeed;
    }
    public void moveDown(int moveSpeed){
        sprite.y -= moveSpeed;
    }
    public int getPoint(){
        return pointsOnHit;
    } 
    public void setOnScreen(boolean set){
        onScreen = set;
    }     
    public boolean getOnScreen(){
        return onScreen;
    }
   
}