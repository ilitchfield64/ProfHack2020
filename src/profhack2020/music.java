/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profhack2020;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 *
 * @author Gavin
 */
public class music {
    void playMusic(String musicLocation){
        try{
            File musicPath = new File(musicLocation);
            
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                
                //JOptionPane.showConfirmDialog(null, "Hit ok to pause");
                //long clipTimePostion = clip.getMicrosecondPosition();
                //clip.stop();
                
                //JOptionPane.showMessageDialog(null, "Hit ok to resume");
                //clip.setMicrosecondPosition(clipTimePostion);
                //clip.start();
                
            }else{
                System.out.println("Can't find file");
            }
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
}
