package profhack2020;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author Ian Litchfield ~ilitchfield64
 */
public class MusicPlayer {
    private boolean playing = false;
    private Clip clip;
    private AudioInputStream audioInput;
    private URL url;

    public void setMusicLoop(String song){
        try {
        url = this.getClass().getResource(song); // This Object allows the files to be loaded from inside of a jar!!!!   
        audioInput = AudioSystem.getAudioInputStream(url);
        clip = AudioSystem.getClip(); 
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void playMusic(){
        try {
            if (!playing){
                
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                playing = true;
            } else {
                System.out.println("Music is already playing");
            }
            
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void stopMusic(){
        try {
            if (playing){
                clip.stop();
                clip.close();
                playing = false;
            } else {
                System.out.println("Music is already stopped");
            }
            
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}