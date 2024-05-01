package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
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

    // @FXML
    // private TableColumn produkTableColumn;

    // @FXML
    // private DatePicker tanggalDatePicker;
    
    // @FXML
    // public void bukaKasirButtonAction(ActionEvent e) {
    //     // Implement logic to open the kasir window or perform kasir-related actions
    //     System.out.println("Buka Kasir button clicked");
    // }
    
    // @FXML
    // public void tambahButtonAction(ActionEvent e) {
    //     // Implement logic to add the product and date to the system
    //     System.out.println("Tambah button clicked");
    // }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("penjualan");
        fruitCombo.getItems().setAll("Pangsit Pedas", "Pangsit Manis", "Pangsit Goreng");
        tanggalDatePicker.setValue(LocalDate.now());
    }
}
