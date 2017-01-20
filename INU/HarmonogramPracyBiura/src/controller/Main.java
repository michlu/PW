package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/MainWindowView.fxml"));
        BorderPane root = loader.load();
        primaryStage.setTitle("Harmonogram Pracy Biura");
        primaryStage.setMinWidth(610.0);
        primaryStage.setMinHeight(780.0);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/app.css").toExternalForm());
        primaryStage.setScene(scene);

        MainWindowController mainWindowController = loader.getController();
        mainWindowController.setPrimaryStage(primaryStage);

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
