package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AdoptanteDAO;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;
import es.iesfranciscodelosrios.petadopt.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddAdopcionController {

    @FXML private ListView<Adoptante> lvAdoptantes;
    @FXML private ListView<Animal> lvAnimales;
    @FXML private TextField txtFiltroAdoptante;
    @FXML private TextField txtFiltroAnimal;

    private ObservableList<Adoptante> obsAdoptantes = FXCollections.observableArrayList();
    private ObservableList<Animal> obsAnimales = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarListas();
        cargarDatos();
    }

    private void configurarListas() {
        lvAdoptantes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Adoptante adopt, boolean empty) {
                super.updateItem(adopt, empty);
                setText((empty || adopt == null) ? null : adopt.getNombre() + " (" + adopt.getDni() + ")");
            }
        });

        lvAnimales.setCellFactory(param -> new ListCell<>() {
            @Override protected void updateItem(Animal animal, boolean empty) {
                super.updateItem(animal, empty);
                setText((empty || animal == null) ? null : animal.getNombre() + " - " + animal.getRaza());
            }
        });
    }

    private void cargarDatos() {
        obsAdoptantes.setAll(AdoptanteDAO.findAllAdoptantes());
        obsAnimales.setAll(AdoptanteDAO.findAnimalesDisponibles());

        FilteredList<Adoptante> filteredAdoptantes = new FilteredList<>(obsAdoptantes, p -> true);
        txtFiltroAdoptante.textProperty().addListener((obs, old, newValue) -> {
            filteredAdoptantes.setPredicate(a -> newValue == null || newValue.isEmpty() || a.getNombre().toLowerCase().contains(newValue.toLowerCase()));
        });
        lvAdoptantes.setItems(filteredAdoptantes);

        FilteredList<Animal> filteredAnimales = new FilteredList<>(obsAnimales, p -> true);
        txtFiltroAnimal.textProperty().addListener((obs, old, newValue) -> {
            filteredAnimales.setPredicate(a -> newValue == null || newValue.isEmpty() || a.getNombre().toLowerCase().contains(newValue.toLowerCase()));
        });
        lvAnimales.setItems(filteredAnimales);
    }

    @FXML
    private void btnVincular() {
        Adoptante ad = lvAdoptantes.getSelectionModel().getSelectedItem();
        Animal an = lvAnimales.getSelectionModel().getSelectedItem();

        if (ad == null || an == null) {
            mostrarAlerta("Selección incompleta", "Debes seleccionar una persona y un animal.");
            return;
        }

        if (AdoptanteDAO.vincularAdopcion(an.getId(), ad.getId())) { //Actualiza el id_adoptante de el aniaml
            cerrarVentana();
        }
    }

    @FXML private void btnCancelar() { cerrarVentana(); }

    private void cerrarVentana() {
        ((Stage) lvAdoptantes.getScene().getWindow()).close();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}