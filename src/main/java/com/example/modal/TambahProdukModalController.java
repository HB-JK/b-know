package com.example.modal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.helpers.InputTypeHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TambahProdukModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button;
    @FXML private TextField nama_produk, jumlah_stok, harga;
    @FXML private ComboBox<String> satuan;
    private TextField[] list_input_integer;
    
    public TambahProdukModalController() {
        this.title = "Tambah Produk";
    }

    public TambahProdukModalController(String title, double width, double height, Node parent_source) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_produk_modal.fxml");
        
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list_input_integer = new TextField[] {
            jumlah_stok,
            harga
        };
        
        for(TextField input: list_input_integer) {
            input_helper.setToInt(input);
        }
        
        satuan.getItems().addAll("Pcs", "Lembar", "Buah", "Pack");
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
}