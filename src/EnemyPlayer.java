import java.awt.*;
import java.util.*;

public class EnemyPlayer extends Player{

    public EnemyPlayer(int height, int width, Color color){
                       super(height, width, color);
                       String[] enemyNames = {"Enemy.1", "Enemy.2", "Enemy.3", "Enemy.4", "Enemy.5", "Enemy.6", "Enemy.7", "Enemy.8", "Enemy.9", "Enemy.10",
                       "Enemy.11", "Enemy.12", "Enemy.13", "Enemy.14", "Enemy.15", "Enemy.16", "Enemy.17", "Enemy.18", "Enemy.19", "Enemy.20", "Enemy.21",
                       "Enemy.22", "Enemy.23", "Enemy.24", "Enemy.25", "Enemy.26", "Enemy.27", "Enemy.28", "Enemy.29", "Enemy.30"};
                       this.name = enemyNames[new Random().nextInt(enemyNames.length)];
    }

    @Override
    public void move() {
           x += dx;
           y += dy;
        double rand = Math.random();
            if(rand < 0.05 && dx != -1) {
               dx = 1;
               dy = 0;
            }
            else if(rand < 0.1 && dx != 1) {
                    dx = -1;
                    dy = 0;
            }
            else if(rand < 0.15 && dy != -1) {
                    dx = 0;
                    dy = 1;
            }
            else if(rand < 0.2 && dy != 1) {
                    dx = 0;
                    dy = -1;
            }
        avoidForbiddenArea();
    }

    private void avoidForbiddenArea() {
        if(x == 0 && y == height-1){
           if(dx == -1){
              dx = 0;
              dy = -1;
           }
           else {
                dx = 1;
                dy = 0;
           }
        }
        else if(x == width-1 && y == 0){
             if(dx == 1){
                dx = 0;
                dy = 1;
             }
             else {
                  dx = -1;
                  dy = 0;
             }
        }
        else if(x == width-1 && y == height-1){
             if(dx == 1){
                dx = 0;
                dy = -1;
            }
             else {
                  dx = -1;
                  dy = 0;
            }
        }
        else if(x == 0 && y == 0){
             if(dx == -1){
                dx = 0;
                dy = 1;
            }
             else {
                  dx = 1;
                  dy = 0;
            }
        }
        else if(x == 0 && dx == -1){
                dx = 0;
                dy = 1;
        }
        else if(x == width-1 &&  dx == 1){
                dx = 0;
                dy = 1;
        }
        else if(y == 0 && dy == -1){
                dx = 1;
                dy = 0;
        }
        else if(y == height-1 && dy == 1){
                dx = 1;
                dy = 0;
        }
    }

    @Override
    public void die() {
           super.die();
           Timer timer = new Timer();
           timer.schedule(new TimerTask() {

               @Override
               public void run() {
                      setAlive(true);
            }
        },500);
    }
}