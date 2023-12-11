package Snake;

import javax.swing.*;

public class SnakeGameFrame extends JFrame {
    public SnakeGameFrame() {
        this.add(new SnakeGamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
