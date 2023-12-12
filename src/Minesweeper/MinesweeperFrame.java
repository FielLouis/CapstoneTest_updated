package Minesweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MinesweeperFrame extends JFrame {
    JFrame parentFrame;
    private JLabel statusbar;

    public MinesweeperFrame(JFrame frame) {
        parentFrame = frame;
        userInt();
        this.setVisible(true);

        JButton restartButton = new JButton("Restart Game");
        setupRestartButton(restartButton);

        this.add(Box.createVerticalStrut(5));
        this.add(restartButton);

        JButton backButton = new JButton("Return to Menu");
        setupReturnButton(backButton);

        this.add(Box.createVerticalStrut(5));
        this.add(backButton);

        getContentPane().add(this);
        pack();
    }

    private void userInt() {
        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        statusbar.setFont(new Font("serif",Font.BOLD, 15));

        add(new MinesweeperGame(statusbar, parentFrame, this));

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void restartButtonActionPerformed(ActionEvent e) {

    }

    private void returnButtonActionPerformed(ActionEvent e)  {
        this.dispose();
        parentFrame.setVisible(true);
    }

    private void setupRestartButton(JButton restartButton) {
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setPreferredSize(new Dimension(150, 50));
        restartButton.setBackground(new Color(55, 227, 55));
        restartButton.setForeground(Color.black);
        restartButton.setFont(new Font("Calibre", Font.BOLD, 25));
        restartButton.addActionListener(this::restartButtonActionPerformed);
    }

    private void setupReturnButton(JButton backButton) {
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setBackground(new Color(55, 227, 55));
        backButton.setForeground(Color.black);
        backButton.setFont(new Font("Calibre", Font.BOLD, 25));
        backButton.addActionListener(this::returnButtonActionPerformed);
    }
}
