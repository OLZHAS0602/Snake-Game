import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class SnakeGame {

    public static void main (String[] args) {
        Window window = new Window();

        while (true) {
            window.draw();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
