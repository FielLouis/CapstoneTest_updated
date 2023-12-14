package Tetris;

import Sound.SFX;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TetrisGamePanel extends JPanel implements Runnable{
    static JFrame parentFrame1;
    static JFrame parentFrame2;
    private BufferedImage bg_image;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    static boolean musicPlayed = false;

    Thread gameThread;
    TetrisPlayManager pm;

    public TetrisGamePanel(JFrame frame1, JFrame frame2){
        parentFrame1 = frame1;
        parentFrame2 = frame2;

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        this.addKeyListener(new TetrisKeyHandler());
        this.setFocusable(true);

        pm = new TetrisPlayManager();

        try {
            setBackgroundImage("img/tetris_BG_neon.jpg");
        } catch (Exception e){
            System.out.println("No such file is present");
        }

    }


    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void run() {
        int ctr = 0;
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Timer variables
        long lastSecondTime = System.currentTimeMillis();
        int secondsPassed = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;

                long now = System.currentTimeMillis();
                if (now - lastSecondTime >= 1000) {
                    lastSecondTime = now;
                    secondsPassed++;

                    if(ctr % 83 == 0 && !musicPlayed){
                        SFX.play(new File("music/tetris_theme.wav"), 0.5f);
                        musicPlayed = true;
                    }

                    ctr++;
                }
            }
        }
    }

    private void update(){
        if(!TetrisKeyHandler.pausePressed){
            pm.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(bg_image, 0,0,1280,720,null);
        pm.draw(g2);
    }

    private void setBackgroundImage(String path_image) throws IOException{
        try{
            bg_image = ImageIO.read(new File(path_image));
        } catch (IOException e){
            throw new IOException("Failed to set bg image");
        }
    }
}
