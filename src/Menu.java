import Minesweeper.MinesweeperFrame;
import Pong.PongFrame;
import Snake.*;
import TicTacToe.*;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private JPanel jpanel;
    private JButton btnSnake;
    private JButton btnTTT;
    private JButton btnMSW;
    private JButton btnTetris;
    private JLabel labelTitle;
    private JButton btnBrickBreak;

    public Menu () {
        this.setContentPane(jpanel);
        this.setSize(new Dimension(1200, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Y4 Games");
        this.setLocationRelativeTo(null);

        btnSnake.addActionListener(e -> {
            this.setVisible(false);
            new SnakeGameFrame(this);
        });

        btnMSW.addActionListener(e -> {
            this.setVisible(false);
            new MinesweeperFrame(this);
        });

        btnTTT.addActionListener((e -> {
            this.setVisible(false);
            new TicTacToeGame(this);
        }));

        btnBrickBreak.addActionListener((e -> {
            this.setVisible(false);
            new PongFrame(this);
        }));
    }
    public static void main(String[] args) {
        new Menu();
    }
}
