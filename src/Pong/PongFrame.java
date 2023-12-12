package Pong;

import javax.swing.*;

public class PongFrame extends JFrame{
    JFrame parentFrame;
    public PongFrame(JFrame frame) {
        parentFrame = frame;
        PongGameplay gamePlay = new PongGameplay(this, parentFrame);

        this.setBounds(10, 10, 700, 600);
        this.setTitle("Breakout Ball");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePlay);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
