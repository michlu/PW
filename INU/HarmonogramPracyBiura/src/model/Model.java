package model;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Michlu
 * @sience 2017-01-08
 */
public class Model {
    /**
     * Otwiera okno wyboru/zapisu pliku
     * @param czynnosc przyjmuje stringa open albo save
     * @param tytulOkna tytul okna
     * @return
     */
    public static File wybierzPlik(String czynnosc, String tytulOkna, Stage primaryStage) {
        File selectedFile;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(tytulOkna);
        // katalog otwierania (tam gdzie jest plik klasy)
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir);
        fileChooser.setInitialDirectory(file);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt"));
        if(czynnosc.equals("open")){
            selectedFile = fileChooser.showOpenDialog(primaryStage);
        }
        else if(czynnosc.equals("save")){
            selectedFile = fileChooser.showSaveDialog(primaryStage);
        }
        else{
            selectedFile = null;
        }
        return selectedFile;
    }

    public static ArrayList<Person> generujRaport(ObservableList<Person> personList) {
        ArrayList<Person> posortowanaLista = new ArrayList<>(personList);
        int a;
        int b;
        Person c;
        Person d;
        for (int i = 0; i < posortowanaLista.size(); i++) {
            for (int j = 0; j < posortowanaLista.size() - i-1; j++) {
                a = posortowanaLista.get(j).podajCzasPracy();
                b = posortowanaLista.get(j+1).podajCzasPracy();

                c = posortowanaLista.get(j);
                d = posortowanaLista.get(j+1);

                if (a < b) {
                    Person temp = d;
                    posortowanaLista.set(j+1, c);
                    posortowanaLista.set(j, temp);
                }
            }
        }
        for (Person person : posortowanaLista ) {
            System.out.println("Raport: " + person.toString());
        }
        return posortowanaLista;
    }


    public static void onlyNumberTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                textField.setText(oldValue);
            }
        });
        // Kasuj zero na poaczatku
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() == 1 && textField.getText().charAt(0) == '0') {
                textField.setText(textField.getText().substring(0, 0));
            }
        });
    }
}
