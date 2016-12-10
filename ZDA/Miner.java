import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Miner {

    private JFrame frame;
    private int iloscStrzalow = 0;

    // Pozycje min
    JButtonMiner miny[] = {
            new JButtonMiner(0,1), new JButtonMiner(3,1), new JButtonMiner(5,2),
            new JButtonMiner(9,2), new JButtonMiner(1,3), new JButtonMiner(6,3),
            new JButtonMiner(8,4), new JButtonMiner(5,5), new JButtonMiner(10,9),
            new JButtonMiner(4,12), new JButtonMiner(8,13), new JButtonMiner(4,14),
            new JButtonMiner(13,7), new JButtonMiner(6,7), new JButtonMiner(5,10),
    };

    public static void main(String[] args) {
        new Miner().run();
    }

    private void run(){
        SwingUtilities.invokeLater(() ->{
            frame = new JFrame("Miner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int rzedy = 15;
            int kolumny = 15;
            GridLayout gridLayout = new GridLayout(rzedy, kolumny);
            frame.setLayout(gridLayout);

            ButtonClick buttonClick = new ButtonClick(); // listner

            // rozklad buttonow na tablicy
            for(int i=0; i < rzedy; i++) {
                for(int j=0; j < kolumny; j++) {
                    JButtonMiner btn = new JButtonMiner(i,j);
                    frame.add(btn);
                    btn.addActionListener(buttonClick);
                    btn.setPreferredSize(new Dimension(30,30));
                }
            }
            frame.pack();
            frame.setVisible(true);
        });

    }
    class ButtonClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            JButtonMiner button = (Miner.JButtonMiner) event.getSource();

            for(JButtonMiner mina : miny){
                if((mina.getPozycjaY() == button.getPozycjaY()) && (mina.getPozycjaX() == button.getPozycjaX())){
                    System.out.println("boom!!!");
                    button.setEnabled(false);
                    button.setBackground(Color.RED);

                    JOptionPane.showOptionDialog(frame,
                            "Koniec gry, trafiłeś na minę!",
                            null, JOptionPane.WARNING_MESSAGE,
                            JOptionPane.OK_OPTION, null, null, null);
                    System.exit(1);
                    break;
                }
                else {
                    iloscStrzalow++;
                    frame.setTitle("Miner - ilosc strzalow "+iloscStrzalow);
                    System.out.println("Grasz dalej! Ilosc strzałow: " + iloscStrzalow);
                    button.setEnabled(false);
                    button.setBackground(Color.GREEN);
                    break;
                }
            }
        }
    }
    public class JButtonMiner extends JButton {
        private int pozycjaX;
        private int pozycjaY;
        public JButtonMiner(int x, int y){
            super();
            this.pozycjaX = x;
            this.pozycjaY = y;
        }
        public int getPozycjaX() {
            return pozycjaX;
        }
        public int getPozycjaY() {
            return pozycjaY;
        }
    }
}
