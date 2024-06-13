package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.PenjualanController;
import com.example.helpers.InputTypeHelper;
import com.example.model.Produk;
import com.example.model.StokJual;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProsesJualModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private PenjualanController parent_controller;
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private ComboBox<String> fruitCombo;
    @FXML private Button close_button;
    @FXML private TextField jumlah_produk, sisa_stok, harga;
    @FXML private TextField[] list_integer_input;
    @FXML private ComboBox<String> nama_produk;
    @FXML private TableView<StokJual> prosesJualModalTable;

    @FXML private Label modal_label;

    public ObservableList<StokJual> initialData = FXCollections.observableArrayList();

    
    public ProsesJualModalController() {
        this.title = "Tambah Item Penjualan";
    }

    public ProsesJualModalController(String title, double width, double height, Node parent_source) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_item_penjualan_modal.fxml");
        this.title = title;

        ProsesJualModalController controller = super.loader.getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list_integer_input = new TextField[]{
            jumlah_produk,
            sisa_stok,
            harga
        };
        
        for(TextField input: list_integer_input) {
            input_helper.setToInt(input);
        }
        
        nama_produk.getItems().addAll("Pangsit bakso ayam", "Pangsit sosis ayam", "Pangsit Mayoo");
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
