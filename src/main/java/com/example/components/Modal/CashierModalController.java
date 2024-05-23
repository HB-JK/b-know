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

    public void updateState() {
        this.modal.setText("Tutup kasir");
    }
    
    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void openKasir(ActionEvent e) throws IOException {
        if (this.modal.getText().isEmpty()) {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Harap masukkan modal terlebih dahulu");
            errorAlert.openModal();
            return;
        }

        Modal modal = new Modal(this.modal.getText(), "Buka");
        if (modal.save()) {
            SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Modal ditambahkan");
            successAlert.openModal();
            this.closeModal();
            this.updateState();

        } else {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Modal gagal ditambahkan");
            errorAlert.openModal();
        }
    }

    

}
