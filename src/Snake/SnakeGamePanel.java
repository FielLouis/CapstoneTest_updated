package Snake;

import Sound.SFX;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener {
    JFrame parentFrame1;
    JFrame parentFrame2;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = SCREEN_WIDTH;
    static final int UNIT_SIZE = 25;
    int ROWS = SCREEN_HEIGHT / UNIT_SIZE;
    int COLUMNS = ROWS;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    boolean adjust;
    Timer timer;
    Random random;
    File snakeBGMusic = new File("music/snake_music_loop.wav");
    Image gameOverIMG = new ImageIcon("img/gameOver-cat.png").getImage();

    public SnakeGamePanel(JFrame frame1, JFrame frame2) {
        parentFrame1 = frame1;
        parentFrame2 = frame2;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        try {
            SFX.loop(snakeBGMusic, 5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        adjust = true;
        bodyParts = 3;
        applesEaten = 0;
        direction = 'R';
        running = true;
        newApple();

        // Reset snake position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if(running) {
            //drawing background
            for(int i = 0; i < ROWS; i++) {
                for(int j = 0; j < COLUMNS; j++) {
                    if((i + j) % 2 == 0) {
                        g.setColor(new Color(170, 215, 81));
                    } else {
                        g.setColor(new Color(148, 191, 67));
                    }
                    g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
            }

            //drawing the newly generated apple to the screen
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(85, 83, 224));
                } else {
                    //setting all body parts' color into different shade of green
                    g.setColor(new Color(57, 56, 156));

                    //setting all body parts' color into random
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                }
                g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 10, 10);

                //displaying score
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics metrics = getFontMetrics(g.getFont());
                if (y[0] <= (SCREEN_HEIGHT / 2) - (10 * UNIT_SIZE)) {
                    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, SCREEN_HEIGHT - 5);
                } else {
                    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
                }

            }
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        //getting new coordinates for the apple
        start:
        while(true) {
            appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

            //making sure the apple does not appear within the snake's already existing body
            for(int i = bodyParts; i > 0; i--) {
                if(appleX == x[i] && appleY == y[i]) {
                    continue start;
                }
            }

            break;
        }
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {

            //sets adjust to true again to enable readjusting the delay(speed of snake)
            //only when (applesEaten * 5 ) + 1
            if(applesEaten % 5 == 0 && !adjust) {
                adjust = true;
            }

            bodyParts++;
            applesEaten++;
            newApple();

            try {
                SFX.play(new File("music/snake_chomp.wav"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkCollisions() {
        //checking if head touches left border
        if(x[0] < 0) {
            running = false;
        }

        //checking if head touches right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }

        //checking if head touches top border
        if(y[0] < 0) {
            running = false;
        }

        //checking if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        //checking if heads collides with body
        for(int i = bodyParts; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if(!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        SFX.stopLoopingSound();

        //play game over sound
        SFX.playGameOver();

        //game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics1.stringWidth("GAME OVER")) / 2, (SCREEN_HEIGHT / 2) - 100);

        //displays final score
        g.setColor(Color.WHITE);
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, (SCREEN_HEIGHT / 2) + 75);

        //display retry
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press [ENTER] to retry", (SCREEN_WIDTH - metrics3.stringWidth("Press [ENTER] to retry")) / 2, (SCREEN_HEIGHT / 2) + 125);

        //back to menu
        FontMetrics metrics4 = getFontMetrics(g.getFont());
        g.drawString("Press [BACKSPACE] to return to Menu", (SCREEN_WIDTH - metrics4.stringWidth("Press [BACKSPACE] to return to Menu")) / 2, (SCREEN_HEIGHT / 2) + 180);

        //display game over picture
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(gameOverIMG, (SCREEN_WIDTH / 2) - 50, (SCREEN_HEIGHT / 2) - 85, 100, 100, null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();

            // Increase speed every time applesEaten is divisible by 5
            if (applesEaten > 0 && applesEaten % 5 == 0 && adjust) {
                int currentDelay = timer.getDelay();
                if (currentDelay > 10) {
                    timer.setDelay(currentDelay - 5);
                }
                //sets adjust to false in order to avoid decrementing infinitely when applesEaten reaches 5
                adjust = false;
            }
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!running) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Restarts the game
                    startGame();
                }
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    parentFrame1.dispose();
                    parentFrame2.setVisible(true);
                }
            } else {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        if (direction != 'R') {
                            direction = 'L';
                        }
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        if (direction != 'L') {
                            direction = 'R';
                        }
                    }
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        if (direction != 'D') {
                            direction = 'U';
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        if (direction != 'U') {
                            direction = 'D';
                        }
                    }

                }
            }
        }
    }

}
