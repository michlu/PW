package nowin.turtle.controller;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TurtleController {

    @FXML private TextArea textArea;
    @FXML private TextField moveTxt, rotateTxt, colorTxt;
    @FXML private Canvas canvas;
    @FXML private Group turtle;

    private static final int MAX_X = 950;
    private static final int MAX_Y = 620;
    private static final int MIN_X = 130;
    private static final int MIN_Y = 10;

    private int[] tokenNumber = new int[3];      // wspolrzedne (dane z komend)
    private double x;
    private double y;
    private double turtleOldX = 0;
    private double turtleOldY = 0;
    private int angle = 0;
    private int angleOld = 0;
    private boolean pen = false; //true=rysuj; false=nie rysuj;
    private Color color = Color.BROWN;

    private GraphicsContext graphicsContext;
    private Animation animation;

    @FXML
    public void initialize(){
        graphicsContext = canvas.getGraphicsContext2D();
        textArea.setText("ustaw [15,200], 315; opusc; naprzod 250; obrot 90; naprzod 250;");
    }

    @FXML
    public void readInstructions(){
        if(!textArea.getText().isEmpty()){
            ArrayList<String> instructonsText = new ArrayList<>(); // tabela z zapamietanymi komendami
            String string = textArea.getText();
            StringTokenizer stringTokenizer = new StringTokenizer(string, ";");

            while (stringTokenizer.hasMoreElements()) {
                String token = stringTokenizer.nextElement().toString();
                instructonsText.add(token); // kopiowanie do listy komend
            }
            runMethod(readInstruction_GiveMethod_SetValues(instructonsText));

            instructonsText.remove(0); // kasowanie z pamieci
            textArea.clear();               // kasowanie tekstu
            System.out.println("Instrukcje w pamieci: " + instructonsText.toString());

            // wypelnienie tekstu nie wykorzystanymi komendami
            for (String token : instructonsText) {
                textArea.appendText(token + ";");
            }
        }
    }

    @FXML
    public void moveBtn(){
        if(moveTxt.getText().isEmpty())
            return;
        textArea.appendText("naprzod " + moveTxt.getText() + ";");
        moveTxt.clear();
    }
    @FXML
    public void rotateBtn(){
        if(rotateTxt.getText().isEmpty())
            return;
        textArea.appendText("obrot " + rotateTxt.getText() + ";");
        rotateTxt.clear();
    }
    @FXML
    public void penDownBtn(){
        textArea.appendText("opusc;");
    }
    @FXML
    public void penUpbtn(){
        textArea.appendText("podnies;");
    }
    @FXML
    public void colorBtn(){
        if(colorTxt.getText().isEmpty())
            return;
        textArea.appendText("kolor " + colorTxt.getText() + ";");
        colorTxt.clear();
    }
    @FXML
    public void resetBtn(){
        turtle.setLayoutX(MIN_X+15);
        turtle.setLayoutY(MAX_Y-300);
        turtle.setRotate(0);
        angle = 0;
        angleOld = 0;
        pen = false;
        textArea.clear();
        graphicsContext.clearRect(0, 0 , 870, 600);
    }

    private void runMethod(String method){
        x = turtle.getLayoutX();
        y = turtle.getLayoutY();
        switch (method){
            case "naprzod":
                moveTurtle();
                break;
            case "obrot":
                rotateTurtle(tokenNumber[0]);
                break;
            case "podnies":
                pen = false;
                break;
            case "opusc":
                pen = true;
                break;
            case "ustaw":
                turtle.setLayoutX(MIN_X+tokenNumber[0]);
                turtle.setLayoutY(MAX_Y-tokenNumber[1]);
                rotateTurtle(tokenNumber[2]);
                break;
        }
    }

    private void moveTurtle() {
        double px=x, py=y; // punkty poczatkowe dla lini

        x += Math.cos(Math.toRadians(angleOld))*tokenNumber[0];
        y += Math.sin(Math.toRadians(angleOld))*tokenNumber[0];
        System.out.println("From[x:"+turtleOldX+",y:"+turtleOldY+"] to [x:" +x+ ",y:" +y+"] angle: [" + angleOld + "]");
        turtleOldX = x;
        turtleOldY = y;

        // sprawdz czy nie przekracza osi X
        if(x>MAX_X){
            turtle.setLayoutX(MAX_X);
            rotateTurtle(135);
        }
        else if(x<MIN_X){
            turtle.setLayoutX(MIN_X);
            rotateTurtle(135);
        }
        else{
            turtle.setLayoutX(x);
        }
        // sprawdz czy nie przekracza osi Y
        if(y>MAX_Y){
            turtle.setLayoutY(MAX_Y);
            rotateTurtle(135);
        }
        else if(y<MIN_Y){
            turtle.setLayoutY(MIN_Y);
            rotateTurtle(135);
        }
        else{
            turtle.setLayoutY(y);
        }

        angle = 0; // wyzerowac kąt
        if(pen) {
            graphicsContext.setStroke(color);
            graphicsContext.setLineWidth(4);
            // wspolzedne kreski z poprawkami aby odpowiadaly srodkowi zlowia
            graphicsContext.strokeLine(px-107, py+20, x-107, y+20);
        }
    }

    private void rotateTurtle(int rotate) {
        angle = rotate;
        System.out.println("Obrot z: " + angleOld + " obrot do: " +angle);
        RotateTransition rt = new RotateTransition();
        rt.setNode(turtle);
        rt.setDuration(Duration.seconds(3));           // ile czasu trwa animacja
        rt.setFromAngle(angleOld);                     // od jakiego kata
        rt.setByAngle(angle);                          // obrot
        angleOld += angle;
        rt.setCycleCount(1);
        animation = rt;
        animation.play();
    }

    private String readInstruction_GiveMethod_SetValues(ArrayList<String> instructonsText) {
        Scanner scanner = new Scanner(instructonsText.get(0));
        String tokenString = scanner.next(); // komenda

        try {
            int i = 0;
            while (scanner.hasNext()){
                if(tokenString.equals("ustaw")){
                    String tt = scanner.next();
                    String regex = "\\d+"; // wylap same liczby
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(tt);

                    while (matcher.find()) {
                        if(i==3)
                            break;
                        System.out.println("G[" +i+ "] " + matcher.group().toString());
                        tokenNumber[i] = Integer.parseInt(matcher.group().toString());
                        i++;
                    }
                }
                else if(tokenString.equals("kolor")){
                    color = Color.valueOf(scanner.next());
                    System.out.println(color.toString());
                }
                else {
                    tokenNumber[i] = Integer.parseInt(scanner.next());
                    break;
                }
            }

        System.out.println("Komenda: [" + tokenString + "] liczby: [" +  tokenNumber[0] + " " +  tokenNumber[1] + " " + tokenNumber[2]+ "]");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return tokenString;
    }

    @FXML
    public void clearTextArea(){
        textArea.clear();
    }


    @FXML
    public void loadData() {
        textArea.clear();
        Scanner in = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik");
        // katalog otwierania (tam gdzie jest plik klasy)
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir);
        fileChooser.setInitialDirectory(file);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) return;
        try {
            in = new Scanner(selectedFile);
            while (in.hasNext()) {
                textArea.appendText(in.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
