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
    protected Scene scene;
    protected FXMLLoader loader;
    protected double width = 300.0, height = 250.0;
    
    public BaseModalController() {
        
    }
    
    public BaseModalController(String title, double width, double height, Node parent_source, String file_source) throws IOException {
        loader = new FXMLLoader(App.class.getResource(file_source));
        Parent root = loader.load();
        this.height = height;
        this.width = width;
        
        this.scene = new Scene(root, this.width, this.height); 
        scene.getStylesheets().add(getClass().getResource("/assets/css/style.css").toExternalForm());
        
        setScene(scene);
        setTitle(title);
        initModality(Modality.WINDOW_MODAL);
        initOwner(parent_source.getScene().getWindow());
    }
    
    public BaseModalController(String title, Node parent_source, String file_source) throws IOException {
        loader = new FXMLLoader(App.class.getResource(file_source));
        Parent root = loader.load();
        
        this.scene = new Scene(root, this.width, this.height); 
        scene.getStylesheets().add(getClass().getResource("/assets/css/style.css").toExternalForm());
        
        setScene(scene);
        setTitle(title);
        initModality(Modality.WINDOW_MODAL);
        initOwner(parent_source.getScene().getWindow());
    }
}
