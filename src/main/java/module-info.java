module es.iesfranciscodelosrios.petadopt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires javafx.graphics;

    opens es.iesfranciscodelosrios.petadopt.dataAccess to java.xml.bind;
    opens es.iesfranciscodelosrios.petadopt to javafx.fxml;
    exports es.iesfranciscodelosrios.petadopt;
    exports es.iesfranciscodelosrios.petadopt.controller;
    opens es.iesfranciscodelosrios.petadopt.controller to javafx.fxml;
    opens es.iesfranciscodelosrios.petadopt.model to javafx.base;
}