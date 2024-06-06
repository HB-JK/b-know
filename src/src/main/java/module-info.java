module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens com.example to javafx.fxml;
    opens com.example.components to javafx.fxml;
    opens com.example.components.Alert to javafx.fxml;
    opens com.example.components.Modal to javafx.fxml;
    opens com.example.database;
    opens com.example.model;
    exports com.example;
}
