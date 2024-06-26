package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.helpers.FormatHelper;
import com.example.helpers.InputTypeHelper;
import com.example.model.DetailPenjualan;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TambahItemPenjualanModalController extends BaseModalController implements Initializable {
    // Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private List<StokJual> list_stok_jual;
    private TambahPenjualanModalController parent_controller;
    private ObservableList<String> initial_choice = FXCollections.observableArrayList();
    private Modal modal = new Modal(); // Ensure modal is not null
    private StokJual stok_jual;
    private DetailPenjualan detail;
    private int item_index;

    // Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button, save_button, edit_button;
    @FXML private ComboBox<String> nama_produk;
    @FXML private TextField jumlah_produk, sisa_stok, harga;
    @FXML private TextField[] list_integer_input;
    @FXML private Label total_harga;
    @FXML private HBox buttons;

    public TambahItemPenjualanModalController() {
        this.title = "Tambah Item Penjualan";
    }

    public TambahItemPenjualanModalController(String title, double width, double height, Node parent_source, TambahPenjualanModalController parent_controller) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_item_penjualan_modal.fxml");
        this.title = title;
        
        TambahItemPenjualanModalController controller = super.loader.getController();
        controller.setController(parent_controller);
        controller.updateState();
    }
    
    public TambahItemPenjualanModalController(String title, double width, double height, Node parent_source, TambahPenjualanModalController parent_controller, DetailPenjualan detail, int index) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_item_penjualan_modal.fxml");
        this.title = title;
        
        TambahItemPenjualanModalController controller = super.loader.getController();
        controller.setController(parent_controller);
        controller.setDetail(detail, index);
        controller.updateState();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modal.getTodayCashier();
        list_integer_input = new TextField[] {
            jumlah_produk,
            sisa_stok,
        };

        for (TextField input : list_integer_input) {
            input_helper.setToInt(input);
        }

        jumlah_produk.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.equals("") || stok_jual == null) return;
            
            total_harga.setText(new FormatHelper().convertToRupiah(Integer.parseInt(jumlah_produk.getText()) * stok_jual.getProduk().getHargaProduk()));
            setJumlahProduk();
        });
        
        setupProduk();
        if(detail == null) {
            total_harga.setText(new FormatHelper().convertToRupiah(0));
            return;
        }
        
    }

    public void setupProduk() {
        StokJual stok = new StokJual();
        stok.setModal(modal);
        list_stok_jual = stok.getData();

        for (StokJual data : list_stok_jual) {
            initial_choice.add(data.getProduk().getKodeProduk() + "_" + data.getProduk().getNama());
        }
        nama_produk.setItems(initial_choice);
    }

    public void openModal() {
        this.showAndWait();
    }

    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    public void setController(TambahPenjualanModalController controller) {
        parent_controller = controller;
    }
    
    public void setDetail(DetailPenjualan detail, int index) {
        this.detail = detail;
        this.item_index = index;
        this.stok_jual = detail.getStokJual();
        this.nama_produk.setValue(detail.getProduk().getKodeProduk() + "_" + detail.getProduk().getNama());
        this.sisa_stok.setText(String.valueOf(detail.getStokJual().getJumlahStokAwal() - detail.getStokJual().getJumlahStokSekarang()));
        this.harga.setText(new FormatHelper().convertToRupiah(detail.getProduk().getHargaProduk()));
        this.jumlah_produk.setText(String.valueOf(detail.getJumlahProduk()));
    }
    
    public void updateState() {
        if(detail == null) {
            this.buttons.getChildren().remove(edit_button);
        } else {
            this.buttons.getChildren().remove(save_button);
        }
    }

    public void setJumlahProduk() {
        if (Integer.parseInt(jumlah_produk.getText()) > stok_jual.getJumlahStokAwal()) {
            jumlah_produk.setText(String.valueOf(stok_jual.getJumlahStokAwal() - stok_jual.getJumlahStokSekarang()));
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

        if (produk == null) return;
        for (StokJual data : list_stok_jual) {
            if (data.getProduk().getKodeProduk().equals(produk[0])) {
                stok_jual = data;
                
                sisa_stok.setText(String.valueOf(data.getJumlahStokAwal() - data.getJumlahStokSekarang()));
                harga.setText(new FormatHelper().convertToRupiah(data.getProduk().getHargaProduk()));

                if (jumlah_produk.getText() != null && !jumlah_produk.getText().equals("")) {
                    total_harga.setText(new FormatHelper().convertToRupiah(Integer.parseInt(jumlah_produk.getText()) * stok_jual.getProduk().getHargaProduk()));
                    
                    setJumlahProduk();
                }
                return;
            }
        }
    }

    @FXML
    public void tambah(ActionEvent e) throws IOException {
        try {
            if (stok_jual != null && !jumlah_produk.getText().equals("")) {
                int jumlah = Integer.parseInt(jumlah_produk.getText());
                if (jumlah < 1) {
                    throw new Exception("Jumlah produk harus lebih dari 0.");
                }
                
                detail = new DetailPenjualan(stok_jual, stok_jual.getProduk(), jumlah, stok_jual.getProduk().getHargaProduk(), 0);
                
                new SuccessAlert("Success", (Node) e.getSource(), stok_jual.getProduk().getNama() + " berhasil ditambahkan").openModal();
                parent_controller.updateTable(detail);
                closeModal();
                
                return;
            }
            
            throw new Exception("Pastikan produk sudah terpilih dan jumlah produk sudah terisi");
        } catch (Exception ex) {
            ex.printStackTrace();
            new ErrorAlert("Error", (Node) e.getSource(), ex.getMessage()).openModal();
        }
    }
    
    @FXML
    public void edit(ActionEvent e) throws IOException {
        try {
            if (stok_jual != null && !jumlah_produk.getText().equals("")) {
                int jumlah = Integer.parseInt(jumlah_produk.getText());
                if (jumlah < 1) {
                    throw new Exception("Jumlah produk harus lebih dari 0.");
                }
                
                detail = new DetailPenjualan(stok_jual, stok_jual.getProduk(), jumlah, stok_jual.getProduk().getHargaProduk(), 0);
                
                new SuccessAlert("Success", (Node) e.getSource(), stok_jual.getProduk().getNama() + " berhasil diubah").openModal();
                parent_controller.updateTable(detail, item_index);
                closeModal();
                
                return;
            }
            
            throw new Exception("Pastikan produk sudah terpilih dan jumlah produk sudah terisi");
        } catch (Exception ex) {
            ex.printStackTrace();
            new ErrorAlert("Error", (Node) e.getSource(), ex.getMessage()).openModal();
        }
    }
}