package Pong;

import Sound.SFX;

import java.io.File;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import javax.swing.Timer;

public class PongGameplay extends JPanel implements KeyListener, ActionListener
{
    JFrame parentFrame1;
    JFrame parentFrame2;
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 600;
    private boolean play = true;
    private int score;
    private int scoreContinue;
    private int totalBricks = 48;
    private final Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private PongMapGenerator map;
    static boolean gameWon;
    private final ImageIcon backgroundImage;
    public PongGameplay(JFrame frame1, JFrame frame2) {
        parentFrame1 = frame1;
        parentFrame2 = frame2;
        map = new PongMapGenerator(4, 12);
        backgroundImage = new ImageIcon("img/bb_bg.jpg");
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g) {
        if(play) {
            // background
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

            // drawing map
            map.draw((Graphics2D) g);

            // borders
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 3, 592);
            g.fillRect(0, 0, 692, 3);
            g.fillRect(691, 0, 3, 592);

            // the scores
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            if(gameWon) {
                score = scoreContinue;
                gameWon = false;
            }
            g.drawString("" + score, 590, 30);

            // the paddle
            g.setColor(Color.green);
            g.fillRect(playerX, 550, 100, 8);

            // the ball
            g.setColor(Color.yellow);
            g.fillOval(ballposX, ballposY, 20, 20);

            g.setColor(Color.WHITE);
            g.setFont(new Font("DialogInput", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            // when you won the game
            if (totalBricks <= 0) {
                play = false;
                g.drawString("You Won", (SCREEN_WIDTH - metrics.stringWidth("You Won")) / 2, 300);

                g.setFont(new Font("Arial", Font.PLAIN, 15));
                FontMetrics metrics1 = getFontMetrics(g.getFont());
                g.drawString("Press (C) to Continue", (SCREEN_WIDTH - metrics1.stringWidth("Press (C) to Continue")) / 2, 360);
                gameWon = true;
                gameOver(g);
            }

            // when ball goes out of bounds
            if(ballposY > 570) {
                SFX.playGameOver();

                play = false;
                g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, 300);

                g.setFont(new Font("Arial", Font.BOLD, 30));
                metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,350);
                gameOver(g);
            }
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        ballXdir = 0;
        ballYdir = 0;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        FontMetrics metrics = getFontMetrics(g.getFont());

        g.drawString("Press (ENTER) to Restart", (SCREEN_WIDTH - metrics.stringWidth("Press (ENTER) to Restart")) / 2,380);
        g.drawString("Press (BACKSPACE) to Return to Menu", (SCREEN_WIDTH - metrics.stringWidth("Press (BACKSPACE) to Return to Menu")) / 2, 400);
    }

    public void keyPressed(KeyEvent e)
    {
        if(!play) {
            if (e.getKeyCode() == KeyEvent.VK_C && gameWon) {
                Random random = new Random();

                int row = random.nextInt(3) + 3; // 3 to 6 rows
                int col = random.nextInt(5) + 5; // 5 to 10 columns

                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                scoreContinue = score;
                totalBricks = row * col;
                map = new PongMapGenerator(row, col);

                repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Random random = new Random();

                int row = random.nextInt(3) + 3; // 3 to 6 rows
                int col = random.nextInt(5) + 5; // 5 to 10 columns

                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = row * col;
                map = new PongMapGenerator(row, col);

                repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if(gameWon) {
                    gameWon = false;

                    //check if apil bas hi score
                }
                parentFrame1.dispose();
                parentFrame2.setVisible(true);
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (playerX >= 600) {
                    playerX = 600;
                } else {
                    moveRight();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                ballposY = 575;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                totalBricks = 0;
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void moveRight()
    {
        play = true;
        playerX += 20;
    }

    public void moveLeft()
    {
        play = true;
        playerX -= 20;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(play)
        {
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = -2;
                SFX.play(new File("music/bb_paddle.wav"));
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
                SFX.play(new File("music/bb_paddle.wav"));
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
            {
                ballYdir = -ballYdir;
                SFX.play(new File("music/bb_paddle.wav"));
            }

            // check map collision with the ball
            A:
            for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        //scores++;
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            score += 5;
                            totalBricks--;

                            SFX.play(new File("music/bb_brick.wav"));

                            // when ball hit right or left of brick
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            // when ball hits top or bottom of brick
                            else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX < 0) {
                ballXdir = -ballXdir;
                SFX.play(new File("music/bb_wall.wav"));
            }
            if(ballposY < 0) {
                ballYdir = -ballYdir;
                SFX.play(new File("music/bb_wall.wav"));
            }
            if(ballposX > 670) {
                ballXdir = -ballXdir;
                SFX.play(new File("music/bb_wall.wav"));
            }
        }
        repaint();
    }
}

