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

    public Menu () {
        this.setContentPane(jpanel);
        this.setSize(new Dimension(600, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Y4 Games");
        this.setLocationRelativeTo(null);

        btnSnake.addActionListener(e -> {
            this.dispose();
            new SnakeGameFrame();
        });

        btnTTT.addActionListener((e -> {
            this.dispose();
            new TicTacToeGame().setVisible(true);
        }));
    }

    public static void main(String[] args) {
        new Menu();
    }
}
