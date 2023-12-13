package Tetris;

import javax.swing.*;

public class TetrisGameFrame extends JFrame {
    JFrame parentFrame;
    public TetrisGameFrame(JFrame frame) {
        parentFrame = frame;

        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        TetrisGamePanel gp = new TetrisGamePanel(this, parentFrame);
        this.add(gp);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        gp.launchGame();
    }

    public static void main(String[] args) {
        new TetrisGameFrame(null);
    }
}
