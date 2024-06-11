package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.helpers.DateHelper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    
    @FXML private ScrollPane scrollpane;
    
    @FXML private Label today_date;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("cashflow");
        tanggalDatePicker.setValue(LocalDate.now());
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
    }

    public void setupColumn() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.1));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
        modalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
        pendapatanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
        pendapatanBersihCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.225));
    }
}
