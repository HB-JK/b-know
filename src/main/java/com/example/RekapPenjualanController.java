package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.helpers.DateHelper;
import com.example.model.Penjualan;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RekapPenjualanController implements Initializable {
    @FXML
    private LeftSidebar sidebar;

    @FXML
    private DatePicker tanggalDatePicker;

    @FXML
    private DatePicker tanggaAwallDatePicker;

    @FXML
    private DatePicker tanggaAkhirlDatePicker;

    @FXML
    private TableView<Penjualan> invoiceTable;

    @FXML
    private TableColumn<Void, Void> noCol, tanggalCol, jumlahProdukCol, totalPenjualanCol;
    
    @FXML private ScrollPane scrollpane;
    
    @FXML private Label today_date;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("rekap_penjualan");
        // tanggalAwalDatePicker.setValue(LocalDate.now());
        // tanggalAkhirDatePicker.setValue(LocalDate.now());

        DatePicker tanggalAwalDatePicker = new DatePicker();
        tanggalAwalDatePicker.setValue(LocalDate.now());  
        tanggalAwalDatePicker.setId("tanggalAwalDatePicker"); 

        DatePicker tanggalAkhirDatePicker = new DatePicker();
        tanggalAkhirDatePicker.setValue(LocalDate.now());  
        tanggalAkhirDatePicker.setId("tanggalAkhirDatePicker");  

        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
    }

    public void setupColumn() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.1));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        jumlahProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        totalPenjualanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
    }
}
