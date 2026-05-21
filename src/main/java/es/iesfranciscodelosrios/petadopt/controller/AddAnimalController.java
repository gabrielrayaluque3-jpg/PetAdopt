package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AnimalDAO;
import es.iesfranciscodelosrios.petadopt.model.Animal;
import es.iesfranciscodelosrios.petadopt.model.Especie;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddAnimalController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtRaza;
    @FXML private TextField txtEdad;
    @FXML private ComboBox<Especie> cbEspecie;

    @FXML
    public void initialize() {
        // Rellenamos el ComboBox con los valores del Enum Especie
        cbEspecie.getItems().setAll(Especie.values());
    }

    @FXML
    private void btnGuardar() {
        try {
            String nombre = txtNombre.getText();
            Especie especie = cbEspecie.getValue();
            String raza = txtRaza.getText();
            int edad = Integer.parseInt(txtEdad.getText());

            if (nombre.isEmpty() || especie == null) {
                mostrarAlerta("Error", "Nombre y Especie son obligatorios.");
                return;
            }

            Animal nuevo = new Animal(0, nombre, especie, raza, edad, null, null);

            if (AnimalDAO.addAnimal(nuevo)) {
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo insertar en la base de datos.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La edad debe ser un número.");
        }
    }

    @FXML
    private void btnCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}