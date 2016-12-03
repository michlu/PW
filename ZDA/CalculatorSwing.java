import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Michał Nowiński
 * @sience 2016-11-29
 */
public class CalculatorSwing {

    private JLabel output;
    private double number1 = 0;
    private String operator = "";
    private boolean start = true;
    private final int MAX_OUTPUT_LENGTH = 28;
    private Model model = new Model();
    private Controller controller = new Controller();

    private ScalableJButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnChangeSign, btnC, btnDivision, btnPercent, btnResult, btnDot, btnAddition, btnMultipilication, btnSqrt, btnSubstraction;

    private int fontSize = 13;
    private Font font = new Font("Helvetica", Font.BOLD, fontSize);


    public static void main(String[] args) {
        new CalculatorSwing().run();
    }

    void run(){
        SwingUtilities.invokeLater(() ->{

            JFrame frame = new JFrame("Calculator SWING") {
                // Naprawia problem z setMaximumSize
            @Override
            public void paint(Graphics g) {
                Dimension d = getSize();
                Dimension m = getMaximumSize();
                boolean resize = d.width > m.width || d.height > m.height;
                d.width = Math.min(m.width, d.width);
                d.height = Math.min(m.height, d.height);
                if (resize) {
                    Point p = getLocation();
                    setVisible(false);
                    setSize(d);
                    setLocation(p);
                    setVisible(true);
                }
                super.paint(g);
            }};
            frame.setBounds(200, 400, 280, 300);
            frame.setMaximumSize(new Dimension(1000, 1000));
            frame.setMinimumSize(new Dimension(280, 300));
            frame.setPreferredSize(new Dimension(280, 300));

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GridBagLayout gridBagLayout = new GridBagLayout();
            frame.setLayout(gridBagLayout);


            // Kontener
            GridBagConstraints gbc = new GridBagConstraints();

            // Listnery
            ProcessNumpad processNumpad = new ProcessNumpad();
            ProcessOperator processOperator = new ProcessOperator();
            CalculatorKeyListner calculatorKeyListner = new CalculatorKeyListner();
            frame.addKeyListener(calculatorKeyListner);

            // TWORZENIE I ROZMIESZCZANIE ELEMENTOW

            // wiersz 1
            output = new ScalableJLabel("0", font);
            output.setHorizontalAlignment(SwingConstants.RIGHT);
            output.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            addElementToGridBag(frame, gbc, 0, 0, output);

            btnC = new ScalableJButton("C");
            addElementToGridBag(frame, gbc, 4, 0, btnC);
            btnC.addActionListener(processOperator);

            // wiersz 2
            btn7 = new ScalableJButton("7");
            addElementToGridBag(frame, gbc, 0, 1, btn7);
            btn7.addActionListener(processNumpad);

            btn8 = new ScalableJButton("8");
            addElementToGridBag(frame, gbc, 1, 1, btn8);
            btn8.addActionListener(processNumpad);

            btn9 = new ScalableJButton("9");
            addElementToGridBag(frame, gbc, 2, 1, btn9);
            btn9.addActionListener(processNumpad);

            btnDivision = new ScalableJButton("/");
            addElementToGridBag(frame, gbc, 3, 1, btnDivision);
            btnDivision.addActionListener(processOperator);

            btnSqrt = new ScalableJButton("sqrt");
            btnSqrt.setSize(new Dimension(100,100));
            addElementToGridBag(frame, gbc, 4, 1, btnSqrt);
            btnSqrt.addActionListener(processOperator);

            // wiersz 3
            btn4 = new ScalableJButton("4");
            addElementToGridBag(frame, gbc, 0, 2, btn4);
            btn4.addActionListener(processNumpad);

            btn5 = new ScalableJButton("5");
            addElementToGridBag(frame, gbc, 1, 2, btn5);
            btn5.addActionListener(processNumpad);

            btn6 = new ScalableJButton("6");
            addElementToGridBag(frame, gbc, 2, 2, btn6);
            btn6.addActionListener(processNumpad);

            btnMultipilication = new ScalableJButton("*");
            addElementToGridBag(frame, gbc, 3, 2, btnMultipilication);
            btnMultipilication.addActionListener(processOperator);

            btnPercent = new ScalableJButton("%");
            addElementToGridBag(frame, gbc, 4, 2, btnPercent);
            btnPercent.addActionListener(processOperator);

            // wiersz 4
            btn1 = new ScalableJButton("1");
            addElementToGridBag(frame, gbc, 0, 3, btn1);
            btn1.addActionListener(processNumpad);

            btn2 = new ScalableJButton("2");
            addElementToGridBag(frame, gbc, 1, 3, btn2);
            btn2.addActionListener(processNumpad);

            btn3 = new ScalableJButton("3");
            addElementToGridBag(frame, gbc, 2, 3, btn3);
            btn3.addActionListener(processNumpad);

            btnSubstraction = new ScalableJButton("-");
            addElementToGridBag(frame, gbc, 3, 3, btnSubstraction);
            btnSubstraction.addActionListener(processOperator);

            btnResult = new ScalableJButton("=");
            addElementToGridBag(frame, gbc, 4, 3, btnResult);
            btnResult.addActionListener(processOperator);

            // wiersz 5
            btn0 = new ScalableJButton("0");
            addElementToGridBag(frame, gbc, 0, 4, btn0);
            btn0.addActionListener(processNumpad);

            btnDot = new ScalableJButton(".");
            addElementToGridBag(frame, gbc, 1, 4, btnDot);
            btnDot.addActionListener(processNumpad);

            btnChangeSign = new ScalableJButton("+/-");
            addElementToGridBag(frame, gbc, 2, 4, btnChangeSign);
            btnChangeSign.addActionListener(processOperator);

            btnAddition = new ScalableJButton("+");
            addElementToGridBag(frame, gbc, 3, 4, btnAddition);
            btnAddition.addActionListener(processOperator);

            frame.setFocusable(true); // aby dzialal KeyListner
            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Metoda ustawiajaca buttony i label w kontenerze GridBag
     * @param frame przekazanie JFrame
     * @param gbc przekazanie kontenera
     * @param x przekazanie wartosci kontenera do GridX
     * @param y przekazanie wartosci kontenera do GridY
     * @param component dodanie przekazanego komponentu do kontenera
     */
    private void addElementToGridBag(JFrame frame, GridBagConstraints gbc, int x, int y, JComponent component) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        if(x == 4 && y == 3) gbc.gridheight = 2; // result =
        if(x == 0 && y == 0) gbc.gridwidth = 4; // label
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(2,2,2,2);
        frame.add(component,gbc);
    }

    /**
     * Metoda wylaczajaca przyciski w trakcie bledu
     */
    private void errorEnableButton(){
        ScalableJButton buttonTable[] = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnChangeSign, btnC, btnDivision, btnPercent, btnResult, btnDot, btnAddition, btnMultipilication, btnSqrt, btnSubstraction};
        for (ScalableJButton button : buttonTable) {
            if(btnC.equals(button)){
                button.setEnabled(true);
            }
            else {
                button.setEnabled(false);
            }
        }
    }

    /**
     * Metoda wlaczajaca przyciski po nacisnieciu C
     */
    private void refreshEnableButton(){
        ScalableJButton buttonTable[] = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnChangeSign, btnC, btnDivision, btnPercent, btnResult, btnDot, btnAddition, btnMultipilication, btnSqrt, btnSubstraction};
        for (ScalableJButton button : buttonTable) {
            button.setEnabled(true);
        }
    }

    class ProcessNumpad implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String value = ((JButton) event.getSource()).getText();
            controller.addText(value);
        }
    }
    class ProcessOperator implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String value = ((JButton) event.getSource()).getText();
            controller.addOperator(value);
        }
    }
    class CalculatorKeyListner extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            controller.setOnKeyPressed(e);
        }
    }

    /**
     * Klasa rozszerzajaca mozliwosci Jbuttona o skalowalna czcionke
     */
    public class ScalableJButton extends JButton {
        int mCurrentSize = 0;
        Font mInitialFont = null;
        int mInitialHeight;

        public ScalableJButton(String pString) {
            super(pString);
            init();
        }

        public ScalableJButton() {
            super();
            init();
        }

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

    /**
     * Klasa rozszerzajaca mozliwosci JLabel o skalowalna czcionke
     */
    public class ScalableJLabel extends JLabel {
        int mCurrentSize = 0;
        Font mInitialFont = null;
        int mInitialHeight;

        public ScalableJLabel(String pString, Font font) {
            super(pString);
            this.setFont(font);
            init();
        }

        public ScalableJLabel(String pString) {
            super(pString);
            init();
        }

        public ScalableJLabel() {
            super();
            init();
        }

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

    /**
     * Klasa kontrolera do widoku kalkulatora SWING
     */
    class Controller {
         //Zbiory przyjmowanych przyciskow
        int[] numpadKey = {96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 110,
                48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 46};
        int[] operatorKey = {47, 106, 45, 107, 109, 111};

        /**
         * Ustawia text w widoku aplikacji
         * @param value przyjmuje String interfejsu klawiatury numerycznej
         */
        public void addText(String value){
            if(start){
                output.setText("");
                start = false;
            }

            // Aby nie przekorczyc dlugosci okna wyswietlania
            if(!(output.getText().length() == MAX_OUTPUT_LENGTH)){

                // Jezeli pierwszym znakiem jest kropka (.) dodaj 0 przed znakiem
                if(output.getText().length() == 0 && value.equals("."))
                    output.setText("0");
                // Jezeli w lancuchu znakow jest kropka nie pozwol na dodanie kolejnej
                if(value.equals(".")){
                    int dot = 1;
                    for(int i=0;i<output.getText().length();++i){
                        if(output.getText().charAt(i)=='.') dot += 1;
                        if(dot==2) return;
                    }
                }
                // Jezeli pierwsza liczba to zero nie dodawaj kolejnego zera
                if(output.getText().length()==1 && output.getText().charAt(0)=='0' && value.equals("0")) return;
                // Jezeli pierwsza liczba to zero a drugi znak nie jest kropka skasuj zero
                if(output.getText().length()==1 && output.getText().charAt(0)=='0' && !value.equals(".")){
                    output.setText(output.getText().substring(0, 0));
                }

                output.setText(output.getText() + value);
            }
            else{
                return;
            }
        }

        /**
         * Ustawia operator, oraz ustawia wyniki dzialan matematycznych
         * @param value przyjmuje String interfejsu operatorow matematycznych
         */
        public void addOperator(String value) {
            // Kasowanie wyniku/wyswietlacza
            if(value.equals("C")){
                cleanOutput();
            }
            // Jezeli brak liczb, nic nie rob przy kliknieciu operoatorow
            if(output.getText().length()==0){
                return;
            }

            // Jezeli operatorem nie jest znak =
            if(!"=".equals(value)){

                // Funkcje operujace na jednej liczbie
                if(value.equals("sqrt") || value.equals("+/-") || value.equals("%")){
                    try {
                        output.setText(String.valueOf(model.calculate(Double.parseDouble(output.getText()), value)));
                    } catch (CalculationError e) {
                        e.printStackTrace();
                        output.setText("ERR");
                        errorEnableButton();
                    }
                }

                else {
                    operator = value;
                    // Jezeli jest juz pierwsza liczba i podana druga, przyciskajac operator matematyczny - oblicza i podaje wynik, przypisuje go do liczby1
                    if(number1>0){
                        try {
                            output.setText(String.valueOf(model.calculate(number1, Double.parseDouble(output.getText()), operator)));
                        } catch (CalculationError calculationError) {
                            calculationError.printStackTrace();
                            output.setText("ERR");
                            errorEnableButton();
                        }
                        number1 = Double.parseDouble(output.getText());
                        start = true;
                    }
                    // jezeli to pierwsza liczba, przypisuje ja d number1
                    else{
                        number1 = Double.parseDouble(output.getText());
                    }
                    start = true;
//                output.setText("");
                }
            }
            // Jezeli operatorem jest znak =
            else {
                if(operator.isEmpty()) return;
                prepareOutput(); //wynik

            }
        }
        public void setOnKeyPressed(KeyEvent event){

            String value = String.valueOf(event.getKeyChar());
            System.out.println(event.getKeyChar());


            // Jezeli enter albo przycisk =, daj wynik
            if(event.getKeyCode() == 10 || event.getKeyCode() == 61){
                if(operator.isEmpty()) return;
                prepareOutput(); //wynik
                return;
            }
            // Jezeli przycisk DEL kasuj
            if(event.getKeyCode() == 127){
                cleanOutput();
                return;
            }
            // dodaj wartosc jezeli przycisk sie zgadza z tabela numeryczna
            for (int keyCode :numpadKey) {
                if(event.getKeyChar() == keyCode){
                    if(event.getKeyCode() == 110) value = ".";
                    addText(value);
                    break;
                }
            }
            // dodaj opertator jezeli przysick zgadza sie z tabela operatorow
            for (int keyCode :operatorKey) {
                if(keyCode == event.getKeyCode()){
                    addOperator(value);
                    break;
                }
            }
        }

        public void cleanOutput() {
            output.setText("");
            operator = "";
            number1 = 0;
            refreshEnableButton();
            start = true;
        }

        public void prepareOutput() {
            // Pobiera pierwsza liczbe i przesyla druga wraz z operatorem
            // String do sprawdzenia ostatnich cyfer
            String checkString = null;
            try {
                checkString = String.valueOf(model.calculate(number1, Double.parseDouble(output.getText()), operator));
            } catch (CalculationError calculationError) {
                calculationError.printStackTrace();
                output.setText("ERR");
                errorEnableButton();
            }

            // Jezeli na koncu wyniku jest (.0) skasuj
            if(checkString.charAt(checkString.length()-1)=='0' && checkString.charAt(checkString.length()-2)=='.'){
                output.setText(checkString.substring(0, checkString.length()-2));
            }
            else{
                output.setText(checkString);
            }
            number1 = 0;
            operator = "";
            start = true;
        }
    }
    public class Model {
        /**
         * Wykonuje obliczenia na dwoch liczbach
         * @param number1 pierwsza przekzana liczba
         * @param number2 druga przekzana liczba
         * @param operator operator matematyczny
         * @return zwraca wynik rownania
         */
        public double calculate(double number1, double number2, String operator) throws CalculationError {
            switch (operator){
                case "+":
                    return checkValue(number1 + number2);
                case "-":
                    return checkValue(number1 - number2);
                case "*":
                    if(number2 ==0) return 0;
                    return checkValue(number1 * number2);
                case "/":
                    if(number2 ==0) return 0;
                    return checkValue(number1 / number2);
                case "%":
                    return checkValue(number1 % number2);
            }

            System.out.println("Nieznany operator - " + operator);
            return 0;
        }

        /**
         * Zmienia wartosc jednej liczby
         * @param number1 liczba do przeksztalcenia
         * @param operator operator matematyczny
         * @return zwraca wynik
         * @throws CalculationError blad obliczen matematycznych
         */
        public double calculate(double number1, String operator) throws CalculationError {
            switch (operator){
                case "%":
                    return checkValue(number1 / 100);
                case "+/-":
                    return checkValue(-number1);
                case "sqrt":
                    if(number1<0) throw (new CalculationError("Nie mozna wyciagac pierwiastka kwadratowego z liczb ujemnych"));
                    return Math.sqrt(number1);
                case "1/x":
                    return checkValue(1/number1);
            }

            System.out.println("Nieznany operator - " + operator);
            return 0;
        }
        public double checkValue(double value) throws CalculationError {
            if (Double.isInfinite(value) || Double.isNaN(value))
                throw new CalculationError("illegal double value: " + value);
            else
                return value;
        }

    }
    class CalculationError extends Exception {
        public CalculationError() {
            super();
        }
        public CalculationError(String s) {
            super(s);
            System.out.println(s);
        }
    }
}

