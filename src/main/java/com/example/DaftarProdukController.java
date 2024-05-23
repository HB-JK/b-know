package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.components.Modal.TambahProdukModalController;
import com.example.model.Penjualan;
import com.example.model.Produk;

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
    private TableView<Produk> produkTable;

    @FXML
    private TableColumn<Produk, String> noCol, tanggalCol, kodeProdukCol, namaProdukCol, jumlahStokCol, satuanCol, hargaProdukCol, aksiCol;
    
    @FXML private ScrollPane scrollpane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("daftar_produk");
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        this.setColumnWidth();
        // produkTable.getItems().add
    }
    
    public void setColumnWidth() {
        noCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.05));
        tanggalCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.100));
        kodeProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.100));
        namaProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.190));
        jumlahStokCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.100));
        satuanCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.120));
        hargaProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.150));
        aksiCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.190));
    }
    
    @FXML
    public void openTambahProdukModal(ActionEvent e) {
        try {
            TambahProdukModalController tambah_produk_modal = new TambahProdukModalController("Tambah Produk", 450, 300, (Node) e.getSource());
            tambah_produk_modal.openModal();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
