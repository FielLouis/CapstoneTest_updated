package Sound;

import java.io.File;
import javax.sound.sampled.*;

public class SFX {
    public static Clip clip;
    static boolean isLooping = false;
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
            clip = AudioSystem.getClip();
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
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLoop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }


    public static void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            isLooping = false;
        }
    }


}