package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Person;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import model.Model;

public class MainWindowController {

    private Stage primaryStage;

    @FXML
    private TableView<Person> mainTableView;
    @FXML
    private TableColumn<Person, String> imieColumn, nazwiskoColumn, pokojColumn;
    @FXML
    private TableColumn<Person, String> odgodzColumn, dogodziColumn;
    @FXML
    private TextField imieTextField, nazwiskoTextField, pokojTextField, odgodzTextField, dogodzTextField;
    @FXML
    private Label alertLabel;
    @FXML
    private Shape rectnagle1, rectnagle2, rectnagle3, rectnagle4, rectnagle5, rectnagle6, rectnagle7, rectnagle8, rectnagle9, rectnagle10, rectnagle11, rectnagle12, rectnagle13, rectnagle14;
    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14;

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainTableView.setItems(personList);

    }

    @FXML
    public void initialize() {
        // USTAWIENIA TEXTFIELDA POKOJ
        pokojTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                pokojTextField.setText(oldValue);
            } else {
                czyscPokoje();
                if (newValue.isEmpty()) return;
                switch (Integer.parseInt(newValue)) {
                    case 1:
                        rectnagle1.setVisible(true);
                        label1.setVisible(true);
                        break;
                    case 2:
                        rectnagle2.setVisible(true);
                        label2.setVisible(true);
                        break;
                    case 3:
                        rectnagle3.setVisible(true);
                        label3.setVisible(true);
                        break;
                    case 4:
                        rectnagle4.setVisible(true);
                        label4.setVisible(true);
                        break;
                    case 5:
                        rectnagle5.setVisible(true);
                        label5.setVisible(true);
                        break;
                    case 6:
                        rectnagle6.setVisible(true);
                        label6.setVisible(true);
                        break;
                    case 7:
                        rectnagle7.setVisible(true);
                        label7.setVisible(true);
                        break;
                    case 8:
                        rectnagle8.setVisible(true);
                        label8.setVisible(true);
                        break;
                    case 9:
                        rectnagle9.setVisible(true);
                        label9.setVisible(true);
                        break;
                    case 10:
                        rectnagle10.setVisible(true);
                        label10.setVisible(true);
                        break;
                    case 11:
                        rectnagle11.setVisible(true);
                        label11.setVisible(true);
                        break;
                    case 12:
                        rectnagle12.setVisible(true);
                        label12.setVisible(true);
                        break;
                    case 13:
                        rectnagle13.setVisible(true);
                        label13.setVisible(true);
                        break;
                    case 14:
                        rectnagle14.setVisible(true);
                        label14.setVisible(true);
                        break;
                    default:
                        czyscPokoje();
                }
            }
        });

        imieColumn.setCellValueFactory(
                new PropertyValueFactory<Person, String>("imie"));
        nazwiskoColumn.setCellValueFactory(
                new PropertyValueFactory<Person, String>("nazwisko"));
        pokojColumn.setCellValueFactory(
                new PropertyValueFactory<Person, String>("pokoj"));
        odgodzColumn.setCellValueFactory(
                new PropertyValueFactory<Person, String>("pracujeOd"));
        dogodziColumn.setCellValueFactory(
                new PropertyValueFactory<Person, String>("pracujeDo"));

        // WYBIERANIE OSOBY Z LISTY W TABELI
        mainTableView.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
                    imieTextField.setText(newVal.getImie());
                    nazwiskoTextField.setText(newVal.getNazwisko());
                    pokojTextField.setText(newVal.getPokoj());
                    odgodzTextField.setText(newVal.getPracujeOd());
                    dogodzTextField.setText(newVal.getPracujeDo());
                }
        );

        Model.onlyNumberTextField(odgodzTextField);
        Model.onlyNumberTextField(dogodzTextField);
    }

    @FXML
    public void dodajOsobe() {
        if (imieTextField.getText().isEmpty() || nazwiskoTextField.getText().isEmpty() || pokojTextField.getText().isEmpty() || odgodzTextField.getText().isEmpty() || dogodzTextField.getText().isEmpty()) {
            alertLabel.setVisible(true);
            return;
        }
        alertLabel.setVisible(false);
        personList.add(new Person(imieTextField.getText(), nazwiskoTextField.getText(), pokojTextField.getText(), odgodzTextField.getText(), dogodzTextField.getText()));
        imieTextField.clear();
        nazwiskoTextField.clear();
        pokojTextField.clear();
        odgodzTextField.clear();
        dogodzTextField.clear();
    }

    @FXML
    public void wczytajPlik() {
        Scanner in = null;
        File file = Model.wybierzPlik("open","Wczytaj plik...", primaryStage);
        if (file == null) return;
        try {
            in = new Scanner(file);

            while (in.hasNext()) {
                Person person = new Person(in.next(), in.next(), in.next(), in.next(), in.next());
                personList.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @FXML
    public void resetField() {
        imieTextField.clear();
        nazwiskoTextField.clear();
        pokojTextField.clear();
        odgodzTextField.clear();
        dogodzTextField.clear();
        alertLabel.setVisible(false);
    }

    @FXML
    public void zapiszTabliceDoPliku(){
        zapiszPlik(personList);
    }

    public void zapiszPlik(List<Person> listaOsob) {
        if (!mainTableView.getItems().isEmpty()) {
            PrintWriter printWriter = null;
            try {
                File file = Model.wybierzPlik("save","Zapisz plik...", primaryStage);
                if (file == null) return;
                printWriter = new PrintWriter(file);
                for (Person person : listaOsob) {
                    System.out.println("Zapis: " + person.toString());
                    printWriter.printf("%s %s %s %s %s\n", person.getImie(), person.getNazwisko(), person.getPokoj(), person.getPracujeOd(), person.getPracujeDo());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
        } else {
            Alert alertPustaLista = new Alert(Alert.AlertType.ERROR, "Tablica jest pusta, brak danych do zapisu");
            alertPustaLista.showAndWait();
        }
    }

    private void czyscPokoje() {
        rectnagle1.setVisible(false);
        rectnagle2.setVisible(false);
        rectnagle3.setVisible(false);
        rectnagle4.setVisible(false);
        rectnagle5.setVisible(false);
        rectnagle6.setVisible(false);
        rectnagle7.setVisible(false);
        rectnagle8.setVisible(false);
        rectnagle9.setVisible(false);
        rectnagle10.setVisible(false);
        rectnagle11.setVisible(false);
        rectnagle12.setVisible(false);
        rectnagle13.setVisible(false);
        rectnagle14.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
        label8.setVisible(false);
        label9.setVisible(false);
        label10.setVisible(false);
        label11.setVisible(false);
        label12.setVisible(false);
        label13.setVisible(false);
        label14.setVisible(false);
    }

    @FXML
    public void zamknijProgram() {
        primaryStage.close();
    }

    @FXML
    public void zerujTablice() {
        resetField();
        personList.remove(0, personList.size());
        mainTableView.refresh();
    }

    @FXML
    public void zapiszRaportDoPliku(){
        zapiszPlik(Model.generujRaport(personList));
    }

    @FXML
    public void generujRaportDoTabeli(){
        resetField();
        ObservableList<Person> copy = Model.generujRaport(personList);
        personList.remove(0, personList.size());
        personList.setAll(copy);
//        personList = FXCollections.observableArrayList(copy);
        mainTableView.refresh();
    }
}

