package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.helpers.InputTypeHelper;
import com.example.model.Penjualan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TambahPenjualanModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button;
    @FXML private TableView<Penjualan> list_penjualan_table;
    @FXML private TableColumn<Void, Void> nama_produk_property, jumlah_property, harga_property, total_harga_property;
    
    public TambahPenjualanModalController() {
        this.title = "Tambah Penjualan";
    }

    public TambahPenjualanModalController(String title, double width, double height, Node parent_source) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_penjualan_modal.fxml");
        
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setColumnWidth();
    }
    
    public void setColumnWidth() {
        nama_produk_property.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        jumlah_property.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        harga_property.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
        total_harga_property.prefWidthProperty().bind(list_penjualan_table.widthProperty().multiply(0.25));
    }
    
    public void openModal() {
        this.showAndWait();
    }
    
    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void tambahItemPenjualan(ActionEvent e) {
        try {
            TambahItemPenjualanModalController tambah_item_penjualan_modal = new TambahItemPenjualanModalController("Tambah Item Penjualan", 450, 300, (Node) e.getSource());
            tambah_item_penjualan_modal.openModal();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
    }
}
