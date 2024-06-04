package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.components.Modal.CashierModalController;
import com.example.components.Modal.TambahPenjualanModalController;
import com.example.helpers.DateHelper;
import com.example.model.Penjualan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class PenjualanController implements Initializable {
    @FXML
    private LeftSidebar sidebar;

    @FXML // fx:id="fruitCombo"
    private ComboBox<String> fruitCombo;

    @FXML
    private DatePicker tanggalDatePicker;

    @FXML
    private TableView<Penjualan> invoiceTable;

    @FXML
    TableColumn<Void, Void> noCol, tanggalCol, noFakturCol, namaPembeliCol, jumlahItemCol, totalHargaCol, aksiCol;
    
    @FXML private ScrollPane scrollpane;
    
    @FXML private Label today_date, modal_label;

    @Override
    public void initialize(URL arg, ResourceBundle arg1) {
        sidebar.setActiveClass("penjualan");
        fruitCombo.getItems().setAll("Pangsit Pedas", "Pangsit Manis", "Pangsit Goreng");
        tanggalDatePicker.setValue(LocalDate.now());
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
    }

    public void setupColumn() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.05));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.120));
        noFakturCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.150));
        namaPembeliCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.180));
        jumlahItemCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.140));
        totalHargaCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.180));
        aksiCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.180));
    }

    public void updateModal(String modal) {
        modal_label.setText(modal);
    }

    @FXML
    public void openCashierModal(ActionEvent e) {
        try {
            CashierModalController cashier_modal = new CashierModalController("Buka Kasir", 550, 350, (Node) e.getSource(), this);
            cashier_modal.openModal();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void closeCashier(ActionEvent e) {
        // try {
            
        // }
    }
    
    @FXML
    public void openTambahPenjualanModal(ActionEvent e) {
        try {
            TambahPenjualanModalController tambah_penjualan_modal = new TambahPenjualanModalController("Tambah Penjualan", 650, 500, (Node) e.getSource());
            tambah_penjualan_modal.openModal();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}


