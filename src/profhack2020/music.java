/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profhack2020;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 *
 * @author Gavin
 */
public class music {

    boolean playing = false;

    void playMusic(String musicLocation) {
        try {
            URL url = this.getClass().getResource(musicLocation); // This Object allows the files to be loaded from inside of a jar!!!!

            if (!playing) {

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                

                //JOptionPane.showConfirmDialog(null, "Hit ok to pause");
                //long clipTimePostion = clip.getMicrosecondPosition();
                //clip.stop();
                //JOptionPane.showMessageDialog(null, "Hit ok to resume");
                //clip.setMicrosecondPosition(clipTimePostion);
                //clip.start();
            } else {
                System.out.println("Can't find file");
            }

        } catch (Exception ex) {
          
        }
    }
}
