package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
        String currentDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "files";
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

    public static ObservableList<Person> generateReport(ObservableList<Person> personList) {
        ObservableList<Person> sortedList = FXCollections.observableArrayList(personList);
        int aPersonTimeWork;
        int bPersonTimeWork;
        Person aPeron;
        Person bPerson;
        for (int i = 0; i < sortedList.size(); i++) {
            for (int j = 0; j < sortedList.size() - i-1; j++) {
                aPersonTimeWork = sortedList.get(j).podajCzasPracy();
                bPersonTimeWork = sortedList.get(j+1).podajCzasPracy();

                aPeron = sortedList.get(j);
                bPerson = sortedList.get(j+1);

                if (aPersonTimeWork < bPersonTimeWork) {
                    sortedList.set(j+1, aPeron);
                    sortedList.set(j, bPerson);
                }
            }
        }
        for (Person person : sortedList ) {
            System.out.println("Raport: " + person.toString());
        }
        return sortedList;
    }

    public static void onlyNumberTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9:]*")) {
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
