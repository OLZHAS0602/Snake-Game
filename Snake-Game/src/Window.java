import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Window {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;

    static final int SIZE_GRID = 20;

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel scorePanel = new JPanel();
    JLabel scoreText = new JLabel();
    JPanel grid = new JPanel();
    JPanel[][] cells = new JPanel[SIZE_GRID][SIZE_GRID];

    Snake snake = new Snake();

    Window () {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        scoreText.setBackground(Color.white);
        scoreText.setForeground(Color.black);
        scoreText.setFont(new Font("Arial", Font.BOLD, 20));
        scoreText.setHorizontalAlignment(JLabel.CENTER);
        scoreText.setText("Score : 0");
        scoreText.setOpaque(true);

        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBackground(new Color(0, 0, 0, 0));
        scorePanel.setBounds(0, 0, 800, 100);

        grid.setLayout(new GridLayout(SIZE_GRID, SIZE_GRID));
        grid.setBackground(Color.white);

        for (int i = 0; i < SIZE_GRID; i++) {
            for (int j = 0; j < SIZE_GRID; j++) {
                cells[i][j] = new JPanel();
                cells[i][j].setSize(SCREEN_WIDTH / SIZE_GRID, SCREEN_HEIGHT / SIZE_GRID);
                cells[i][j].setBackground(Color.white);
                grid.add(cells[i][j]);
            }
        }

        scorePanel.add(scoreText);
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(grid);
    }

    public void clear () {
        for (int i = 0; i < SIZE_GRID; i++) {
            for (int j = 0; j < SIZE_GRID; j++) {
                cells[i][j].setBackground(Color.white);
            }
        }
    }

    public void draw () {
        clear();

        cells[snake.head.y][snake.head.x].setBackground(Color.black);

        for (int i = 0; i < snake.tail.size(); i++) {
            cells[snake.tail.get(i).y][snake.tail.get(i).x].setBackground(Color.black);
        }

        snake.move();
    }

}
