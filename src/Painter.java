import java.awt.*;
import java.util.List;

public class Painter{
       private int width, height;
       private final int Size;
       private final List<Player> players;
       private final Player humanPlayer;
       private final GameBoard gb;
       private boolean paint = true;

    Painter(int Size, GameBoard gb, Player humanPlayer, List<Player> players){
            this.Size = Size;
            this.gb = gb;
            this.players = players;
            this.humanPlayer = humanPlayer;
    }
    public void setPaint(boolean paint) {
           this.paint = paint;
    }
    public void Paint(Graphics g){
           if(paint){
              height = g.getClipBounds().height;
              width = g.getClipBounds().width;
              paintGameArea(g);
              paintPlayers(g);
           }
    }

    private void paintPlayers(Graphics g){
            int paintX;
            int paintY;

        g.setFont(new Font("Courier Bold", Font.PLAIN, 12));
        FontMetrics fontMetrics = g.getFontMetrics();

        for(Player player : players) {
            paintX = (player.getX() - humanPlayer.getX()) * Size + ((width - Size) / 2);
            paintY = (player.getY() - humanPlayer.getY()) * Size + ((height - Size) / 2);
            if (player != humanPlayer) {
                paintX += ((player.getDx() - humanPlayer.getDx()) * Size * ((gb.getTickCounter() + 1) / (double) gb.getTickReset()));
                paintY += ((player.getDy() - humanPlayer.getDy()) * Size * ((gb.getTickCounter() + 1) / (double) gb.getTickReset()));
            }

            g.setColor(Color.BLACK);
            g.drawString(player.getName(), paintX + (Size - fontMetrics.stringWidth(player.getName()))/2, paintY+Size+16);

            if (!(paintX + Size < 0 || paintX > width || paintY + Size < 0 || paintY > height)) {
                g.setColor(player.getColor());
                g.fillRect(paintX, paintY, Size, Size);
            }
        }
    }

    private void paintGameArea(Graphics g) {
            int paintX;
            int paintY;

        for(int y=0; y < gb.getAreaHeight(); y++) {
            for(int x=0; x < gb.getAreaWidth(); x++) {
                // x and y position relative to humanPlayer at which tile should be drawn
                paintX = (x - humanPlayer.getX()) * Size + ((width - Size) / 2) + (int) ((-humanPlayer.getDx()) * Size *
                         ((gb.getTickCounter() + 1) / (double) gb.getTickReset()));
                paintY = (y - humanPlayer.getY()) * Size + ((height - Size) / 2) + (int) ((-humanPlayer.getDy()) * Size *
                         ((gb.getTickCounter() + 1) / (double) gb.getTickReset()));

                if (!(paintX + Size < 0 || paintX > width || paintY + Size < 0 || paintY > height)) {
                    g.setColor(Color.white);
                    g.fillRect(paintX, paintY, Size, Size);
                    g.setColor(gb.getTile(x, y).getColor());
                    g.fillRect(paintX, paintY, Size, Size);
                }
            }
        }
    }
}