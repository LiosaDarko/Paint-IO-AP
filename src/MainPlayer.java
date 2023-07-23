import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPlayer extends Player {
       private int key;

    public MainPlayer(int height, int width, Color color, String name) {
                      super(height, width, color);
                      this.name = name;
    }

    public void setKey(int key) {
           this.key = key;
    }

    @Override
    public void move(){
           x += dx;
           y += dy;
    }

    public void dir() {
         if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && dx != 1) {
            dx = -1;
            dy = 0;
        }
        if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && dx != -1) {
            dx = 1;
            dy = 0;
        }
        if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && dy != 1) {
            dx = 0;
            dy = -1;
        }
        if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && dy != -1) {
            dx = 0;
            dy = 1;
        }
    }
}