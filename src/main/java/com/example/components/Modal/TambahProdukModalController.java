package com.example.components.Modal;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.DaftarProdukController;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.enums.ErrorLevel;
import com.example.helpers.InputTypeHelper;
import com.example.model.LogError;
import com.example.model.Produk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TambahProdukModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private Produk produk = new Produk();
    private DaftarProdukController parent_controller;
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private HBox buttons;
    @FXML private Button close_button, save_button, edit_button;
    @FXML private TextField nama_produk, harga;
    @FXML private ComboBox<String> satuan;
    private TextField[] list_input_integer;
    
    public TambahProdukModalController() {
        this.title = "Tambah Produk";
    }

    public TambahProdukModalController(String title, double width, double height, Node parent_source, DaftarProdukController parent_controller) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_produk_modal.fxml");
        this.title = title;
        
        TambahProdukModalController controller = super.loader.getController();
        controller.setController(parent_controller);
        controller.updateState();
    }
    
    public TambahProdukModalController(String title, double width, double height, Node parent_source, DaftarProdukController daftarProdukController, Produk produk) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_produk_modal.fxml");
        this.title = title;
        
        TambahProdukModalController controller = super.loader.getController();
        controller.setController(daftarProdukController);
        controller.setProduk(produk);
        controller.updateState();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list_input_integer = new TextField[] {
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
    
    public void setProduk(Produk produk) {
        this.produk = produk;
        this.nama_produk.setText(produk.getNama());
        this.harga.setText(String.valueOf(produk.getHargaProduk()));
        this.satuan.setValue(produk.getSatuan());
    }
    
    public Produk getProduk() {
        return produk;
    }
    
    public void updateState() {
        if(produk.getId() == null) {
            this.buttons.getChildren().remove(edit_button);
        } else {
            this.buttons.getChildren().remove(save_button);
        }
    }
    
    public void setController(DaftarProdukController controller) {
        this.parent_controller = controller;
    }
    
    public String isEmptyInput() {
        if (nama_produk.getText().isEmpty()) {
            return "Harap mengisi nama produk terlebih dahulu";
        }

        if (harga.getText().isEmpty()) {
            return "Harap mengisi harga terlebih dahulu";
        }

        if (satuan.getValue() == null) {
            return "Harap memilih satuan terlebih dahulu";
        }
        
        return null;
    }

    @FXML
    public void save(ActionEvent e) throws IOException {
        try {
            produk = new Produk(nama_produk.getText(), harga.getText(), satuan.getValue());
            String check_empty_input = isEmptyInput();
            
            if(check_empty_input != null){
                throw new Exception(check_empty_input);
            }

            if (produk.save()) {
                SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Produk berhasil ditambahkan");
                successAlert.openModal();
                
                parent_controller.updateTable(produk);
                
                this.closeModal();
                return;
            }
            
            throw new Exception("Produk gagal ditambahkan");
        } catch (Exception exc) {
            new LogError(ErrorLevel.ERROR, exc.getMessage());
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), exc.getMessage());
            errorAlert.openModal();
        }
    }
    
    @FXML
    public void update(ActionEvent e) throws IOException {
        try {
            String check_empty_input = isEmptyInput();
            
            if(check_empty_input != null){
                throw new Exception(check_empty_input);
            }
            
            produk.setHargaProduk(this.harga.getText());
            produk.setNama(this.nama_produk.getText());
            produk.setSatuan(this.satuan.getValue());

            if (produk.update()) {
                SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Produk " + produk.getNama() +" diubah");
                successAlert.openModal();
                
                parent_controller.updateTable();
                this.closeModal();
                return;
            }
            
            throw new Exception("Produk gagal diubah");
        } catch (Exception exc) {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), exc.getMessage());
            errorAlert.openModal();
        }

    }
}