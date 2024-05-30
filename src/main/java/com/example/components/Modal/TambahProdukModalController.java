package com.example.components.Modal;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.DaftarProdukController;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.helpers.InputTypeHelper;
import com.example.model.Modal;
import com.example.model.Produk;

import javafx.application.Platform;
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
    private Produk produk = new Produk();
    
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
    
    public TambahProdukModalController(String title, double width, double height, Node parent_source, Produk produk) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_produk_modal.fxml");
        
        this.title = title;
        TambahProdukModalController controller = super.loader.getController();
        controller.setProduk(produk);
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
        System.out.println(produk.getNama());
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void close(ActionEvent e) {
        System.out.println(this.produk.getId());
        this.closeModal();
    }
    
    public void setProduk(Produk produk) {
        this.produk = produk;
        this.nama_produk.setText(produk.getNama());
        this.harga.setText(String.valueOf(produk.getHargaProduk()));
        this.satuan.setValue(produk.getSatuan());
        this.jumlah_stok.setText(String.valueOf(produk.getSisaStok()));
    }
    
    public Produk getProduk() {
        return produk;
    }

    @FXML
    public void save(ActionEvent e) throws IOException {
        try {
            if (nama_produk.getText().isEmpty()) {
                throw new Exception("Harap mengisi nama produk terlebih dahulu");
            }

            if (harga.getText().isEmpty()) {
                throw new Exception("Harap mengisi harga terlebih dahulu");
            }

            if (satuan.getValue() == null) {
                throw new Exception("Harap memilih satuan terlebih dahulu");
            }

            if (jumlah_stok.getText().isEmpty()) {
                throw new Exception("Harap mengisi jumlah stok terlebih dahulu");
            }

            Produk produk = new Produk(nama_produk.getText(), harga.getText(), satuan.getValue(), jumlah_stok.getText());

            if (produk.save()) {
                SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Produk berhasil ditambahkan");
                successAlert.openModal();
                
                this.produk = produk;
                this.finish = true;
                this.closeModal();
            } else {
                throw new Exception("Produk gagal ditambahkan");
            }
        } catch (Exception exc) {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), exc.getMessage());
            errorAlert.openModal();
        }
    }
}

