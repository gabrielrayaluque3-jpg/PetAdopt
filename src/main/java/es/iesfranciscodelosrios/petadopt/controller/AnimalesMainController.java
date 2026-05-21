package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.Ejecutable;
import es.iesfranciscodelosrios.petadopt.dao.AnimalDAO;
import es.iesfranciscodelosrios.petadopt.model.Animal;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AnimalesMainController {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Animal> tvAnimales;
    @FXML private TableColumn<Animal, String> colNombre;
    @FXML private TableColumn<Animal, String> colEspecie;
    @FXML private TableColumn<Animal, String> colRaza;
    @FXML private TableColumn<Animal, Integer> colEdad;
    @FXML private TableColumn<Animal, String> colVoluntario;
    @FXML private TableColumn<Animal, String> colAdoptante;

    private final ObservableList<Animal> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEspecie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecie().name()));
        colRaza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaza()));
        colEdad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());

        colVoluntario.setCellValueFactory(cellData -> {
            Animal animal = cellData.getValue();
            if (animal.getVoluntario() != null) {
                return new SimpleStringProperty(animal.getVoluntario().getNombre());
            }
            return new SimpleStringProperty("Sin asignar");
        });

        colAdoptante.setCellValueFactory(cellData -> {
            Animal animal = cellData.getValue();
            if (animal.getAdoptante() != null) {
                return new SimpleStringProperty("Adoptado por: " + animal.getAdoptante().getNombre());
            }
            return new SimpleStringProperty("🟢 Disponible");
        });

        cargarAnimales();

        FilteredList<Animal> filteredData = new FilteredList<>(masterData, p -> true);
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(animal -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return animal.getNombre().toLowerCase().contains(lowerCaseFilter);
            });
        });

        tvAnimales.setItems(filteredData);
    }

    private void cargarAnimales() {
        masterData.clear();
        List<Animal> lista = AnimalDAO.findAllAnimales();
        if (lista != null) {
            masterData.addAll(lista);
        }
    }

    @FXML
    private void btnAddAnimal() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    Ejecutable.class.getResource("AddAnimal.fxml")
            );
            javafx.scene.Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Añadir Nuevo Animal");

            stage.setScene(new javafx.scene.Scene(root));
            stage.showAndWait();

            cargarAnimales();

        } catch (java.io.IOException e) {
            System.err.println("Error al cargar la ventana flotante: AddAnimal.fxml");
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana para añadir un animal.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnEditar() throws IOException {
        Animal seleccionado = tvAnimales.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource("UpdateAnimal.fxml"));
            Parent root = fxmlLoader.load();

            UpdateAnimalController controller = fxmlLoader.getController();
            controller.setAnimal(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Animal: " + seleccionado.getNombre());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarAnimales();
        } else {
            mostrarAlerta("Atención", "Por favor, selecciona un animal de la lista.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar() {
        Animal seleccionado = tvAnimales.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Eliminar Animal");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro de que desea eliminar a " + seleccionado.getNombre() + "? Esta acción no se puede deshacer.");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (AnimalDAO.deleteAnimal(seleccionado.getId())) {
                    cargarAnimales();
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el animal de la base de datos.", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Atención", "Por favor, seleccione un animal de la tabla para eliminarlo.", Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


    @FXML
    private void btnAnimales() {
        cargarAnimales();
    }

    @FXML
    private void btnVoluntarios(javafx.event.ActionEvent event) {
        Utils.irA(event, "VoluntariosMain.fxml", "Gestión de Voluntarios");
    }

    @FXML
    private void btnTareas(javafx.event.ActionEvent event) {
        Utils.irA(event, "TareasMain.fxml", "Gestión de Tareas");
    }

    @FXML
    private void btnAdoptantes(javafx.event.ActionEvent event) {
        Utils.irA(event, "AdoptantesMain.fxml", "Gestión de Adoptantes");
    }

    @FXML
    private void btnHistorial(javafx.event.ActionEvent event) {
        Utils.irA(event, "HistorialTareas.fxml", "Historial de Tareas");
    }

    @FXML
    private void btnMenuPrincipal(javafx.event.ActionEvent event) {
        Utils.irA(event, "MenuPrincipal.fxml", "Menú Principal");
    }

    @FXML
    private void btnSalir(javafx.event.ActionEvent event) {
        Utils.cerrar(event);
    }
}