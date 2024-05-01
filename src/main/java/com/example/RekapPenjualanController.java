package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class RekapPenjualanController implements Initializable {
    @FXML
    private LeftSidebar sidebar;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("rekap_penjualan");
    }

}
