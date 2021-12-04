import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_SIZE = 600;
    static final int GRID_COUNT = 20;
    static final int CELL_SIZE = SCREEN_SIZE / GRID_COUNT;

    int deltaTime;
    Random random;
    Timer timer;

    int[][] grid;
    Coord snakeHead;
    Vector<Coord> tail;
    Coord direction;
    Coord applePos;
    int score;

    boolean isGameOver;
    boolean moved;

    GamePanel () {
        deltaTime = 170;
        random = new Random();

        grid = new int[GRID_COUNT][GRID_COUNT];
        snakeHead = new Coord(GRID_COUNT / 2, GRID_COUNT / 2);
        tail = new Vector<Coord>();
        tail.add(new Coord(GRID_COUNT / 2 - 1, GRID_COUNT / 2));
        grid[GRID_COUNT / 2][GRID_COUNT / 2] = 1;
        grid[GRID_COUNT / 2][GRID_COUNT / 2 -1] = 1;

        direction = new Coord(1, 0);
        applePos = new Coord();
        score = 0;

        this.setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(new SnakeKeyAdapter());

        isGameOver = false;
        startGame();
    }

    public void startGame () {
        setApplePos();
        timer = new Timer(deltaTime, this);
        timer.start();
    }

    public void setApplePos () {

        while (grid[applePos.y][applePos.x] != 0) {
            applePos.x = random.nextInt(0, GRID_COUNT);
            applePos.y = random.nextInt(0, GRID_COUNT);
        }

        grid[applePos.y][applePos.x] = 2;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g, int transparency) {
        moved = false;
        g.setColor(new Color(0, 255, 0, transparency));
        g.fillOval(applePos.x * CELL_SIZE, applePos.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(new Color(255, 0, 0, transparency));
        g.fillRect(snakeHead.x * CELL_SIZE, snakeHead.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        for (int i = 0; i < tail.size(); i++) {
            g.setColor(new Color(0, 0, 0, transparency));
            g.fillRect(tail.get(i).x * CELL_SIZE, tail.get(i).y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("SCORE: " + score, (SCREEN_SIZE / 2) - (fontMetrics.stringWidth("SCORE: " + score) / 2), SCREEN_SIZE - g.getFont().getSize());
        moved = false;
    }

    public void draw (Graphics g) {
        if (!isGameOver) {
            draw (g, 255);
        }
        else {
            draw(g, 100);
            g.setColor(Color.yellow);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("GAME OVER!", (SCREEN_SIZE / 2) - (metrics.stringWidth("GAME OVER!") / 2), SCREEN_SIZE / 2);
        }
    }

    public void move () {
        boolean ate = false;

        Coord prevPos = new Coord(snakeHead.x, snakeHead.y);

        snakeHead.x += direction.x;
        snakeHead.y += direction.y;

        if (snakeHead.x < 0) snakeHead.x = GRID_COUNT - 1;
        if (snakeHead.x >= GRID_COUNT) snakeHead.x = 0;
        if (snakeHead.y < 0) snakeHead.y = GRID_COUNT - 1;
        if (snakeHead.y >= GRID_COUNT) snakeHead.y = 0;

        if (grid[snakeHead.y][snakeHead.x] == 1) {
            snakeHead = prevPos;
            isGameOver = true;
            return;
        }

        grid[snakeHead.y][snakeHead.x] = 1;

        if (snakeHead.x == applePos.x && snakeHead.y == applePos.y) {
            score++;
            deltaTime -= 3;
            timer.setDelay(deltaTime);
            ate = true;
            setApplePos();
        }

        for (int i = 0; i < tail.size(); i++) {
            Coord temp = new Coord(tail.get(i).x, tail.get(i).y);
            tail.set(i, new Coord(prevPos.x, prevPos.y));
            prevPos = new Coord(temp.x, temp.y);
            grid[tail.get(i).y][tail.get(i).x] = 1;
        }

        if (ate) {
            tail.add(new Coord(prevPos.x, prevPos.y));
            grid[prevPos.y][prevPos.x] = 1;
        }
        else {
            grid[prevPos.y][prevPos.x] = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();
        }

        repaint();
    }

    public class SnakeKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (moved) return;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_W: case KeyEvent.VK_UP:
                    if (direction.y == 0) {
                        moved = true;
                        direction.y = -1;
                        direction.x = 0;
                    }
                    break;
                case KeyEvent.VK_A: case KeyEvent.VK_LEFT:
                    if (direction.x == 0) {
                        moved = true;
                        direction.x = -1;
                        direction.y = 0;
                    }
                    break;
                case KeyEvent.VK_S: case KeyEvent.VK_DOWN:
                    if (direction.y == 0) {
                        moved = true;
                        direction.y = 1;
                        direction.x = 0;
                    }
                    break;
                case KeyEvent.VK_D: case KeyEvent.VK_RIGHT:
                    if (direction.x == 0) {
                        moved = true;
                        direction.x = 1;
                        direction.y = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
