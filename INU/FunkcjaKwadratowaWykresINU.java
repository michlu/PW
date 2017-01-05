package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Program rysuje wykres z funkxji kwadratowej y = ax2 + c
 * Dla sprawdzenia: http://mathscribe.com/algebra1/quad/standard-to-vertex-form-2.html
 * @author Michlu
 * @sience 2016-12-18
 */
public class FunkcjaKwadratowaWykresINU extends Application {

    // Maksymalna i minimalna skala osi Y
    public static final int MAX_Y = 6;
    public static final int MIN_Y = -6;
    public static final int MAX_Y_PIXEL = 600;
    public static final int MIN_Y_PIXEL = 0;

    // jeden punkt na osi wynosi pixeli:
    public static final int WIELKOSC_PUNKTU = 50;

    public Label x1Label = new Label();
    public Label x2Label = new Label();
    public Label wLabel = new Label();

    DoubleProperty aProperty;
    DoubleProperty bProperty;
    DoubleProperty x1Property;
    DoubleProperty x2Property;
    DoubleProperty wPProperty;
    DoubleProperty wQProperty;
    DoubleProperty p1Property;
    DoubleProperty startYProperty;
    DoubleProperty p2Property;
    DoubleProperty endYProperty;

    public void obliczFunkcje(double a, double b){
        int powiekszOpunkt = 50; // jeden punkt na osi wynosi 50 pixeli
        a = aProperty.get();
        b = bProperty.get();
        double delta, pdelta, x1, x2, wP, wQ, p1 = 0, p2 = 0;
        double ratio = 0.5; // proporcja powiekszenia paraboli aby przecinala punkt controlny

        delta = -4*a*b;
        pdelta = Math.sqrt(delta);
        x1 = ((-pdelta)/(2*a))*WIELKOSC_PUNKTU;
        x2 = ((pdelta)/(2*a))*WIELKOSC_PUNKTU;
        wP = 0;
        wQ = b*WIELKOSC_PUNKTU;

        if(a>0) {
            startYProperty.setValue(MIN_Y_PIXEL);
            endYProperty.setValue(MIN_Y_PIXEL);
            wQProperty.setValue((-wQ+300)/ratio);

            p1 = -(Math.sqrt((MAX_Y - b) / a))*WIELKOSC_PUNKTU;
            p2 = Math.sqrt((MAX_Y - b) / a)*WIELKOSC_PUNKTU;

            wPProperty.setValue(wP+300);
            p1Property.setValue(p1+300);
            p2Property.setValue(p2+300);
        }
        else if(a==0){ // linia prosta przy a rownym zero
            startYProperty.setValue(-wQ+300);
            endYProperty.setValue(-wQ+300);
            p1Property.setValue(0);
            p2Property.setValue(600);
            wQProperty.setValue(-wQ+300);
            wPProperty.setValue(-wQ+300);
        }
        else{ // a mniejsze od zera
            startYProperty.setValue(MAX_Y_PIXEL);
            endYProperty.setValue(MAX_Y_PIXEL);
            wQProperty.setValue(-(wQ)/ratio);

            p1 = -(Math.sqrt((MIN_Y - b) / a))*WIELKOSC_PUNKTU;
            p2 = Math.sqrt((MIN_Y - b) / a)*WIELKOSC_PUNKTU;

            wPProperty.setValue(wP+300);
            p1Property.setValue(p1+300);
            p2Property.setValue(p2+300);
        }
        x1Property.setValue(x1+300);
        x2Property.setValue(x2+300);

        System.out.print("\n[Delta: " + delta + " Pierwiastek z delty: " + pdelta +"] [punkt1: " + p1 + " punkt2: "+ p2 + "] [x1: " + x1/WIELKOSC_PUNKTU + " x2: " + x2/WIELKOSC_PUNKTU + "]");
        System.out.print("[Q px: "+wQProperty.getValue() + " (Q var: "+wQ/WIELKOSC_PUNKTU+")]");

        // wspolrzedne przeciecia punktow z osia x
        if(x1!=Double.NaN && b!=0){
            x1Label.setText("("+String.format("%.1f", x1/WIELKOSC_PUNKTU)+" ; 0)");
            x1Label.setTextFill(Color.WHITE);
            x1Label.setLayoutY(280);
            x1Label.setLayoutX(x1Property.getValue()+10);

            x2Label.setText("("+String.format("%.1f", x2/WIELKOSC_PUNKTU)+" ; 0)");
            x2Label.setTextFill(Color.WHITE);
            x2Label.setLayoutY(280);
            x2Label.setLayoutX(x2Property.getValue()-45);
        }
        else{
            x1Label.isDisable();
            x2Label.isDisable();
        }

        // wspolrzedne wierzcholka
        wLabel.setText("("+String.format("%.1f", wQ/WIELKOSC_PUNKTU)+" ; 0)");
        wLabel.setTextFill(Color.WHITE);
        wLabel.setLayoutY(-wQ+280);
        if(a>0)wLabel.setLayoutY(-wQ+305);
        wLabel.setLayoutX(262);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // OBLICZENIA
        aProperty = new SimpleDoubleProperty(-4);
        bProperty = new SimpleDoubleProperty(4);

        x1Property = new SimpleDoubleProperty(1);
        x2Property = new SimpleDoubleProperty(-1);
        wPProperty = new SimpleDoubleProperty(300);
        wQProperty  = new SimpleDoubleProperty(-400);

        p1Property = new SimpleDoubleProperty(221);
        startYProperty = new SimpleDoubleProperty(600);
        p2Property = new SimpleDoubleProperty(379);
        endYProperty = new SimpleDoubleProperty(600);

        // LISTNERY - przy zmianie wartosci a oraz b
        aProperty.addListener((observable, oldValue, newValue) -> obliczFunkcje(aProperty.get(), bProperty.get()));
        bProperty.addListener((observable, oldValue, newValue) -> obliczFunkcje(aProperty.get(), bProperty.get()));

        // ROOT
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Wykres funkcji kwadratowej    y = ax2 + b");
        //PANE
        Pane pane = new Pane();
        pane.setPrefSize(600,600);
        pane.setStyle("-fx-background-color: #383838;");
        pane.getChildren().addAll(x1Label, x2Label, wLabel); // dodanie punktow przeciecia
        root.setCenter(pane);

        // GORNY PASEK
        AnchorPane anchorPane = new AnchorPane();
        root.setTop(anchorPane);

        StringConverter conventer = new NumberStringConverter();

        Label labelA = new Label("A");
        labelA.setLayoutX(15);
        labelA.setStyle("-fx-font-size: 18px;");

        Label funkcja1 = new Label("y = ");
        funkcja1.setLayoutX(220);
        funkcja1.setStyle("-fx-font-size: 22px; -fx-font-weigh: bold;");
        funkcja1.setFocusTraversable(false);

        Label funkcja2 = new Label(" x2 +");
        funkcja2.setLayoutX(330);
        funkcja2.setStyle("-fx-font-size: 22px; -fx-font-weigh: bold;");
        funkcja2.setFocusTraversable(false);

        TextField textFieldA = new TextField();
        textFieldA.setPrefWidth(75);
        textFieldA.setLayoutX(260);
        textFieldA.setStyle("-fx-font-size: 17px;");
        textFieldA.textProperty().bindBidirectional(aProperty, conventer);

        Label labelB = new Label("B");
        labelB.setLayoutX(655);
        labelB.setStyle("-fx-font-size: 18px;");

        TextField textFieldB = new TextField();
        textFieldB.setPrefWidth(75);
        textFieldB.setLayoutX(380);
        textFieldB.setStyle("-fx-font-size: 17px;");
        textFieldB.textProperty().bindBidirectional(bProperty, conventer);

        anchorPane.getChildren().addAll(labelA,textFieldB,textFieldA,labelB,funkcja1, funkcja2);

        // OSie
        Line lineY = new Line(300,0,300,600);
        lineY.setStroke(Color.ORANGE);
        lineY.setStrokeLineJoin(StrokeLineJoin.MITER);

        Line lineX = new Line(0,300,600,300);
        lineX.setStroke(Color.ORANGE);
        lineX.setStrokeLineJoin(StrokeLineJoin.MITER);

        pane.getChildren().addAll(lineX, lineY);

        // punkty na osi X
        for (int i = 0; i < 11; i++) {
            double liczbyX = -5.0+i;
            int powieksz = i * 50;
            Line lineXPunkt = new Line(50+powieksz,295,50+powieksz,305);
            lineXPunkt.setStroke(Color.ORANGE);
            Label liczba = new Label(String.valueOf(liczbyX));
            liczba.setLayoutX(40+powieksz);
            liczba.setLayoutY(305);
            liczba.setTextFill(Color.ORANGE);
            pane.getChildren().addAll(lineXPunkt, liczba);
        }
        // punkty na osi Y
        for (int i = 0; i < 11; i++) {
            double liczbyY = 5.0-i;
            int powieksz = i * 50;
            Line lineYPunkt  = new Line(295,50+powieksz,305,50+powieksz);
            lineYPunkt.setStroke(Color.ORANGE);
            if(liczbyY != 0){
                Label liczba = new Label(String.valueOf(liczbyY));
                liczba.setLayoutY(43+powieksz);
                liczba.setLayoutX(308);
                liczba.setTextFill(Color.ORANGE);
                pane.getChildren().add(liczba);
            }
            pane.getChildren().add(lineYPunkt);
        }

        //SLIDER A
        Slider sliderA = new Slider(-10, 10, 0); // wartosc minimalna, wartosc maksymalna, wartosc poczatkowego ustawienia
        sliderA.setLayoutX(10);
        sliderA.setLayoutY(90);
        sliderA.setOrientation(Orientation.VERTICAL);
        sliderA.setPrefWidth(40);
        sliderA.valueProperty().bindBidirectional(aProperty);
        root.setLeft(sliderA);

        //SLIDER B
        Slider sliderB = new Slider(-6, 6, 0); // wartosc minimalna, wartosc maksymalna, wartosc poczatkowego ustawienia
        sliderB.setLayoutX(10);
        sliderB.setLayoutY(90);
        sliderB.setOrientation(Orientation.VERTICAL);
        sliderB.setPrefWidth(40);
        sliderB.valueProperty().bindBidirectional(bProperty);
        root.setRight(sliderB);

        // KRZYWA
        QuadCurve quad = new QuadCurve(
                p1Property.get(),     // start x point
                startYProperty.get(), // start y point
                wPProperty.get(),     // control x point
                wQProperty.get(),     // control y point
                p2Property.get(),     // end x point
                endYProperty.get());  // end y point
        quad.setStrokeWidth(1);
        quad.setStroke(Color.GREENYELLOW);
        quad.setFill(Color.TRANSPARENT);

        // bindowanie punktow do wartosci
        quad.startXProperty().bind(p1Property);
        quad.startYProperty().bind(startYProperty);
        quad.controlXProperty().bind(wPProperty);
        quad.controlYProperty().bind(wQProperty);
        quad.endXProperty().bind(p2Property);
        quad.endYProperty().bind(endYProperty);

        pane.getChildren().add(quad);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
