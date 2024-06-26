package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.helpers.DateHelper;
import com.example.helpers.FormatHelper;
import com.example.model.Modal;
import com.example.model.Penjualan;

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

public class RekapPenjualanController implements Initializable {
    @FXML
    private LeftSidebar sidebar;
    @FXML
    private DatePicker tanggalAwalPicker, tanggalAkhirPicker;
    @FXML
    private TableView<Penjualan> invoiceTable;
    @FXML
    private TableColumn<Penjualan, String> noCol, tanggalCol, jumlahProdukCol, totalPenjualanCol;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label today_date, total_penjualan;
    @FXML
    private Button filter_button;

    private ObservableList<Penjualan> initialData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sidebar.setActiveClass("rekap_penjualan");
        tanggalAwalPicker.setValue(LocalDate.now().withDayOfMonth(1));
        tanggalAkhirPicker.setValue(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));

        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
        invoiceTable.setItems(initialData);
        filterData();
    }

    public void setupColumn() {
        noCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        String.valueOf(invoiceTable.getItems().indexOf(data.getValue()) + 1)));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        jumlahProdukCol.setCellValueFactory(new PropertyValueFactory<>("jumlahProduk"));
        totalPenjualanCol.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.1));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        jumlahProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        totalPenjualanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
    }

    public void filterData() {        
        initialData.setAll(new Penjualan().getData(tanggalAwalPicker.getValue().toString(), tanggalAkhirPicker.getValue().toString()));
        
        total_penjualan.setText(new FormatHelper().convertToRupiah(new Penjualan().getTotalPenjualan(tanggalAwalPicker.getValue().toString(),tanggalAkhirPicker.getValue().toString())));
    }
    
    @FXML
    public void Filter(ActionEvent e ) {
        filterData();
    }
}

