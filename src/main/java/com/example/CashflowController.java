package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.helpers.DateHelper;
import com.example.model.Modal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CashflowController implements Initializable {
    @FXML private LeftSidebar sidebar;
    @FXML private DatePicker tanggalDatePicker;
    @FXML private TableView<Modal> invoiceTable;
    @FXML private TableColumn<Modal, String> tanggalCol, modalCol, pendapatanCol, pendapatanBersihCol;
    @FXML private ScrollPane scrollpane;
    @FXML private Label today_date;
    
    public ObservableList<Modal> initialData = FXCollections.observableArrayList(new Modal().getData());
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("cashflow");
        tanggalDatePicker.setValue(LocalDate.now());
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
        this.invoiceTable.setItems(initialData);
    }

    public void setupColumn() {
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.25));
        modalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.25));
        pendapatanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.25));
        pendapatanBersihCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.25));
        
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("createdAt"));
        modalCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("jumlahModalMasuk"));
        pendapatanCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("jumlahPenarikanModal"));
        pendapatanBersihCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("profit"));
    }
}
