package com.example.modal;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BaseModalController extends Stage {
    public BaseModalController() {
        
    }
    
    public BaseModalController(String title, double width, double height, Node parent_source, String file_source) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource(file_source));
        Scene scene = new Scene(root, width, height); 
        scene.getStylesheets().add(getClass().getResource("/assets/css/style.css").toExternalForm());
        
        setScene(scene);
        setTitle(title);
        initModality(Modality.WINDOW_MODAL);
        initOwner(parent_source.getScene().getWindow());
    }
}
