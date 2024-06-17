package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.Alert.ErrorAlert;
import com.example.enums.ErrorLevel;
import com.example.helpers.InputTypeHelper;
import com.example.model.DetailPenjualan;
import com.example.model.LogError;
import com.example.model.Penjualan;
import com.example.model.Produk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class TambahPenjualanModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button;
    @FXML private TableView<DetailPenjualan> list_penjualan_table;
    @FXML private TableColumn<DetailPenjualan, String> nama_produk, jumlah, harga, total_harga;
    @FXML private Button action_button;
    @FXML private TextField nama_customer;
    public ObservableList<DetailPenjualan> initialData = FXCollections.observableArrayList();
    
    public TambahPenjualanModalController() {
        this.title = "Tambah Penjualan";
    }

    public TambahPenjualanModalController(String title, double width, double height, Node parent_source) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_penjualan_modal.fxml");
        
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setupColumn();
        this.setupRow();
        
        list_penjualan_table.setItems(initialData);
    }
    
    public void setupColumn() {
        nama_produk.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        jumlah.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        harga.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        total_harga.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        
        //set property of each column to get data from the model Detail Penjualan
        nama_produk.setCellValueFactory(new PropertyValueFactory<DetailPenjualan, String>("namaProduk"));
        jumlah.setCellValueFactory(new PropertyValueFactory<DetailPenjualan, String>("jumlahProduk"));
        harga.setCellValueFactory(new PropertyValueFactory<DetailPenjualan, String>("hargaJual"));
        total_harga.setCellValueFactory(new PropertyValueFactory<DetailPenjualan, String>("totalHarga"));
    }
    
    public void setupRow() {
        list_penjualan_table.setRowFactory(tv -> {
            TableRow<DetailPenjualan> row = new TableRow<>();
            
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() ) {
                    DetailPenjualan rowData = row.getItem();
                    
                    try {
                        TambahItemPenjualanModalController tambah_item_penjualan = new TambahItemPenjualanModalController("Edit Item Penjualan", 450, 300, (Node) event.getSource(), this, rowData, row.getIndex());
                        tambah_item_penjualan.openModal();
                    } catch (IOException el) {
                        el.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    public void openModal() {
        this.showAndWait();
    }
    
    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    public boolean isDuplicate(DetailPenjualan detail) {
        try {
            for(int i = 0; i < list_penjualan_table.getItems().size(); i++) {
                DetailPenjualan detail_loop = list_penjualan_table.getItems().get(i); // make a loop item to a variable
                
                // check if sale item is the exist or not on menu? If exist then will add current added item on exist item count
                if(detail_loop.getProduk().getKodeProduk().equals(detail.getProduk().getKodeProduk())) {
                    if(detail_loop.getJumlahProduk() + detail.getJumlahProduk() > detail_loop.getStokJual().getJumlahStokAwal()) {
                        detail_loop.setJumlahProduk(String.valueOf(detail.getStokJual().getJumlahStokAwal()));
                    } else {
                        detail_loop.setJumlahProduk(String.valueOf(detail_loop.getJumlahProduk() + detail.getJumlahProduk()));
                    }
                    
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());;
            return false;
        }
    }
    
    public void updateTable(DetailPenjualan detail) {
        if(!isDuplicate(detail)) {
            initialData.add(detail);
        }
    }
    
    public void updateTable(DetailPenjualan detail, int index) {
        DetailPenjualan detail_penjualan = list_penjualan_table.getItems().get(index);
        
        if(detail_penjualan.getProduk().getKodeProduk().equals(detail.getProduk().getKodeProduk())) {
            detail_penjualan.setJumlahProduk(String.valueOf(detail.getJumlahProduk()));
            return;
        }

        list_penjualan_table.getItems().remove(index); // remove the item from table        
        if(!isDuplicate(detail)) { // if not duplicate then add a new item
            initialData.add(detail);
        }
    }
    
    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void tambahItemPenjualan(ActionEvent e) {
        try {
            TambahItemPenjualanModalController tambah_item_penjualan_modal = new TambahItemPenjualanModalController("Tambah Item Penjualan", 450, 300, (Node) e.getSource(), this);
            tambah_item_penjualan_modal.openModal();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    @FXML
    public void resetPenjualan(ActionEvent e) {
        try {
            TambahItemPenjualanModalController tambah_item_penjualan_modal = new TambahItemPenjualanModalController("Tambah Item Penjualan", 450, 300, (Node) e.getSource(), this);
            tambah_item_penjualan_modal.openModal();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // @FXML 
    // public void tambahPenjualan(ActionEvent e) throws IOException {
    //     if (this..getTex)
    // }
}
