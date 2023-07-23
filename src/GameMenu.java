import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JPanel{
       private JTextField name1, name2;
       private JSpinner HeightSpnr, WidthSpnr, SpeedSpnr, EnemySpnr;
       private final ActionListener AL;

    public String getP1Name() {
           return name1.getText();
    }

    public String getP2Name() {
           return name2.getText();
    }

    public int getAreaHeight() {
           return Integer.parseInt(((JTextField)HeightSpnr.getEditor().getComponent(0)).getText());
    }

    public int getAreaWidth() {
           return Integer.parseInt(((JTextField)WidthSpnr.getEditor().getComponent(0)).getText());
    }

    public int getSpeed() {
           return Integer.parseInt(((JTextField)SpeedSpnr.getEditor().getComponent(0)).getText());
    }

    public int getEnemyNumber() {
           return Integer.parseInt(((JTextField)EnemySpnr.getEditor().getComponent(0)).getText());
    }

    GameMenu(ActionListener AL){
             this.AL = AL;
             setBackground(new Color(199, 134, 255));
             GridLayout gridLayout = new GridLayout(7, 2);
             setLayout(gridLayout);
             addComponents();
    }

    private void addComponents(){
            JButton playSingle = new JButton("Single-Player");
            JButton playMulti = new JButton("Multi-Player");
            JButton[] Buttons = {playSingle, playMulti};

        for(JButton button : Buttons){
            button.setSize(95, 80);
            button.addActionListener(AL);
            button.setFont(new Font("Courier", Font.PLAIN, 50));
            button.setBackground(new Color(80, 180, 100));
            button.setForeground(new Color(150, 80, 250));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            add(button);
        }

        JLabel p1name = new JLabel("Player.1");
        JLabel p2name = new JLabel("Player.2");
        JLabel[] labels = {p1name, p2name};

        for(JLabel label : labels){
            label.setFont(new Font("Courier", Font.PLAIN, 35));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            add(label);
        }

        name1 = new JTextField();
        name2 = new JTextField();
        JTextField[] textFields = {name1, name2};

        for(JTextField textField : textFields){
            textField.setFont(new Font("Courier", Font.PLAIN, 40));
            textField.setBackground(new Color(50,150, 100));
            textField.setForeground(Color.BLACK);
            textField.addMouseListener(new MyMouseListener(textField));
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setCaretColor(Color.BLACK);
            add(textField);
        }

        JLabel EnemyLabel = new JLabel("Enemies                       ");
        EnemySpnr = new JSpinner(new SpinnerNumberModel(20, 0, 30, 1));
        JLabel SpeedLabel = new JLabel("Speed (1 to 8)               ");
        SpeedSpnr = new JSpinner(new SpinnerNumberModel(4, 1, 8, 1));
        JLabel HeightLabel = new JLabel("Board Height                 ");
        HeightSpnr = new JSpinner(new SpinnerNumberModel(360, 25, 500, 5));
        JLabel WidthLabel = new JLabel("Board Width                 ");
        WidthSpnr = new JSpinner(new SpinnerNumberModel(360, 25, 500, 5));

        JLabel[] Labels = {EnemyLabel, SpeedLabel, HeightLabel, WidthLabel};
        JSpinner[] Spinners = {EnemySpnr, SpeedSpnr, HeightSpnr, WidthSpnr};

        for(JLabel label : Labels){
            label.setFont(new Font("Courier", Font.PLAIN, 28));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.RIGHT);
        }

        for(JSpinner spinner : Spinners){
            JTextField textField = (JTextField)spinner.getEditor().getComponent(0);
            spinner.setFont(new Font("Courier", Font.PLAIN, 24));
            textField.setBackground(new Color(199, 134, 255));
            textField.setForeground(Color.BLACK);
            textField.setHorizontalAlignment(JTextField.CENTER);
        }

        JComponent[] Components = {EnemyLabel, EnemySpnr, SpeedLabel, SpeedSpnr, HeightLabel, HeightSpnr, WidthLabel, WidthSpnr};
        for(JComponent component : Components){
            add(component);
        }
    }

    static class MyMouseListener implements MouseListener {
           private boolean changed = false;
           private final JTextField txt;

        public MyMouseListener(JTextField txt) {
               this.txt = txt;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
               if(!changed) {
                  txt.setText("");
                  txt.setForeground(Color.BLACK);
               }
                  changed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}