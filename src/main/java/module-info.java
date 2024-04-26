module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example to javafx.fxml;
    opens com.example.components to javafx.fxml;
    exports com.example;
}
