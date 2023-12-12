package Minesweeper;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MinesweeperFrame extends JFrame {
    JFrame parentFrame;
    private JLabel statusbar;

    public MinesweeperFrame(JFrame frame) {
        parentFrame = frame;
        userInt();
        this.setVisible(true);
    }

    private void userInt() {
        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        statusbar.setFont(new Font("Arial",Font.BOLD, 30));

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new MinesweeperGame(statusbar, this, parentFrame));

        setResizable(false);
        pack();
    }
}
