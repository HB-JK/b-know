package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.database.ConnectDatabase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    public void verifyAccount() throws IOException {
        ConnectDatabase connection = new ConnectDatabase();
        App.setRoot("dashboard");
        System.out.println("Email: " + email.getText() + ", Password: " + password.getText() );
    }
}
