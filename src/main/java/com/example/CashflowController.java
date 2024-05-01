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

public class CashflowController implements Initializable {
    @FXML
    private LeftSidebar sidebar;

    @FXML
    private DatePicker tanggalDatePicker;

    @FXML
    private TableView<String> invoiceTable;

    @FXML
    private TableColumn<Void, Void> noCol, tanggalCol, modalCol, pendapatanCol, pendapatanBersihCol;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("cashflow");
        tanggalDatePicker.setValue(LocalDate.now());

        this.setColumnWidth();
    }

    public void setColumnWidth() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.1));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
        modalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
        pendapatanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
        pendapatanBersihCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
    }
}
