import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Paintio extends JFrame implements ActionListener {
       private GameBoard gb;
       private GameMenu menu;
       private JPanel cards;

    private Paintio(){
            StartGame();
    }
    private void StartGame(){
            setSize(900, 800);
            setResizable(false);
            setVisible(true);
            setTitle("Paint.IO");
            ImageIcon icon = new ImageIcon("F:\\Paint-IO-AP\\src\\icon.png");
            setIconImage(icon.getImage());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
        menu = new GameMenu(this);
        cards = new JPanel(new CardLayout());
        cards.add(menu, "GameMenu");
        add(cards);
    }
    private void setMode(Mode mode){
            CardLayout cardLayout = (CardLayout) cards.getLayout();
         if(mode == Mode.GAME){
            cardLayout.show(cards, "GameBoard");
            gb.setPaused(false);
         }
         else if(mode == Mode.MENU){
                 cardLayout.show(cards, "GameMenu");
                 gb.setPaused(true);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
           switch(e.getActionCommand()) {
            case "Single-Player":
                  gb = new GameBoard(this, menu.getP1Name(), menu.getAreaHeight(), menu.getAreaWidth(), menu.getSpeed(), menu.getEnemyNumber());
                  cards.add(gb, "GameBoard");
                  setMode(Mode.GAME);
                  break;
            case "Multi-Player":
                  gb = new GameBoard(this, menu.getP1Name(), menu.getP2Name(), menu.getAreaHeight(), menu.getAreaWidth(), menu.getSpeed(), menu.getEnemyNumber());
                  cards.add(gb, "GameBoard");
                  setMode(Mode.GAME);
                  break;
            case "End Game":
                  setMode(Mode.MENU);
                  break;
            }
    }
    public static void main(String[] args) {
           EventQueue.invokeLater(Paintio::new);
    }
}