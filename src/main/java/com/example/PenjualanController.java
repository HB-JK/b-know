package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.components.Modal.CashierModalController;
import com.example.components.Modal.TambahPenjualanModalController;
import com.example.helpers.DateHelper;
import com.example.helpers.FormatHelper;
import com.example.model.Modal;
import com.example.model.Penjualan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class PenjualanController implements Initializable {
    @FXML private LeftSidebar sidebar; // self made component
    @FXML private ComboBox<String> fruitCombo; // comboxbox element which data is list of sring with fx:id fruitombo
    @FXML private DatePicker tanggalDatePicker;
    @FXML private TableView<Penjualan> invoiceTable;
    @FXML private ScrollPane scrollpane;
    @FXML private Label today_date, modal_label;
    @FXML private HBox tombol;
    @FXML private Button open_cashier_button, close_cashier_button, add_penjualan_button;
    @FXML TableColumn<Penjualan, String> tanggalCol, noFakturCol, namaPembeliCol, jumlahItemCol, totalHargaCol;
    
    public ObservableList<Penjualan> initialData = FXCollections.observableArrayList(new Penjualan().getData());
    private Modal modal = new Modal();

    @Override
    public void initialize(URL arg, ResourceBundle arg1) {
        sidebar.setActiveClass("penjualan");
        fruitCombo.getItems().setAll("Pangsit Pedas", "Pangsit Manis", "Pangsit Goreng");
        tanggalDatePicker.setValue(LocalDate.now());
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setupColumn();
        today_date.setText(new DateHelper().getTodayDate());
        
        modal.getTodayCashier();
        if(modal.getId() != null) {
            this.updateModal(String.valueOf(modal.getJumlahModalMasuk()));
            invoiceTable.setItems(initialData);
        } else {
            this.updateState(false);
        }
    }

    public void setupColumn() {
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.25));
        noFakturCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.150));
        namaPembeliCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.260));
        jumlahItemCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.140));
        totalHargaCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        
        //set property of each column to get data from the model Penjualan
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Penjualan, String>("createdAt"));
        noFakturCol.setCellValueFactory(new PropertyValueFactory<Penjualan, String>("nomorFaktur"));
        namaPembeliCol.setCellValueFactory(new PropertyValueFactory<Penjualan, String>("namaCustomer"));
        jumlahItemCol.setCellValueFactory(new PropertyValueFactory<Penjualan, String>("jumlahProduk"));
        totalHargaCol.setCellValueFactory(new PropertyValueFactory<Penjualan, String>("totalHarga"));
    }

    public void updateModal(String modal) {
        modal_label.setText(
            new FormatHelper().convertToRupiah(Integer.parseInt(modal))
        );
        this.updateState(true);
    }

    public void updateState(boolean state) {
        if(!this.tombol.getChildren().contains(close_cashier_button) && state) {
            this.tombol.getChildren().add(close_cashier_button);
        }
        
        if(!this.tombol.getChildren().contains(add_penjualan_button) && state) {
            this.tombol.getChildren().add(add_penjualan_button);
        }
        
        if(state) {
            this.tombol.getChildren().remove(open_cashier_button);
        } else {
            this.tombol.getChildren().remove(close_cashier_button);
            this.tombol.getChildren().remove(add_penjualan_button);
        }
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


