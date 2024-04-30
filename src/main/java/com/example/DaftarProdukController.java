package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DaftarProdukController implements Initializable {
    @FXML private LeftSidebar sidebar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("daftar_produk");
    }
    
}
