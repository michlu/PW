package zal;

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;
import java.util.Date;


public class Miner {

    private JFrame frame;
    private GridLayout gridLayout;
    int czas = 0;

    private Font font = new Font("Serif", Font.BOLD, 14);

    // Pozycje min
    JButtonMiner[] miny = {
            new JButtonMiner(0,1), new JButtonMiner(3,1), new JButtonMiner(5,2),
            new JButtonMiner(9,2), new JButtonMiner(1,3), new JButtonMiner(6,3),
            new JButtonMiner(8,4), new JButtonMiner(5,5), new JButtonMiner(10,9),
            new JButtonMiner(4,12), new JButtonMiner(8,13), new JButtonMiner(4,14),
            new JButtonMiner(13,7), new JButtonMiner(6,7), new JButtonMiner(14,14)
    };

    public static void main(String[] args) {
        new Miner().run();
    }

    private void run(){
        SwingUtilities.invokeLater(() ->{

            frame = new JFrame("Miner ");


            Timer t = new Timer(1000, e->{
                czas++;
                frame.setTitle("Miner    " + czas);
            });
            t.start();


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int rzedy = 15;
            int kolumny = 15;
            gridLayout = new GridLayout(rzedy, kolumny);
            frame.setLayout(gridLayout);

            MouseListener clickMouseListner = new ClickMouseListner();

            // rozklad buttonow na tablicy
            for(int i=0; i < rzedy; i++) {
                for(int j=0; j < kolumny; j++) {
                    JButtonMiner btn = new JButtonMiner(i,j);
                    frame.add(btn);
                    btn.addMouseListener(clickMouseListner);
                    btn.setPreferredSize(new Dimension(30,30));
                    btn.setMargin(new Insets(0,0,0,0));
                }
            }
            frame.pack();
            frame.setVisible(true);
        });

    }

    public class ClickMouseListner extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent event){
            JButtonMiner button = (Miner.JButtonMiner) event.getSource();


            if ((event.getButton() == MouseEvent.BUTTON1) && ! button.getText().equals("M")) {
                for(JButtonMiner mina : miny){
                    if((mina.getPozycjaY() == button.getPozycjaY()) && (mina.getPozycjaX() == button.getPozycjaX())){

                        System.out.println("boom!!!");
                        button.setEnabled(false);
                        button.setText("!");
                        button.setBackground(Color.RED);


                        JOptionPane.showOptionDialog(frame,
                                "Koniec gry, trafiłeś na minę!",
                                null, JOptionPane.WARNING_MESSAGE,
                                JOptionPane.OK_OPTION, null, null, null);
                        System.exit(1);
                    }

                    else {
                        button.setEnabled(false);
                        button.setBackground(Color.GREEN);
                    }
                }
            }

            if (event.getButton() == MouseEvent.BUTTON3 != button.getText().equals("M")) {

                button.setText("M");
                button.setForeground(Color.RED);
            }
            else{
                button.setText("");
                button.setForeground(Color.BLACK);
            }
        }

    }


    public class JButtonMiner extends JButton {
        private int pozycjaX;
        private int pozycjaY;
        public JButtonMiner(int x, int y){
            super();
            init();
            this.pozycjaX = x;
            this.pozycjaY = y;
        }
        public int getPozycjaX() {
            return pozycjaX;
        }
        public int getPozycjaY() {
            return pozycjaY;
        }

        int mCurrentSize = 0;
        Font mInitialFont = null;
        int mInitialHeight;

        private void init() {
            mInitialFont = getFont();
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (mInitialHeight == 0) {
                mInitialHeight = getHeight();
            }
            int resizal = this.getHeight() * mInitialFont.getSize() / mInitialHeight;
            if(resizal != mCurrentSize){
                setFont(mInitialFont.deriveFont((float) resizal));
                mCurrentSize = resizal;
            }
            super.paintComponent(g);
        }
    }

}

