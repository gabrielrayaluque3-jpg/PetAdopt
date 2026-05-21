package es.iesfranciscodelosrios.petadopt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Ejecutable extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource("MenuPrincipal.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Panel de Control");
        stage.setScene(scene);
        stage.show();
    }
}