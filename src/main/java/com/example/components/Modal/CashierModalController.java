package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.PenjualanController;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.helpers.InputTypeHelper;
import com.example.model.Modal;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class CashierModalController extends BaseModalController implements Initializable{
    //Cashier modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private PenjualanController parent_controller;
    
    // fx:id="fruitCombo" 
    @FXML private ComboBox<String> fruitCombo;
    @FXML private Button close_button, action_button;
    @FXML private TextField modal;
    @FXML private TableView<StokJual> cashierModalTable;
    
    @FXML TableColumn<StokJual, String> noCol, namaProdukCol, stockCol, hargaCol, statusCol;
    @FXML private Label modal_label;
    
    public ObservableList<StokJual> initialData = FXCollections.observableArrayList();
    
    public CashierModalController() {
        this.title = "Input Modal";
    }

    public CashierModalController(String title, double width, double height, Node parent_source, PenjualanController parent_controller) throws IOException {
        super(title, width, height, parent_source, "modal/cashier_modal.fxml");
        this.title = title;

        CashierModalController controller = super.loader.getController();
        controller.setController(parent_controller);
        controller.updateState();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input_helper.setToInt(modal);
        
        this.setupStokJual();
        this.setupColumn();
        this.editData();
    }
    
    public void setupStokJual() {
        List<StokJual> list_stok_jual = new ArrayList<StokJual>();
        
        List<Produk> list_produk = new Produk().getTableData(); 
        for(Produk produk : list_produk) {
            StokJual stok_jual = new StokJual();
            stok_jual.setProduk(produk);
            stok_jual.setStatus();
            list_stok_jual.add(stok_jual);
        }
        
        initialData.addAll(list_stok_jual);
        cashierModalTable.setItems(initialData);
        cashierModalTable.setEditable(true);
    }
    
    public void setupColumn() {
        namaProdukCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.3));
        stockCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.2));
        hargaCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.25));
        statusCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.25));
        
        //set property of each column to get data from the model Produk
        namaProdukCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("namaProduk"));
        stockCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("jumlahStokAwal"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("hargaProduk"));
        statusCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("status"));
    }
    
    private void editData() {
        stockCol.setCellFactory(TextFieldTableCell.<StokJual>forTableColumn());
        stockCol.setOnEditCommit(event -> {
            StokJual stok_jual = event.getTableView().getItems().get(event.getTablePosition().getRow());
            stok_jual.setJumlahStokAwal(event.getNewValue());
            stok_jual.setStatus();
        });
    }
    
    public void openModal() {
        this.showAndWait();
    }
    
    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    public void updateModal(String modal) {
        modal_label.setText(modal);
        
    }
    
    public void updateState() {
        this.action_button.setText("Simpan");
    }

    public void setController(PenjualanController controller) {
        this.parent_controller = controller;
    }

    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void openKasir(ActionEvent e) throws IOException {
        if (this.modal.getText().isEmpty()) {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Harap masukkan modal terlebih dahulu");
            errorAlert.openModal();
            return;
        }
        
        Modal modal = new Modal(this.modal.getText(), "Buka");
        
        for(int i = 0; i < cashierModalTable.getItems().size(); i++) {
            modal.setStokJual(cashierModalTable.getItems().get(i));
        }

        if (modal.save()) {
            SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Modal ditambahkan");
            successAlert.openModal();

            this.parent_controller.updateModal(this.modal.getText());
            this.closeModal();
            return;

        } else {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Modal gagal ditambahkan");
            errorAlert.openModal();
        }
    }
}
