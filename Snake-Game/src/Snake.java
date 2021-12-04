import java.util.Vector;

public class Snake {
    Coord head = new Coord(10, 10);
    Vector<Coord> tail = new Vector<Coord>();

    int xDir;
    int yDir;

    Snake () {
        tail.add(new Coord(9, 10));

        xDir = 1;
        yDir = 0;
    }

    public void move () {
        Coord prevPos = new Coord(head.x, head.y);
        head.x += xDir;
        head.y += yDir;

        for (int i = 0; i < tail.size(); i++) {
            tail.set(i, prevPos);
        }
    }
}
