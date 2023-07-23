import java.awt.*;
import java.util.ArrayList;

abstract class Player implements Comparable<Player> {
         protected int x, y, dx, dy, height, width;
         protected String name;
         private Boolean alive = true;
         private Tile presentTile;
         private final Color color;
         private final ArrayList<Tile> possessedTiles = new ArrayList<>();
         private final ArrayList<Tile> contestedTiles = new ArrayList<>();

    public int getX(){
           return x;
    }

    public int getY(){
           return y;
    }

    public Color getColor(){
           return color;
    }

    public int getDx() {
           return dx;
    }
    public int getDy() {
           return dy;
    }

    public String getName() {
           return name;
    }

    public Boolean getAlive() {
           return alive;
    }

    public void setPossessedTiles(Tile tile){
           possessedTiles.add(tile);
           tile.setOwner(this);
           tile.setContestedOwner(null);
    }

    public void removePossessedTiles(Tile tile){
           possessedTiles.remove(tile);
    }

    public ArrayList<Tile> getPossessedTiles(){
           return possessedTiles;
    }

    public double getScore(){
           return getPossessedTiles().size();
    }

    public void setContestedTiles(Tile tile){
           contestedTiles.add(tile);
           tile.setContestedOwner(this);
    }

    public ArrayList<Tile> getContestedTiles(){
           return contestedTiles;
    }

    void contestToPossess(){
         for(Tile tile : contestedTiles) {
             setPossessedTiles(tile);
        }
        contestedTiles.clear();
    }

    public void checkHit(Tile tile){
           if(tile.getContestedOwner() != null) {
              tile.getContestedOwner().die();
           }
    }

    public void setPresentTile(Tile presentTile) {
           this.presentTile = presentTile;
    }

    public void setAlive(Boolean alive) {
           this.alive = alive;
    }

    public int compareTo(Player player){
           return Integer.compare(player.getPossessedTiles().size(), possessedTiles.size());
    }

    public Tile getPresentTile() {
           return presentTile;
    }

    abstract void move();

    Player(int height, int width, Color color){
           x = (int)(Math.random() * (width - 2) + 1);
           y = (int)(Math.random() * (height - 2) + 1);

        if(x < 5){
           x += 5;
        }
        else if(x > (width-5)){
                x -= 5;
        }
        if(y < 5){
           y += 5;
        }
        else if(y > (height)-5){
                y -= 5;
        }
        this.color = color;
        this.height = height;
        this.width = width;

        double rand = Math.random();
            if(rand < 0.25) {
               dx = 1;
               dy = 0;
           }
           else if(rand < 5) {
                   dx = -1;
                   dy = 0;
           }
           else if (rand < 0.75) {
                    dx = 0;
                    dy = 1;
           }
           else {
                dx = 0;
                dy = -1;
           }
    }

    public void die() {
           alive = false;
           ArrayList<Tile> possessedTilesCopy = (ArrayList<Tile>)possessedTiles.clone();
           ArrayList<Tile> contestedTilesCopy = (ArrayList<Tile>)contestedTiles.clone();

        for(Tile value : possessedTilesCopy) {
            value.setOwner(null);
            }

        for(Tile tile : contestedTilesCopy) {
            tile.setContestedOwner(null);
            }
                 possessedTiles.clear();
                 contestedTiles.clear();
                 presentTile = null;
    }
}