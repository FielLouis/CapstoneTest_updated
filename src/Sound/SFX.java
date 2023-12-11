package Sound;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SFX {
    public static void play(File sndfile) {
        play(sndfile,1f);
    }
    public static void loop(File sndfile) {
        loop(sndfile, 1f);
    }
    public static void playGameOver(){
        play(new File("music/game-overHUH.wav"));
    }
    public static void play(File sndfile, float volume){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sndfile);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loop(File sndfile, float volume) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sndfile);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.loop(255);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}