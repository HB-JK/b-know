package com.example;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.obj.Penjualan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PenjualanController implements Initializable {
    @FXML
    private LeftSidebar sidebar;

    @FXML // fx:id="fruitCombo"
    private ComboBox<String> fruitCombo;

    @FXML
    private DatePicker tanggalDatePicker;
    
    @FXML
    private TableView<Penjualan> tablePenjualan;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("penjualan");
        fruitCombo.getItems().setAll("Pangsit Pedas", "Pangsit Manis", "Pangsit Goreng");
        tanggalDatePicker.setValue(LocalDate.now());
    }
}
