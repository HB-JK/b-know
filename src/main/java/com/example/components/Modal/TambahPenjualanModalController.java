package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.PenjualanController;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.enums.ErrorLevel;
import com.example.helpers.FormatHelper;
import com.example.helpers.InputTypeHelper;
import com.example.model.DetailPenjualan;
import com.example.model.LogError;
import com.example.model.Modal;
import com.example.model.Penjualan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class TambahPenjualanModalController extends BaseModalController implements Initializable{
    //Tambah / Edit Produk Modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private Penjualan penjualan;
    private Modal modal;
    private PenjualanController parent_controller;
    
    //Tambah / Edit Produk Modal FXML element
    @FXML private Button close_button;
    @FXML private TableView<DetailPenjualan> list_penjualan_table;
    @FXML private TableColumn<DetailPenjualan, String> nama_produk, jumlah, harga, total_harga;
    @FXML private Button action_button, add_button, reset_button, process_button;
    @FXML private TextField nama_customer, total_pembayaran, no_faktur;
    @FXML private Label total_harga_label, total_pembayaran_label, kembalian_label;
    @FXML private HBox pembayaran_actions, buttons;
    public ObservableList<DetailPenjualan> initialData = FXCollections.observableArrayList();
    
    public TambahPenjualanModalController() {
        this.title = "Tambah Penjualan";
    }

    public TambahPenjualanModalController(String title, double width, double height, Node parent_source, Modal modal, PenjualanController parent_controller) throws IOException {
        super(title, width, height, parent_source, "modal/tambah_penjualan_modal.fxml");
        
        this.title = title;
        TambahPenjualanModalController controller = super.loader.getController();
        controller.setController(parent_controller);
        controller.setModal(modal);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setupColumn();
        this.updateState();
        
        list_penjualan_table.setItems(initialData);
        penjualan = new Penjualan();
        
        no_faktur.setText(penjualan.getUniqueCode());
        getTotalHarga();
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
        
        total_pembayaran.textProperty().addListener((observable, oldVal, newVal) -> {
            if (penjualan == null) return;
            
            penjualan.setJumlahBayar(Integer.parseInt(newVal.equals("") ? "0" : newVal));
            penjualan.setKembalian();
            kembalian_label.setText(new FormatHelper().convertToRupiah(penjualan.getKembalian()));
        });
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
        Stage stage = (Stage) process_button.getScene().getWindow();
        stage.close();
    }
    
    public void updateState() {
        if(this.penjualan == null )  {
            this.pembayaran_actions.getChildren().remove(total_pembayaran_label);
            this.setupRow();
        } else {
            this.pembayaran_actions.getChildren().remove(total_pembayaran);
            this.buttons.getChildren().remove(add_button);
            this.buttons.getChildren().remove(reset_button);
            this.buttons.getChildren().remove(process_button);
        }
    }
    
    public void setController(PenjualanController controller) {
        this.parent_controller = controller;
    }
    
    public void setModal(Modal modal) {
        this.modal = modal;
    }
    
    public boolean isDuplicate(DetailPenjualan detail) {
        try {
            for(DetailPenjualan detail_loop: list_penjualan_table.getItems()) {                
                // check if sale item is the exist or not on menu? If exist then will add current added item on exist item count
                if(detail_loop.getProduk().getKodeProduk().equals(detail.getProduk().getKodeProduk())) {
                    if(detail_loop.getJumlahProduk() + detail.getJumlahProduk() > detail_loop.getStokJual().getJumlahStokAwal()) {
                        detail_loop.setJumlahProduk(String.valueOf(detail.getStokJual().getJumlahStokAwal()));
                    } else {
                        detail_loop.setJumlahProduk(String.valueOf(detail_loop.getJumlahProduk() + detail.getJumlahProduk()));
                    }
                    
                    detail_loop.setTotalHarga(String.valueOf(detail_loop.getJumlahProduk() * detail_loop.getHargaJual()));
                    getTotalHarga();
                    list_penjualan_table.refresh();
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());;
            return false;
        }
    }
    
    // update table for insert action
    public void updateTable(DetailPenjualan detail) {
        if(!isDuplicate(detail)) {
            initialData.add(detail);
            getTotalHarga();
        }
    }
    
    // updatetable for edit action
    public void updateTable(DetailPenjualan detail, int index) {
        DetailPenjualan detail_penjualan = list_penjualan_table.getItems().get(index);
        
        if(detail_penjualan.getProduk().getKodeProduk().equals(detail.getProduk().getKodeProduk())) {
            detail_penjualan.setJumlahProduk(String.valueOf(detail.getJumlahProduk()));
            detail_penjualan.setTotalHarga(String.valueOf(detail_penjualan.getJumlahProduk() * detail.getHargaJual()));
            
            getTotalHarga();
            list_penjualan_table.refresh();
            return;
        }

        list_penjualan_table.getItems().remove(index); // remove the item from table        
        if(!isDuplicate(detail)) { // if not duplicate then add a new item
            initialData.add(detail);
            
            getTotalHarga();
            list_penjualan_table.refresh();
        }
    }
    
    public void getTotalHarga() {
        int total_harga = 0;
        for(DetailPenjualan detail : list_penjualan_table.getItems()) {
            total_harga += detail.getJumlahProduk() * detail.getHargaJual();
        }
        
        penjualan.setTotalHarga(String.valueOf(total_harga));
        penjualan.setKembalian();
        total_harga_label.setText(new FormatHelper().convertToRupiah(total_harga));
        kembalian_label.setText(new FormatHelper().convertToRupiah(penjualan.getKembalian()));
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
        initialData.clear();
        
        getTotalHarga();
    }

    @FXML 
    public void tambahPenjualan(ActionEvent e) throws IOException {
        try {
            if(penjualan.getTotalHarga() < 1) {
                new ErrorAlert("Warning", (Node) e.getSource(), "Belum ada barang yang dijual").show();
                return;
            }
            
            if(penjualan.getJumlahBayar() < 1) {
                new ErrorAlert("Warning", (Node) e.getSource(), "Silahkan diisi jumlah uang yang dibayarkan oleh pembeli").show();
                return;
            }
            
            if(penjualan.getJumlahBayar() < penjualan.getTotalHarga()) {
                new ErrorAlert("Warning", (Node) e.getSource(), "Uang yang dibayar tidak mencukupi").show();
                return;
            }
            
            for(DetailPenjualan detail: list_penjualan_table.getItems()) {
                penjualan.setListDetailPenjualan(detail);
            }
            
            penjualan.setNamaCustomer((!nama_customer.getText().equals("")) ? nama_customer.getText() : "Tidak ada nama");
            penjualan.setNomorFaktur(penjualan.getUniqueCode());
            penjualan.setModal(modal);
            
            if(penjualan.save()) {
                new SuccessAlert("Berhasil", (Node) e.getSource(), "Berhasil menambahkan penjualan");
                modal.setJumlahPendapatan(String.valueOf(modal.getJumlahPendapatan() + penjualan.getTotalHarga()));
                modal.update();
                
                parent_controller.updateTable(penjualan);
                parent_controller.updateModal(String.valueOf(modal.getJumlahPendapatan()));
                
                this.closeModal();
            }
        } catch (Exception ex) {
            new LogError(ErrorLevel.ERROR, ex.getMessage());
            ex.printStackTrace();
        }
    }
}
