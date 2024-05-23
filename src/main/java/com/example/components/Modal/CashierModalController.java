package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.helpers.InputTypeHelper;
import com.example.model.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CashierModalController extends BaseModalController implements Initializable{
    //Cashier modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    
    //Cashier modal fxml element
    @FXML private Button close_button;
    @FXML private TextField modal;
    
    public CashierModalController() {
        this.title = "Input Modal";
    }

    public CashierModalController(String title, double width, double height, Node parent_source) throws IOException {
        super(title, width, height, parent_source, "modal/cashier_modal.fxml");
        
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input_helper.setToInt(modal);
    }
    
    public void openModal() {
        this.showAndWait();
    }
    
    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void save(ActionEvent e) throws IOException {
        Modal modal = new Modal(this.modal.getText(), "buka");
        if(modal.save()) {
            SuccessAlert success_alert = new SuccessAlert("Buka Kasir", (Node) e.getSource(), "Kasir sudah dibuka dengan modal " + this.modal.getText());
            success_alert.openModal();
        } else {
            ErrorAlert error_alert = new ErrorAlert("Buka Kasir", (Node) e.getSource(), "Kasir belum berhasil dibuka");
            error_alert.openModal();
        }
        
        this.closeModal();
    }
}
