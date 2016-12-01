package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
/**
 * Program kompresuje/dekompresuje łańcuch znaków poprzez zliczanie liter "aaaBBbbcccc" <-> "a3B2b2c4"
 * @author Michal Nowinski
 * @sience 2016-11-27
 */
public class KompresjaDekompresjaRegex extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            Scene scene = new Scene(root, 340, 220);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            TextField textfield1 = new TextField();
            textfield1.setPrefWidth(300);
            TextField textfield2 = new TextField();
            textfield2.setPrefWidth(300);

            RadioButton radioButton1 = new RadioButton("Kompresja");
            RadioButton radioButton2 = new RadioButton("Dekompresja");

            ToggleGroup toggleGroup = new ToggleGroup();
            radioButton1.setToggleGroup(toggleGroup);
            radioButton2.setToggleGroup(toggleGroup);
            toggleGroup.selectToggle(radioButton1);

            Button btnLicz = new Button("Licz");
            Button btnReset = new Button("Reset");
            Button btnCopy = new Button("^ Kopiuj ^");


            root.getChildren().addAll(textfield1, btnCopy, textfield2, btnLicz, btnReset, radioButton1, radioButton2);
            // ROZSTAWIENIE ELEMENTOW
            textfield1.setLayoutX(20);
            textfield1.setLayoutY(30);

            btnCopy.setLayoutX(60);
            btnCopy.setLayoutY(65);
            btnCopy.setPrefWidth(115);

            textfield2.setLayoutX(20);
            textfield2.setLayoutY(100);

            btnLicz.setLayoutX(60);
            btnLicz.setLayoutY(160);
            btnLicz.setPrefWidth(60);

            btnReset.setLayoutX(130);
            btnReset.setLayoutY(160);

            radioButton1.setLayoutX(220);
            radioButton1.setLayoutY(140);

            radioButton2.setLayoutX(220);
            radioButton2.setLayoutY(180);

            //OBSLUGA ZDARZEN

            btnLicz.setOnAction(e -> {
                // sprawdz odpowiednie metode kompresji/dekompresji
                if (radioButton1.isSelected()) {
                    textfield2.setText(kompresuj(textfield1.getText()));
                } else {
					textfield2.setText(dekompresuj(textfield1.getText()));
                }
            });

            btnCopy.setOnAction(e -> {
                textfield1.setText(textfield2.getText());
                textfield2.setText("");

                // zmien zaznaczenie kompresja/dekompresja
                if(radioButton1.isSelected()){
                    radioButton2.setSelected(true);
                }
                else{
                    radioButton1.setSelected(true);
                }
            });
            // wyczysc pola textowe
            btnReset.setOnAction(e -> {
                textfield1.setText("");
                textfield2.setText("");
                radioButton1.setSelected(true);
            });


            primaryStage.setScene(scene);
            primaryStage.setTitle("Kompresja/Dekompresja String");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String kompresuj(String str) {
        String regex = "(.)(\\1*)";                 // (.) zamiast \D, dzieki czemu mozna rowniez roskladac ilosc cyfer
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String wynik = new String();
        while (matcher.find()) {
            String litera = matcher.group().substring(0, 1);
            int iloscLiter = Integer.parseInt(String.valueOf(matcher.group().length()));
            wynik = wynik + litera + iloscLiter;
        }
        return wynik;
    }

//    STARA METODA KOMPRESUJ BEZ URZYCA REGEX'OW
//    public static String compresed(String str) {
//        String wynik = new String();
//        int licznik = 1, i;
//        for (i = 0; i < str.length() - 1; i++)
//            if (str.charAt(i) == str.charAt(i + 1))
//                licznik++;
//            else {
//                wynik = wynik + str.charAt(i) + licznik;
//                licznik = 1;
//            }
//        wynik = wynik + str.charAt(i) + licznik;
//        return wynik;
//    }

    public String dekompresuj(String str) {
        String regex = "(.)\\d";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String wynik = new String();
        while (matcher.find()) {
                String litera = matcher.group().substring(0, 1);
                int iloscLiter = Integer.parseInt(matcher.group().substring(1, 2));
                wynik = wynik + litera;
                for (int i = 1; i < iloscLiter; i++) {
                    wynik = wynik + litera;
                }
        }
        return wynik;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
