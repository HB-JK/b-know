package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.helpers.DateHelper;
import com.example.helpers.FormatHelper;
import com.example.model.Modal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CashflowController implements Initializable {
    @FXML private LeftSidebar sidebar;
    @FXML private DatePicker tanggalAwalPicker, tanggalAkhirPicker;
    @FXML private TableView<Modal> invoiceTable;
    @FXML private TableColumn<Modal, String> tanggalCol, modalCol, pendapatanCol, nominalYangDiinputCol, pendapatanBersihCol;
    @FXML private ScrollPane scrollpane;
    @FXML private Label today_date, total_pendapatan;
    @FXML private Button filter_button;
    
    public ObservableList<Modal> initialData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("cashflow");
        tanggalAwalPicker.setValue(LocalDate.now().withDayOfMonth(1));
        tanggalAkhirPicker.setValue(LocalDate.now().withDayOfMonth(30));
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
        this.invoiceTable.setItems(initialData);
        filterData();
    }

    public void setupColumn() {
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        modalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        pendapatanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        nominalYangDiinputCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        pendapatanBersihCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("createdAt"));
        modalCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("jumlahModalMasuk"));
        pendapatanCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("jumlahPenarikanModal"));
        nominalYangDiinputCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("jumlahPendapatan"));
        pendapatanBersihCol.setCellValueFactory(new PropertyValueFactory<Modal, String>("profit"));
    }
    
    public void filterData() {
        initialData.setAll(new Modal().getData(String.valueOf(tanggalAwalPicker.getValue()), String.valueOf(tanggalAkhirPicker.getValue())));
        setTotalPendapatan();
    }
    
    public void setTotalPendapatan() {
        int total_bersih = 0;
        
        for(Modal modal: invoiceTable.getItems()) {
            total_bersih += modal.getJumlahPenarikanModal() - modal.getJumlahModalMasuk();
        }
        
        total_pendapatan.setText(new FormatHelper().convertToRupiah(total_bersih));
    }
    
    @FXML
    public void filter(ActionEvent e ) {
        filterData();
    }
}
