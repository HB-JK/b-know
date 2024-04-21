package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable{
    
    @FXML
    private AnchorPane left_screen, right_screen;
    
    @FXML
    private ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.left_screen.setStyle("-fx-background-image: url(" + getClass().getResource("/assets/img/login-background.jpg").toExternalForm() +")");
        
        this.logo.setImage(new Image(getClass().getResource("/assets/img/logo.png").toExternalForm()));
    }
}
