package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private int playerXScore;
    private int playerOScore;
    private JLabel scoreLabel;
    private JLabel scoreboardLabel;
    private char startingPlayer;

    public TicTacToeGame() {
        initComponents();
        initializeGame();
    }

    private void initComponents() {
        buttons = new JButton[3][3];
        currentPlayer = 'X';
        playerXScore = 0;
        playerOScore = 0;
        startingPlayer = 'X';

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        scoreboardLabel = new JLabel(" Scoreboard:  X: 0  O: 0");
        scoreboardLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        scoreboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreboardLabel.setForeground(new Color(128, 0, 140));

        mainPanel.add(scoreboardLabel);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        gamePanel.setPreferredSize(new Dimension(600, 600));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial Black", Font.PLAIN, 90));
                buttons[i][j].setFocusPainted(false);

                // Set background color
                buttons[i][j].setBackground(new Color(240, 224, 255)); // Example: Light gray

                // Set font color
                buttons[i][j].setForeground(Color.pink);

                // Set border color
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.darkGray, 4));

                buttons[i][j].addActionListener(this::buttonActionPerformed);
                gamePanel.add(buttons[i][j]);
            }
        }

        mainPanel.add(gamePanel);

        JButton restartButton = new JButton("Restart Game");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set preferred size to avoid extra whitespace
        restartButton.setPreferredSize(new Dimension(150, 50));

        // Set background color
        restartButton.setBackground(new Color(245, 164, 243));
        // Set font color
        restartButton.setForeground(Color.black);

        restartButton.setFont(new Font("Calibre", Font.BOLD, 25));

        restartButton.addActionListener(this::restartButtonActionPerformed);
        mainPanel.add(Box.createVerticalStrut(0)); // Add some vertical spacing
        mainPanel.add(restartButton);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = startingPlayer;
        updateTurnIndicator();
    }

    private void buttonActionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (clickedButton.getText().equals("")) {
            // Set different colors for 'X' and 'O'
            if (currentPlayer == 'X') {
                clickedButton.setText(String.valueOf(currentPlayer));
                clickedButton.setForeground(new Color(109, 1, 159));  // Set color for 'X'
            } else {
                clickedButton.setText(String.valueOf(currentPlayer));
                clickedButton.setForeground(new Color(248, 7, 234));  // Set color for 'O'
            }

            if (checkWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                updateScore();
                initializeGame();
                switchStartingPlayer();  // Switch starting player after each game
            } else if (checkDraw()) {
                JOptionPane.showMessageDialog(null, "It's a draw!");
                initializeGame();
                switchStartingPlayer();  // Switch starting player after each game
            } else {
                switchPlayer();
                updateTurnIndicator();
            }
        }
    }

    private void updateTurnIndicator() {
        scoreboardLabel.setText("  Scoreboard:  X: " + playerXScore + "  O: " + playerOScore + "   |   Player " + currentPlayer + "'s turn ");
        scoreboardLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
        scoreboardLabel.setForeground(new Color(140, 33, 140));
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        return buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer));
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateScore() {
        if (currentPlayer == 'X') {
            playerXScore++;
        } else {
            playerOScore++;
        }
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreboardLabel.setText(" Scoreboard:  X: " + playerXScore + "  O: " + playerOScore + "   |   Player " + currentPlayer + "'s turn");
    }

    private void restartButtonActionPerformed(ActionEvent e) {
        initializeGame();
        playerXScore = 0;
        playerOScore = 0;
        updateScoreLabel();
        switchStartingPlayer();  // Switch starting player after each game
    }

    private void switchStartingPlayer() {
        startingPlayer = (startingPlayer == 'X') ? 'O' : 'X';
    }
}