package com.example.components.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.helpers.InputTypeHelper;
import com.example.modal.BaseModalController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SuccessAlert extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;

    private final StringProperty alert_msg = new SimpleStringProperty();
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button;
    @FXML ImageView icon;
    @FXML Label alert_message;
    
    public SuccessAlert() {
        this.title = "Success Alert";
    }

    public SuccessAlert(String title, double width, double height, Node parent_source, String alert_msg) throws IOException {
        super(title, width, height, parent_source, "component/alert/success_alert.fxml");
        
        //Agar dapat memberi value ke variabel alert_msg dalam fxml, maka harus melalui controller
        SuccessAlert current = super.loader.getController();
        current.setAlertMsg(alert_msg);
        this.title = title;
    }
    
    public SuccessAlert(String title, Node parent_source, String alert_msg) throws IOException {
        super(title, parent_source, "component/alert/success_alert.fxml");
        
        //Agar dapat memberi value ke variabel alert_msg dalam fxml, maka harus melalui controller
        SuccessAlert current = super.loader.getController();
        current.setAlertMsg(alert_msg);
        this.title = title;
    }
    
    public void setAlertMsg(String text) {
        alert_msg.setValue(text);
    }
    
    public final String getAlertMsg() {
        return alert_msg.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.icon.setImage(new Image(getClass().getResource("/assets/img/icons/success_icon.png").toExternalForm()));
        
        this.alert_message.textProperty().bind(alert_msg);
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
}
