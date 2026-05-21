package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AdoptanteDAO;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateAdoptanteController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private Button btnCancelar;

    private Adoptante adoptanteActual;

    /**
     * Este método permite recibir el adoptante seleccionado en la tabla principal
     * y rellenar automáticamente los cuadros de texto.
     */
    public void setAdoptante(Adoptante adoptante) {
        this.adoptanteActual = adoptante;

        if (adoptante != null) {
            txtDni.setText(adoptante.getDni());
            txtNombre.setText(adoptante.getNombre());
            txtTelefono.setText(adoptante.getTelefono());
            txtEmail.setText(adoptante.getEmail());
        }
    }

    @FXML
    private void btnGuardar() {
        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }
        adoptanteActual.setDni(dni);
        adoptanteActual.setNombre(nombre);
        adoptanteActual.setTelefono(telefono);
        adoptanteActual.setEmail(email);

        try {
            boolean actualizado = AdoptanteDAO.updateAdoptante(adoptanteActual);

            if (actualizado) {
                mostrarAlerta("Actualizado", "Los datos se han modificado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el registro en la base de datos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error Crítico", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
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