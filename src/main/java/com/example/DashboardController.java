package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class DashboardController implements Initializable {
    @FXML private ImageView logo;    
    
    @FXML private LeftSidebar sidebar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.sidebar.setActiveClass("dashboard");
    }    
}
