package Tetris;

import Sound.SFX;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class TetrisKeyHandler implements KeyListener {
    public static boolean upPressed,downPressed, leftPressed, rightPressed,pausePressed,spacePressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(TetrisPlayManager.gameOver && code == KeyEvent.VK_BACK_SPACE) {
            TetrisGamePanel.parentFrame1.dispose();
            TetrisGamePanel.parentFrame2.setVisible(true);
        }

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            SFX.play(new File("music/rotation.wav"));
            upPressed = true;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            if(pausePressed){
                pausePressed = false;
            }else{
                pausePressed = true;
            }
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
