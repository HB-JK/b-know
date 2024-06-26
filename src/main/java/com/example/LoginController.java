package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import com.example.components.Alert.ErrorAlert;
import com.example.enums.ErrorLevel;
import com.example.helpers.UserHelper;
import com.example.model.Admin;
import com.example.model.LogError;

public class LoginController implements Initializable{
    
    @FXML
    private AnchorPane left_screen, right_screen;
    
    @FXML
    private ImageView logo;
    
    @FXML
    private TextField email;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private Button login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.left_screen.setStyle("-fx-background-image: url(" + getClass().getResource("/assets/img/login-background.jpg").toExternalForm() +")");
        
        this.logo.setImage(new Image(getClass().getResource("/assets/img/logo.png").toExternalForm()));
    }
    
    @FXML
    public void verifyAccount(ActionEvent e) throws IOException {
        try{
            Admin admin = new Admin();
            
            // verifikasi akun melalui model admin dengan mengirimkan email, dan password
            if(admin.verifyAccount((String) email.getText(), (String) password.getText())) {
                if(new UserHelper().saveAdminToJson(admin)) {
                    App.setRoot("dashboard");
                    return;
                }
                
                throw new Exception("Folder config kamu terhapus, silahkan buat ulang folder config");
            } else {
                ErrorAlert alert = new ErrorAlert("Login", (Node) e.getSource(), "Silahkan input ulang email dan password yang benar");
                alert.openModal();
            }
        } catch (Exception exception) {
            ErrorAlert alert = new ErrorAlert("Login", (Node) e.getSource(), exception.getMessage());
            alert.openModal();
            
            new LogError(ErrorLevel.CRITICAL, exception.getMessage());
        }
    }
}
