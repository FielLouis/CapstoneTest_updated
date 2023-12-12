package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeGame extends JFrame {
    JFrame parentFrame;
    private JButton[][] buttons;
    private char currentPlayer;
    private int playerXScore;
    private int playerOScore;
    private JLabel scoreboardLabel;
    private char startingPlayer;

    public TicTacToeGame(JFrame frame) {
        parentFrame = frame;
        initComponents();
        initializeGame();
        this.setVisible(true);
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

        scoreboardLabel = new JLabel(" Scoreboard:  X: 0  O: 0 ");
        setupScoreboardLabel();

        mainPanel.add(scoreboardLabel);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        setupGamePanel(gamePanel);

        mainPanel.add(gamePanel);

        JButton restartButton = new JButton("Restart Game");
        setupRestartButton(restartButton);

        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(restartButton);

        JButton backButton = new JButton("Return to Menu");
        setupReturnButton(backButton);

        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeGame() {
        clearButtons();
        currentPlayer = 'X';
        updateTurnIndicator();
    }

    private void buttonActionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();

        if (selectedButton.getText().equals("")) {
            handleMove(selectedButton);
        }
    }

    private void handleMove(JButton button) {
        button.setText(String.valueOf(currentPlayer));
        updateButtonAppearance(button);

        JButton[][] board = cloneButtonArray(buttons);
        if (checkWin(board, currentPlayer)) {
            handleWin();
        } else if (checkDraw(board)) {
            handleDraw();
        } else {
            switchPlayer();
            updateTurnIndicator();

            if (currentPlayer == 'O') {
                makeComputerMove();
            }
        }
    }

    private void makeComputerMove() {
        Move bestMove = findBestMove(buttons);
        buttons[bestMove.row][bestMove.col].setText("O");
        updateButtonAppearance(buttons[bestMove.row][bestMove.col]);

        JButton[][] board = cloneButtonArray(buttons);
        if (checkWin(board, 'O')) {
            handleWin();
        } else if (checkDraw(board)) {
            handleDraw();
        } else {
            switchPlayer();
            updateTurnIndicator();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void updateTurnIndicator() {
        scoreboardLabel.setText("  Scoreboard:  X: " + playerXScore + "  O: " + playerOScore);
        scoreboardLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
        scoreboardLabel.setForeground(new Color(3, 26, 96));
    }

    private boolean checkDraw(JButton[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
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
        scoreboardLabel.setText(" Scoreboard:  X: " + playerXScore + "  O: " + playerOScore);
    }

    private void restartButtonActionPerformed(ActionEvent e) {
        initializeGame();
        playerXScore = 0;
        playerOScore = 0;
        updateScoreLabel();
        switchStartingPlayer();
    }

    private void returnButtonActionPerformed(ActionEvent e)  {
        this.dispose();
        parentFrame.setVisible(true);
    }

    private void switchStartingPlayer() {
        startingPlayer = (startingPlayer == 'X') ? 'O' : 'X';
        updateTurnIndicator();
    }

    private void showWinnerAlert(char winner) {
        JOptionPane.showMessageDialog(null, "Player " + winner + " wins!");
    }

    private void showDrawAlert() {
        JOptionPane.showMessageDialog(null, "It's a draw!");
    }

    private void setupScoreboardLabel() {
        scoreboardLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        scoreboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreboardLabel.setForeground(new Color(92, 206, 86));
    }

    private void setupGamePanel(JPanel gamePanel) {
        gamePanel.setPreferredSize(new Dimension(600, 600));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                configureButton(buttons[i][j]);
                buttons[i][j].addActionListener(this::buttonActionPerformed);
                gamePanel.add(buttons[i][j]);
            }
        }
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

    private void configureButton(JButton button) {
        button.setFont(new Font("Arial Black", Font.PLAIN, 90));
        button.setFocusPainted(false);
        button.setBackground(new Color(254, 253, 255));
        button.setForeground(Color.pink);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 4));
    }

    private void clearButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void updateButtonAppearance(JButton button) {
        button.setForeground(currentPlayer == 'X' ? new Color(1, 51, 159) : new Color(213, 2, 19));
    }

    private JButton[][] cloneButtonArray(JButton[][] source) {
        JButton[][] target = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                target[i][j] = new JButton(source[i][j].getText());
            }
        }
        return target;
    }

    private static class Move {
        int row;
        int col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Move findBestMove(JButton[][] board) {
        List<Move> availableMoves = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <

                    3; j++) {
                if (board[i][j].getText().equals("")) {
                    availableMoves.add(new Move(i, j));
                }
            }
        }

        if (Math.random() < 0.8) {
            return findBestMoveWithMinimax(board, availableMoves);
        } else {
            return getRandomMove(availableMoves);
        }
    }

    private Move findBestMoveWithMinimax(JButton[][] board, List<Move> availableMoves) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (Move move : availableMoves) {
            int i = move.row;
            int j = move.col;

            board[i][j].setText("O");
            int score = minimax(board, 0, false);
            board[i][j].setText("");

            if (score > bestScore) {
                bestScore = score;
                bestMove.row = i;
                bestMove.col = j;
            }
        }

        return bestMove;
    }

    private Move getRandomMove(List<Move> availableMoves) {
        return availableMoves.get((int) (Math.random() * availableMoves.size()));
    }

    private int minimax(JButton[][] board, int depth, boolean isMaximizing) {
        if (checkWin(board, 'O')) {
            return 1;
        } else if (checkWin(board, 'X')) {
            return -1;
        } else if (checkDraw(board)) {
            return 0;
        }

        if (isMaximizing) {
            return getMaxEvaluation(board);
        } else {
            return getMinEvaluation(board);
        }
    }

    private int getMaxEvaluation(JButton[][] board) {
        int maxEval = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    board[i][j].setText("O");
                    int depth = 0;
                    int eval = minimax(board, depth + 1, false);
                    board[i][j].setText("");
                    maxEval = Math.max(maxEval, eval);
                }
            }
        }
        return maxEval;
    }

    private int getMinEvaluation(JButton[][] board) {
        int minEval = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    board[i][j].setText("X");
                    int depth = 0;
                    int eval = minimax(board, depth + 1, true);
                    board[i][j].setText("");
                    minEval = Math.min(minEval, eval);
                }
            }
        }
        return minEval;
    }

    private boolean checkWin(JButton[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (checkRow(board, i, player) || checkColumn(board, i, player)) {
                return true;
            }
        }

        return checkDiagonals(board, player);
    }

    private boolean checkRow(JButton[][] board, int row, char player) {
        return board[row][0].getText().equals(String.valueOf(player))
                && board[row][1].getText().equals(String.valueOf(player))
                && board[row][2].getText().equals(String.valueOf(player));
    }

    private boolean checkColumn(JButton[][] board, int col, char player) {
        return board[0][col].getText().equals(String.valueOf(player))
                && board[1][col].getText().equals(String.valueOf(player))
                && board[2][col].getText().equals(String.valueOf(player));
    }

    private boolean checkDiagonals(JButton[][] board, char player) {
        return (board[0][0].getText().equals(String.valueOf(player))
                && board[1][1].getText().equals(String.valueOf(player))
                && board[2][2].getText().equals(String.valueOf(player)))
                || (board[0][2].getText().equals(String.valueOf(player))
                && board[1][1].getText().equals(String.valueOf(player))
                && board[2][0].getText().equals(String.valueOf(player)));
    }

    private void handleWin() {
        updateScore();
        showWinnerAlert(currentPlayer);
        initializeGame();
    }

    private void handleDraw() {
        showDrawAlert();
        initializeGame();
    }
}
