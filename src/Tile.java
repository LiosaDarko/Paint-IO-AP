import java.awt.*;

public class Tile {
       private final int x, y;
       private final Color color = Color.white;
       private Player owner;
       private Player contestedOwner;

    public Tile(int x, int y){
                this.x = x;
                this.y = y;
    }
    public int getX() {
           return x;
    }

    public int getY() {
           return y;
    }

    public Player getContestedOwner() {
           return contestedOwner;
    }

    public void setContestedOwner(Player contestedOwner) {
           this.contestedOwner = contestedOwner;
    }

    public Player getOwner() {
           return owner;
    }

    public void setOwner(Player owner) {
           if(this.owner != null){
              this.owner.removePossessedTiles(this);
           }
              this.owner = owner;
    }

    Color getColor(){
          if(owner != null && contestedOwner == null) {
                   return owner.getColor().darker();
          }
          else if(owner == null && contestedOwner != null) {
                  return(new Color(contestedOwner.getColor().getRed(), contestedOwner.getColor().getGreen(), contestedOwner.getColor().getBlue(), 80));
          }
          else if(owner != null && contestedOwner != owner){
                  return blendColors();
          }
          else{
                  return color;
          }
    }

    private Color blendColors(){
            float blendedRed = ((owner.getColor().getRed() / 255f) * (contestedOwner.getColor().getRed() / 255f));
            float blendedGreen = ((owner.getColor().getGreen() / 255f) * (contestedOwner.getColor().getGreen() / 255f));
            float blendedBlue = ((owner.getColor().getBlue() / 255f) * (contestedOwner.getColor().getBlue() / 255f));

            return(new Color(((blendedRed + 1 ) / 2), ((blendedGreen + 1) / 2), ((blendedBlue + 1 ) / 2)));
    }
}