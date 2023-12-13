package Tetris;

import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import Mino.*;
import Sound.SFX;

public class TetrisPlayManager {
    boolean donePlayed;
    public final int WIDTH = 360;
    public final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bot_y;

    // Mino
    private Mino currentMino;
    private final int MINO_START_X;
    private final int MINO_START_Y;

    private Mino nextMino;
    public final int NEXTMINO_X;
    public final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60;

    static boolean gameOver;

    private boolean effectCounterOn;
    private int effectCounter;

    private ArrayList<Integer> effectY = new ArrayList<>();

    private int score;
    private int lines;

    private static final String SCORE_FILE_PATH = "src/Tetris/tetris_score.txt";

    public TetrisPlayManager() {
        donePlayed = false;

        left_x = (TetrisGamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bot_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        loadScore();

        score = 0;


        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }

    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0 -> mino = new Mino_L1();
            case 1 -> mino = new Mino_L2();
            case 2 -> mino = new Mino_Square();
            case 3 -> mino = new Mino_Bar();
            case 4 -> mino = new Mino_T();
            case 5 -> mino = new Mino_Z1();
            case 6 -> mino = new Mino_Z2();
        }
        return mino;
    }

    public void update() {
        if (gameOver) {
            return;
        }

        if (!currentMino.active) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                gameOver = true;
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            checkDelete();
            saveScore();
        } else {
            currentMino.update();
        }
    }


    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bot_y) {

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }
            x += Block.SIZE;

            if (x == right_x) {

                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }
                    lineCount++;
                    lines++;
                    if (score % 10 == 0 && dropInterval > 1) {
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
        if (lineCount > 0) {
            int singleLineScore = 5;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {
        // draw play area
        g2.setColor(Color.cyan);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // draw next mino frame
        int x = right_x + 100;
        int y = bot_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Courier Pro", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        // draw score frame
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("SCORE: " + score, x, y);

        if (currentMino != null) {
            currentMino.draw(g2);
        }
        // draw next mino
        nextMino.draw(g2);

        // draw static blocks
        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }
        // draw effect
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.RED);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }
            if (effectCounter == 2) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
                SFX.play(new File("music/tetris_clear.wav"));
            }
        }
        // draw pause

        g2.setFont(new Font("Courier Pro", Font.BOLD, 40));
        if (gameOver) {
            if(!donePlayed) {
                SFX.playGameOver();
                donePlayed = true;
            }

            g2.setColor(Color.red);
            x = left_x + 60;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        } else if (TetrisKeyHandler.pausePressed) {
            g2.setColor(Color.yellow);
            x = left_x + 30;
            y = top_y + 320;
            g2.drawString("GAME PAUSED", x, y);
        }

        x = 45;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Georgia",Font.BOLD,50));
        g2.drawString("y4.Games.com",x,y);
    }

    private void loadScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE_PATH))) {
            String scoreStr = reader.readLine();
            if (scoreStr != null) {
                score = Integer.parseInt(scoreStr);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void saveScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE_PATH))) {
            writer.write(Integer.toString(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
