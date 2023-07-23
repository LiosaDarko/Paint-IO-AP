import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class GameBoard extends JPanel {
       private final int areaHeight, areaWidth, enemyNumber, reset, Size = 25;
       private Tile[][] Area;
       private final ArrayList<Player> players = new ArrayList<>();
       private final ArrayList<MainPlayer> mainPlayers = new ArrayList<>();
       private final HashMap<Tile, Player> tilePlayerMap = new HashMap<>();
       private int counter = 0;
       private final ArrayList<Player> deadEnemies = new ArrayList<>();
       private boolean paused = true;
       private final ActionListener AL;
       private final ArrayList<Painter> painters = new ArrayList<>();
       private final HashMap<Player, Painter> playerPainter = new HashMap<>();
       private final List<Color> colorList = new ArrayList<>(Arrays.asList(Color.magenta, Color.green, Color.red, Color.blue, Color.orange, Color.yellow, Color.pink, Color.cyan, Color.darkGray));

    public void setPaused(Boolean bool){
           paused = bool;
    }
    public int getAreaHeight() {
           return areaHeight;
    }
    public int getAreaWidth() {
           return areaWidth;
    }
    public int getTickCounter() {
           return counter;
    }
    public int getTickReset() {
           return reset;
    }
    public Tile getTile(int x, int y){
           return Area[y][x];
    }

    GameBoard(ActionListener AL, String p1name, int areaHeight, int areaWidth, int gameSpeed, int enemyNumber){
              this.AL = AL;
              this.areaHeight = areaHeight;
              this.areaWidth = areaWidth;
              this.enemyNumber = enemyNumber;
              int[] speeds = {15, 13, 11, 9, 7, 5, 4, 3};
              reset = speeds[gameSpeed-1];

        players.add(new MainPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p1name));
        mainPlayers.add((MainPlayer)players.get(0));
        loadBoard();
        painters.add(new Painter(Size, this, mainPlayers.get(0), players));
        playerPainter.put(mainPlayers.get(0), painters.get(0));
    }

    GameBoard(ActionListener AL, String p1name, String p2name, int areaHeight, int areaWidth, int gameSpeed, int enemyNumber) {
              this.AL = AL;
              this.areaHeight = areaHeight;
              this.areaWidth = areaWidth;
              this.enemyNumber = enemyNumber;
              int[] speeds = {15, 13, 11, 9, 7, 5, 4, 3};
              reset = speeds[gameSpeed-1];

        players.add(new MainPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p1name));
        players.add(new MainPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p2name));
        mainPlayers.add((MainPlayer)players.get(0));
        mainPlayers.add((MainPlayer)players.get(1));
        loadBoard();
        painters.add(new Painter(Size, this, mainPlayers.get(0), players));
        painters.add(new Painter(Size, this, mainPlayers.get(1), players));
        playerPainter.put(mainPlayers.get(0), painters.get(0));
        playerPainter.put(mainPlayers.get(1), painters.get(1));
    }

    private void loadBoard(){
            this.Area = new Tile[areaHeight][areaWidth];
                 for(int i=0; i < Area.length; i++){
                 for(int j=0; j < Area[i].length; j++){
                     Area[i][j] = new Tile(j,i);
                 }
            }

        moveByKey();
        setBackground(Color.BLACK);

            for(int i=0; i < enemyNumber; i++){
                if(i > 8){
                   players.add(new EnemyPlayer(Area.length, Area[0].length, new Color((int)(Math.random() * 0x1000000))));
                }
                else{
                   players.add(new EnemyPlayer(Area.length, Area[0].length, colorList.get(i)));
                }
            }

        for(int i=0; i < players.size(); i++){
            if(!checkSpawn(players.get(i))){
                players.remove(players.get(i));
                                           i--;
                if(enemyNumber > 8){
                    players.add(new EnemyPlayer(Area.length,Area[0].length, new Color((int)(Math.random() * 0x1000000))));
                }
                else{
                    players.add(new EnemyPlayer(Area.length,Area[0].length, colorList.get(i)));
                }
            }
            else{
                startingPoint(players.get(i));
            }
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                0, 1000/70);
    }
    private void moveByKey(){
            InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();

        if(mainPlayers.size() == 1){
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
            am.put("up", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_UP);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
            am.put("down", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_DOWN);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
            am.put("left", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_LEFT);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
            am.put("right", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_RIGHT);
                }
            });
        }
        else if(mainPlayers.size() == 2){
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up1");
            am.put("up1", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(1).setKey(KeyEvent.VK_UP);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down1");
            am.put("down1", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(1).setKey(KeyEvent.VK_DOWN);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left1");
            am.put("left1", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(1).setKey(KeyEvent.VK_LEFT);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right1");
            am.put("right1", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(1).setKey(KeyEvent.VK_RIGHT);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up2");
            am.put("up2", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_W);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down2");
            am.put("down2", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_S);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left2");
            am.put("left2", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_A);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right2");
            am.put("right2", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    mainPlayers.get(0).setKey(KeyEvent.VK_D);
                }
            });
        }
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
        am.put("pause", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "pause");
                AL.actionPerformed(action);
            }
        });
    }
    private void startingPoint(Player player){
            int x = player.getX();
            int y = player.getY();
        if(!checkSpawn(player)){
            Player playerCopy = new EnemyPlayer(Area.length, Area[0].length, player.getColor());
            startingPoint(playerCopy);
        }
        for(int i=x-1; i <= x; i++){
            for(int j=y-1; j <= y; j++){
                player.setPossessedTiles(getTile(i,j));
            }
        }
    }

    private boolean checkSpawn(Player player){
            int x = player.getX();
            int y = player.getY();
        for(int i=x-3; i <= x+3; i++) {
            for (int j=y-3; j <= y+3; j++) {
                if(getTile(i, j).getOwner() != null || getTile(i, j).getContestedOwner() != null) {
                   return false;
                }
            }
        }
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
           super.paintComponent(g);
        for(int i=0; i < painters.size(); i++){
            g.setClip(getWidth()/painters.size() * i, 0, getWidth()/painters.size(), getHeight());
            g.translate(getWidth()/painters.size() * i, 0);
            painters.get(i).Paint(g);
            g.translate(-getWidth()/painters.size() * i, 0);
        }
        try {
            PaintScore(g);
        } catch(IndexOutOfBoundsException ignored){
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void PaintScore(Graphics g) {
            g.setFont(new Font("Courier", Font.PLAIN, 16));
            FontMetrics fontMetrics = g.getFontMetrics();
            int fontHeight = fontMetrics.getHeight();
            int barWidth;
            int barHeight = fontHeight + 4;

        Player player;
        String string;

        double bestScore = players.get(0).getScore();
               Collections.sort(players);
        for(int i=0; i < Integer.min(6, players.size()); i++){
            player = players.get(i);
            string = String.format("%.0f - " + player.getName(), player.getScore());
            barWidth = (int)((player.getScore()/bestScore) * (getWidth()/4));
            g.setColor(player.getColor());
            g.fillRect(getWidth()-barWidth-15, barHeight*i, barWidth, barHeight);
            g.setColor(Color.WHITE);
            g.drawString(string, getWidth()-barWidth-10, barHeight*i + fontHeight);
        }
    }
    private void tick(){
            Player player;
            tilePlayerMap.clear();
        for(int i=0; i < players.size(); i++) {
            player = players.get(i);
            player.move();
            if(player.getX() < 0 || player.getX() >= areaWidth || player.getY() < 0 || player.getY() >= areaHeight){
               player.die();
            }
            else{
                Tile tile = getTile(player.getX(), player.getY());
                player.checkHit(tile);
                player.setPresentTile(tile);
                findHit(player, tile);

                if(tile.getOwner() != player && player.getAlive()) {
                   player.setContestedTiles(tile);
                }
                else if(player.getContestedTiles().size() > 0) {
                        player.contestToPossess();
                        fillClosedArea(player);
                }
            }
            if(player instanceof EnemyPlayer && !player.getAlive()){
                deadEnemies.add(player);
            }
        }
        recreateEnemies();
        boolean allKilled = true;

        for(MainPlayer mainPlayer : mainPlayers){
            mainPlayer.dir();
            playerPainter.get(mainPlayer).setPaint(mainPlayer.getAlive());
            allKilled = allKilled && !mainPlayer.getAlive();
        }
        if(allKilled){
            endGame();
        }
        players.removeIf(p -> !p.getAlive());
    }

    private void endGame(){
            JOptionPane.showMessageDialog(this, "You Lost.", "Game Over!", JOptionPane.WARNING_MESSAGE);
            AL.actionPerformed(new ActionEvent(this, 0, "End Game"));
    }

    private void recreateEnemies(){
            for(int i=0; i < deadEnemies.size(); i++){
                if(deadEnemies.get(i).getAlive()){
                   Player player = new EnemyPlayer(Area.length, Area[0].length, new Color((int)(Math.random() * 0x1000000)));
                   startingPoint(player);
                   players.add(player);
                   deadEnemies.remove(deadEnemies.get(i));
            }
        }
    }

    private void findHit(Player player, Tile tile) {
            if(tilePlayerMap.containsKey(tile)) {
            for(Map.Entry<Tile, Player> entry : tilePlayerMap.entrySet()) {
                if(entry.getKey() == tile) {
                    if(entry.getValue().getContestedTiles().size() > player.getContestedTiles().size()) {
                       entry.getValue().die();
                    }
                    else if(entry.getValue().getContestedTiles().size() < player.getContestedTiles().size()) {
                            player.die();
                    }
                    else if(entry.getValue().getContestedTiles().size() == player.getContestedTiles().size()) {
                         if(entry.getValue().getPossessedTiles().size() > player.getPossessedTiles().size()) {
                            entry.getValue().die();
                        }
                        else {
                             player.die();
                        }
                    }
                }
            }
        }
        else {
             tilePlayerMap.put(tile, player);
        }
        players.removeIf(p -> !p.getAlive());
    }

    private void updateTime(){
            counter++;
            counter %= reset;
    }

    private void fillClosedArea(Player player) {
            int maxX = 0;
            int maxY = 0;
            int minX = Area[0].length;
            int minY = Area.length;
        for(Tile tile : player.getPossessedTiles()) {
            if(tile.getX() > maxX) maxX = tile.getX();
            if(tile.getX() < minX) minX = tile.getX();
            if(tile.getY() > maxY) maxY = tile.getY();
            if(tile.getY() < minY) minY = tile.getY();
        }
        ArrayList<Tile> outside = new ArrayList<>();
        ArrayList<Tile> inside  = new ArrayList<>();
        ArrayList<Tile> visited = new ArrayList<>();
        HashSet<Tile> toCheck = new HashSet<>();
        int y;
        int x;

        for(Tile tile : player.getPossessedTiles()){
            y = tile.getY();
            x = tile.getX();
            if(y - 1 >= 0) toCheck.add(Area[y-1][x]);
            if(y + 1 < Area.length) toCheck.add(Area[y+1][x]);
            if(x - 1 >= 0) toCheck.add(Area[y][x-1]);
            if(x + 1 < Area[y].length) toCheck.add(Area[y][x+1]);
        }

        for(Tile tile : toCheck){
            if(!inside.contains(tile)){
                Stack<Tile> stack = new Stack<>();
                boolean temp = true;
                Tile v;
                visited.clear();
                stack.push(tile);
                while((!stack.empty()) && temp){
                        v = stack.pop();
                    if(!visited.contains(v) && (v.getOwner() != player)){
                        y = v.getY();
                        x = v.getX();
                        if(outside.contains(v)
                                || x < minX || x > maxX || y < minY || y > maxY
                                || x == Area[0].length-1 || x == 0 || y == 0 || y == Area.length-1){
                            temp = false;
                        }
                        else{
                            visited.add(v);
                            if(y -1 >= 0) stack.push(Area[y-1][x]);
                            if(y + 1 < Area.length) stack.push(Area[y+1][x]);
                            if(x - 1 >= 0) stack.push(Area[y][x-1]);
                            if(x + 1 < Area[y].length) stack.push(Area[y][x+1]);
                        }
                    }
                }
                if(temp){
                    inside.addAll(visited);
                }
                else{
                    outside.addAll(visited);
                }
            }
        }
        for(Tile tile : inside){
            player.setPossessedTiles(tile);
        }
    }
    private class ScheduleTask extends TimerTask {
            @Override
            public void run() {
                   if(!paused) {
                       updateTime();
                   if(counter == 0) {
                       tick();
                   }
            repaint();
            }
        }
    }
}