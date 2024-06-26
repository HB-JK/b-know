package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.components.Modal.TambahProdukModalController;
import com.example.model.Produk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class DaftarProdukController implements Initializable {
    @FXML private LeftSidebar sidebar;

    @FXML private TableView<Produk> produkTable;

    @FXML private TableColumn<Produk, String> noCol, tanggalCol, kodeProdukCol, namaProdukCol, jumlahStokCol, satuanCol, hargaProdukCol, aksiCol;
    
    @FXML private ScrollPane scrollpane;
    
    public ObservableList<Produk> initialData = FXCollections.observableArrayList(new Produk().getData());
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("daftar_produk");
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        this.setupRow();
        this.setupColumn();
        produkTable.setItems(initialData);
    }
    
    public void setupColumn() {        
        //set column width to fit with tableview
        tanggalCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.15));
        kodeProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.17));
        namaProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.25));
        jumlahStokCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.10));
        satuanCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.12));
        hargaProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.20));
        
        //set property of each column to get data from the model Produk
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("createdAt"));
        kodeProdukCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("kodeProduk"));
        namaProdukCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("nama"));
        jumlahStokCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("sisaStok"));
        satuanCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("satuan"));
        hargaProdukCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("hargaProduk"));
    }
    
    public void setupRow() {
        produkTable.setRowFactory(tv -> {
            TableRow<Produk> row = new TableRow<>();
            
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Produk rowData = row.getItem();
                    
                    try {
                        TambahProdukModalController tambah_produk_modal = new TambahProdukModalController("Edit Produk", 450, 250, (Node) event.getSource(), this, rowData);
                        tambah_produk_modal.openModal();
                    } catch (IOException el) {
                        el.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    public void updateTable(Produk produk) {
        initialData.add(produk);
        
        produkTable.refresh();
    }
    
    public void updateTable() {
        produkTable.refresh();
    }
    
    @FXML
    public void openTambahProdukModal(ActionEvent e) {
        try {
            TambahProdukModalController tambah_produk_modal = new TambahProdukModalController("Tambah Produk", 450, 250, (Node) e.getSource(), this);
            tambah_produk_modal.openModal();
        } catch (IOException el) {
            el.printStackTrace();
        }
    }
}
