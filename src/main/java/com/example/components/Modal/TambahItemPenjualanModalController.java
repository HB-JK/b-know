package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.helpers.FormatHelper;
import com.example.helpers.InputTypeHelper;
import com.example.model.Modal;
import com.example.model.StokJual;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TambahItemPenjualanModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private Modal modal = new Modal();
    private InputTypeHelper input_helper = new InputTypeHelper();
    private List<StokJual> list_stok_jual;
    private StokJual stok_jual;
    public ObservableList<String> initialData = FXCollections.observableArrayList();
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button;
    @FXML private ComboBox<String> nama_produk;
    @FXML private TextField jumlah_produk, sisa_stok, harga;
    @FXML private TextField[] list_integer_input;
    
    public TambahItemPenjualanModalController() {
        this.title = "Tambah Item Penjualan";
    }

    public TambahItemPenjualanModalController(String title, double width, double height, Node parent_source) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_item_penjualan_modal.fxml");
        
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modal.getTodayCashier();
        list_integer_input = new TextField[]{
            jumlah_produk,
            sisa_stok,
        };
        
        for(TextField input: list_integer_input) {
            input_helper.setToInt(input);
        }
        
        jumlah_produk.textProperty().addListener((observeable, oldVal, newVal) -> {
            if(newVal.equals("") || stok_jual == null) return;
            
            setJumlahProduk();
        });
        
        
        setupProduk();
    }
    
    public void setupProduk() {
        StokJual stok = new StokJual();
        stok.setModal(modal);
        
        list_stok_jual = stok.getData();
        
        for(StokJual data: list_stok_jual ){
            initialData.add(data.getProduk().getKodeProduk()+"_"+data.getProduk().getNama());
        }
        nama_produk.setItems(initialData);
    }
    
    public void openModal() {
        this.showAndWait();
    }
    
    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    public void setJumlahProduk() {
        if(Integer.parseInt(jumlah_produk.getText()) > stok_jual.getJumlahStokAwal()) {
            jumlah_produk.setText(String.valueOf(stok_jual.getJumlahStokAwal()));
        }
    }
    
    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void changeProduk(ActionEvent e) {
        String selected_item = nama_produk.getSelectionModel().getSelectedItem();
        String[] produk = (selected_item != null) ? selected_item.split("_") : null;
        
        if(produk == null) return;
        for(StokJual data : list_stok_jual) {
            if(data.getProduk().getKodeProduk().equals(produk[0])) {
                stok_jual = data;
                sisa_stok.setText(String.valueOf(data.getJumlahStokAwal()));
                harga.setText(new FormatHelper().convertToRupiah(data.getProduk().getHargaProduk()));
                
                if(jumlah_produk.getText() != null && !jumlah_produk.getText().equals("")) {
                    setJumlahProduk();
                }
                return;
            }
        }
    }
}
