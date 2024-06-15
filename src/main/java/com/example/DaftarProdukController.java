package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar;
import com.example.components.Alert.ConfirmAlert;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
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
    
    private boolean is_confirm = false;
    
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
        kodeProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.25));
        namaProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.25));
        satuanCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.25));
        hargaProdukCol.prefWidthProperty().bind(produkTable.widthProperty().multiply(0.25));
        
        //set property of each column to get data from the model Produk
        kodeProdukCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("kodeProduk"));
        namaProdukCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("nama"));
        satuanCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("satuan"));
        hargaProdukCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("hargaProduk"));
    }
    
    public void setupRow() {
        produkTable.setRowFactory(tv -> {
            TableRow<Produk> row = new TableRow<>();
            
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() ) {
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
    
    //callback function used for getting response from modal
    public void handleUserChoice(boolean state) {
        this.is_confirm = state;
    }
    
    public int getSelectedProdukIndex(Node source) throws IOException {
        TableView.TableViewSelectionModel<Produk> selectionModel = produkTable.getSelectionModel();
        if(selectionModel.isEmpty()) {
            ErrorAlert error_alert = new ErrorAlert("Menghapus data", source, "Gagal menghapus data, belum ada produk yang terpilih");
            error_alert.openModal();
            return -1;
        }
        
        return selectionModel.getFocusedIndex();
    }
    
    @FXML
    public void deleteData(ActionEvent e) throws IOException {
        int produk_index = getSelectedProdukIndex((Node) e.getSource());
        if(produk_index == -1) return;
        
        Produk produk = produkTable.getItems().get(produk_index);
        
        ConfirmAlert confirm_alert = new ConfirmAlert(this::handleUserChoice, "Konfirmasi menghapus data", (Node) e.getSource(), "Apakah yakin untuk menghapus " + produk.getNama() + " ?");
        confirm_alert.openModal();
        
        if(is_confirm) {
            if(produk.delete()) {
                SuccessAlert success_alert = new SuccessAlert("Menghapus data", (Node) e.getSource(), "Berhasil menghapus data " + produk.getNama());
                success_alert.openModal();
                produkTable.getItems().remove(produk_index);
            } else {
                ErrorAlert error_alert = new ErrorAlert("Menghapus data", (Node) e.getSource(), "Gagal menghapus data, sudah ada histori penjualan pada produk ini.");
                error_alert.openModal();
            }
        }
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
