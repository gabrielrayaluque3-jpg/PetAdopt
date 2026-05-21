package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AdoptanteDAO;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAdoptanteController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private Button btnCancelar;

    @FXML
    private void btnGuardar() {
        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor, rellene todos los campos del formulario.", Alert.AlertType.WARNING);
            return;
        }

        String dniIntroducido = txtDni.getText().trim();

        if (!Utils.validarDNI(dniIntroducido)) {
            mostrarAlerta("DNI Incorrecto", "El DNI introducido no es válido.", Alert.AlertType.WARNING);
            return;
        }

        Adoptante nuevoAdoptante = new Adoptante();
        nuevoAdoptante.setDni(dni);
        nuevoAdoptante.setNombre(nombre);
        nuevoAdoptante.setTelefono(telefono);
        nuevoAdoptante.setEmail(email);

        try {
            boolean insertado = AdoptanteDAO.addAdoptante(nuevoAdoptante);
            if (insertado) {
                mostrarAlerta("Éxito", "El adoptante se ha registrado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar el registro en la base de datos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("¡¡¡ERRORR!!!", "Ocurrió un error al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void btnCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}