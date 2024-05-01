package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.obj.Penjualan;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RekapProdukController implements Initializable {
    @FXML 
    private LeftSidebar sidebar;

    @FXML
    private DatePicker tanggalDatePicker;

    @FXML
    private TableView<Penjualan> invoiceTable;

    @FXML
    private TableColumn<Void, Void> noCol, tanggalCol, namaProdukCol, jumlahCol;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("rekap_produk");
        tanggalDatePicker.setValue(LocalDate.now());

        this.setColumnWidth();
    }

    public void setColumnWidth() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.1));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        namaProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        jumlahCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
    }

}
