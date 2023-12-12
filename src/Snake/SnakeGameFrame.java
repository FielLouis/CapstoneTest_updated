package Snake;

import javax.swing.*;

public class SnakeGameFrame extends JFrame {
    JFrame parentFrame;
    public SnakeGameFrame(JFrame frame) {
        parentFrame = frame;
        this.add(new SnakeGamePanel(this, frame));
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
