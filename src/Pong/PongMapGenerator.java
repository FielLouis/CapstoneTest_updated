package Pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class PongMapGenerator {
    public int[][] map;
    public int brickWidth;
    public int brickHeight;
    public int glowOffset;

    public PongMapGenerator (int row, int col) {
        map = new int[row][col];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
        glowOffset = 10;
    }

    public void draw(Graphics2D g) {
        int padding = 5;
        int cornerRadius = 10;
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0) {
                    // glow effect
                    g.setColor(new Color(255, 255, 255, 100)); // Adjust alpha value for transparency
                    g.fillRoundRect(j * brickWidth + 80 - glowOffset, i * brickHeight + 50 - glowOffset,
                            brickWidth + 2 * glowOffset, brickHeight + 2 * glowOffset, cornerRadius, cornerRadius);

                    // for brick lines
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col)
    {
        map[row][col] = value;
    }
}

