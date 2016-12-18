package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Program robi wykres z funkxji kwadratowej y = ax2 + bx
 * Dla sprawdzenia podobny: http://mathscribe.com/algebra1/quad/standard-to-vertex-form-1.html
 * @author Michlu
 * @sience 2016-12-18
 */
public class FunkcjaKwadratowaWykres extends Application {

    DoubleProperty aProperty;
    DoubleProperty bProperty;
    DoubleProperty x1Property;
    DoubleProperty x2Property;
    DoubleProperty wPProperty;
    DoubleProperty wQProperty;

    public void obliczFunkcje(double a, double b){
        int powiekszOpunkt = 50;
        a = aProperty.get();
        b = bProperty.get();
        double delta;
        double pdelta, x1, x2, wP, wQ;

        delta = Math.pow(b, 2);
        pdelta = Math.sqrt(delta);
        x1 = ((-b-pdelta)/(2*a))*powiekszOpunkt;
        x2 = ((-b+pdelta)/(2*a))*powiekszOpunkt;
        wP = (-b/(2*a))*powiekszOpunkt;
        wQ = ((-delta)/(4*a))*powiekszOpunkt;

        x1Property.setValue(x1+300);
        x2Property.setValue(x2+300);
        wPProperty.setValue(wP+300);
        wQProperty.setValue(-wQ+300);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // OBLICZENIA
        aProperty = new SimpleDoubleProperty(-1);
        bProperty = new SimpleDoubleProperty(-5);

        x1Property = new SimpleDoubleProperty(0);
        x2Property = new SimpleDoubleProperty(-250);
        wPProperty = new SimpleDoubleProperty(-125);
        wQProperty  = new SimpleDoubleProperty(312.5);

        aProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                obliczFunkcje(aProperty.get(), bProperty.get());
            }
        });

        bProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                obliczFunkcje(aProperty.get(), bProperty.get());
            }
        });

        // ROOT
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Wykres funkcji kwadratowej    y = ax2 + bx");
        //PANE
        Pane pane = new Pane();
        pane.setPrefSize(600,600);
        pane.setStyle("-fx-background-color: DARKSLATEGRAY;");
        root.setCenter(pane);

        // GORNY PASEK
        AnchorPane anchorPane = new AnchorPane();
        root.setTop(anchorPane);

        StringConverter conventer = new NumberStringConverter();

        Label labelA = new Label("A");
        labelA.setLayoutX(15);

        TextField textFieldA = new TextField();
        textFieldA.setLayoutX(40);
        textFieldA.textProperty().bindBidirectional(aProperty, conventer);

        Label labelB = new Label("B");
        labelB.setLayoutX(660);

        TextField textFieldB = new TextField();
        textFieldB.setLayoutX(505);
        textFieldB.textProperty().bindBidirectional(bProperty, conventer);

        Label funkcja = new Label("y = ax2 + b");
        funkcja.setLayoutX(320);

        anchorPane.getChildren().addAll(labelA,textFieldB,textFieldA,labelB,funkcja);


        // OS
        Line lineY = new Line(300,0,300,600);
        lineY.setStroke(Color.GREENYELLOW);

        Line lineX = new Line(0,300,600,300);
        lineX.setStroke(Color.GREENYELLOW);

        pane.getChildren().addAll(lineX, lineY);

        // punkty na osi X
        for (int i = 0; i < 11; i++) {
            double liczbyX = -5.0+i;
            int powieksz = i * 50;
            Line lineXPunkt = new Line(50+powieksz,295,50+powieksz,305);
            lineXPunkt.setStroke(Color.GREENYELLOW);
            Label liczba = new Label(String.valueOf(liczbyX));
            liczba.setLayoutX(40+powieksz);
            liczba.setLayoutY(305);
            liczba.setTextFill(Color.GREENYELLOW);
            pane.getChildren().addAll(lineXPunkt, liczba);
        }
        // punkty na osi Y
        for (int i = 0; i < 11; i++) {
            double liczbyY = -5.0+i;
            int powieksz = i * 50;
            Line lineYPunkt  = new Line(295,50+powieksz,305,50+powieksz);
            lineYPunkt.setStroke(Color.GREENYELLOW);
            if(liczbyY != 0){
                Label liczba = new Label(String.valueOf(liczbyY));
                liczba.setLayoutY(43+powieksz);
                liczba.setLayoutX(308);
                liczba.setTextFill(Color.GREENYELLOW);
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
        Slider sliderB = new Slider(-10, 10, 0); // wartosc minimalna, wartosc maksymalna, wartosc poczatkowego ustawienia
        sliderB.setLayoutX(10);
        sliderB.setLayoutY(90);
        sliderB.setOrientation(Orientation.VERTICAL);
        sliderB.setPrefWidth(40);
        sliderB.valueProperty().bindBidirectional(bProperty);
        root.setRight(sliderB);


        // KRZYWA
        Path path = new Path();
        path.setStrokeWidth(3);
        pane.getChildren().add(path);

        QuadCurve quad = new QuadCurve(
                x1Property.get()+300,     // start x point
                0+300,                    // start y point
                wPProperty.get()+300,   // control x point
                -wQProperty.get()+300,  // control y point
                x2Property.get()+300,     // end x point
                0+300);                   // end y point
        quad.setStrokeWidth(1);
        quad.setStroke(Color.WHITE);
        quad.setFill(Color.TRANSPARENT);
        System.out.print("Wspolrzedne punktu1: " + "(" + (quad.getStartX()-300) + ", " + (quad.getStartY()-300) +")");
        System.out.print(", Wspolrzedne wierzcholka: " + "(" + (quad.getControlX()-300) + ", " + (quad.getControlY()-300) +")");
        System.out.print(", Wspolrzedne punktu2: " + "(" + (quad.getEndX()-300) + ", " + (quad.getEndY()-300) +")");

        // bindowanie punktow do wartosci
        quad.startXProperty().bind(x1Property);
        quad.controlXProperty().bind(wPProperty);
        quad.controlYProperty().bind(wQProperty);
        quad.endXProperty().bind(x2Property);

        pane.getChildren().add(quad);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
