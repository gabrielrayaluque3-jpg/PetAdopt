package es.iesfranciscodelosrios.petadopt.utils;

import es.iesfranciscodelosrios.petadopt.Ejecutable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class Utils {

    public static void irA(ActionEvent event, String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Ejecutable.class.getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage;

            if (event.getSource() instanceof MenuItem) {
                MenuItem item = (MenuItem) event.getSource();
                stage = (Stage) item.getParentPopup().getOwnerWindow();
            } else {
                Node node = (Node) event.getSource();
                stage = (Stage) node.getScene().getWindow();
            }

            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la escena: " + fxml);
            e.printStackTrace();
        }
    }

    public static void cerrar(ActionEvent event) {
        Stage stage;
        if (event.getSource() instanceof MenuItem) {
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        } else {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }
        stage.close();
    }

    /**
     * Comprueba si un DNI introducido es valido (formato y letra matemática correcta).
     * @param dni Cadena de texto con el DNI a validar.
     * @return true si el DNI es correcto, false en caso contrario.
     */
    public static boolean validarDNI(String dni) {
        boolean valido = true;
        if (dni == null) valido=false;
        dni = dni.trim().toUpperCase();

        if (!dni.matches("^[0-9]{8}[A-Z]$")) {
            valido = false;
        }
        return valido;
    }
}