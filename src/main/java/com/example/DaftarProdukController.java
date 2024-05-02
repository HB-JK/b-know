package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.modal.TambahProdukModalController;
import com.example.obj.Penjualan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class DaftarProdukController implements Initializable {
    @FXML private LeftSidebar sidebar;

    @FXML
    private TableView<Penjualan> invoiceTable;

    @FXML
    private TableColumn<Void, Void> noCol, tanggalCol, kodeProdukCol, namaProdukCol, jumlahStokCol, satuanCol, hargaProdukCol, aksiCol;
    
    @FXML private ScrollPane scrollpane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("daftar_produk");
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        this.setColumnWidth();
    }
    
    public void setColumnWidth() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.05));
        tanggalCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.100));
        kodeProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.100));
        namaProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.190));
        jumlahStokCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.100));
        satuanCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.120));
        hargaProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.150));
        aksiCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.190));
    }
    
    @FXML
    public void openTambahProdukModal(ActionEvent e) {
        TambahProdukModalController tambah_produk_modal;
        try {
            tambah_produk_modal = new TambahProdukModalController("Tambah Produk", 450, 300, (Node) e.getSource());
            tambah_produk_modal.openModal();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
