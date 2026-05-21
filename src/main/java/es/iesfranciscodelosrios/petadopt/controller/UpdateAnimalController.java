
package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AnimalDAO;
import es.iesfranciscodelosrios.petadopt.dao.VoluntarioDAO;
import es.iesfranciscodelosrios.petadopt.model.Animal;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;
import es.iesfranciscodelosrios.petadopt.model.Especie;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.stage.Stage;

public class UpdateAnimalController {

    @FXML private TextField txtNombre;
    @FXML private ComboBox<Especie> cbEspecie;
    @FXML private TextField txtRaza;
    @FXML private TextField txtEdad;
    @FXML private ComboBox<Voluntario> cbVoluntario;
    private Animal animalAEditar;
    private Adoptante adoptanteActual;

    @FXML
    public void initialize() {
        cbEspecie.setItems(FXCollections.observableArrayList(Especie.values()));
        cbVoluntario.setItems(FXCollections.observableArrayList(VoluntarioDAO.findAllVoluntarios()));
        cbVoluntario.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Voluntario item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre() + " (" + item.getEspecialidad() + ")");
            }
        });
        cbVoluntario.setButtonCell(cbVoluntario.getCellFactory().call(null));
    }

    /**
     * Recibe el animal seleccionado desde la pantalla principal y rellena el formulario.
     */
    public void setAnimal(Animal animal) {
        if (animal != null) {
            this.animalAEditar = animal;
            this.adoptanteActual = animal.getAdoptante();
            txtNombre.setText(animal.getNombre());
            cbEspecie.setValue(animal.getEspecie());
            txtRaza.setText(animal.getRaza());
            txtEdad.setText(String.valueOf(animal.getEdad()));

            if (animal.getVoluntario() != null) {
                for (Voluntario v : cbVoluntario.getItems()) {
                    if (v.getId() == animal.getVoluntario().getId()) {
                        cbVoluntario.setValue(v);
                        break;
                    }
                }
            }
        }
    }

    @FXML
    private void btnGuardar() {
        if (txtNombre.getText().trim().isEmpty() || cbEspecie.getValue() == null || txtRaza.getText().trim().isEmpty()) {
            mostrarAlerta("Campos vacíos", "El nombre, la especie y la raza son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        int edad = 0;
        try {
            if (!txtEdad.getText().trim().isEmpty()) {
                edad = Integer.parseInt(txtEdad.getText().trim());
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Edad errónea", "La edad debe ser un número entero válido.", Alert.AlertType.WARNING);
            return;
        }

        animalAEditar.setNombre(txtNombre.getText().trim());
        animalAEditar.setEspecie(cbEspecie.getValue());
        animalAEditar.setRaza(txtRaza.getText().trim());
        animalAEditar.setEdad(edad);
        animalAEditar.setVoluntario(cbVoluntario.getValue());

        if (adoptanteActual != null && adoptanteActual.getId() <= 0) {
            animalAEditar.setAdoptante(null);
        } else {
            animalAEditar.setAdoptante(adoptanteActual);
        }

        if (AnimalDAO.updateAnimal(animalAEditar)) {
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "No se pudieron guardar los cambios en la base de datos.", Alert.AlertType.ERROR);
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

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}