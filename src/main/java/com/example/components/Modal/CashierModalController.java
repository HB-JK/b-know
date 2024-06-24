package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.PenjualanController;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.helpers.FormatHelper;
import com.example.helpers.InputTypeHelper;
import com.example.helpers.UserHelper;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierModalController extends BaseModalController implements Initializable{
    //Cashier modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private PenjualanController parent_controller;
    private Modal modal;
    private String action = "add";
    
    // fx:id="fruitCombo" 
    @FXML private ComboBox<String> fruitCombo;
    @FXML private Button close_button, action_button, close_cashier_button;
    @FXML private TextField open_modal_input, close_modal_input;
    @FXML private TableView<StokJual> cashierModalTable;
    
    @FXML TableColumn<StokJual, String> noCol, namaProdukCol, stockCol, hargaCol, statusCol;
    @FXML private Label modal_label, admin_name;
    @FXML private HBox buttons, list_modal;
    @FXML private VBox open_modal_output, close_modal_output;
    
    public ObservableList<StokJual> initialData = FXCollections.observableArrayList();
    
    public CashierModalController() {
        this.title = "Input Modal";
    }

    public CashierModalController(String title, double width, double height, Node parent_source, PenjualanController parent_controller, Modal modal, String action) throws IOException {
        super(title, width, height, parent_source, "modal/cashier_modal.fxml");
        this.title = title;

        CashierModalController controller = super.loader.getController();
        controller.setController(parent_controller);
        controller.setModal(modal);
        controller.action = (modal.getId() != null && action == "edit") ? "edit" : "add";
        controller.updateState();
        controller.setupStokJual();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input_helper.setToInt(open_modal_input);
        
        this.setupColumn();
        this.editData();
        admin_name.setText(new UserHelper().getAdmin().getNama());
        
        // open_modal_input.textProperty().addListener((observable, oldVal, newVal) -> {
        //     if (modal == null) return;
            
        //     modal.setJumlahModalMasuk(newVal.equals("") ? "0" : newVal);
        //     // return ;
        //     open_modal_input.textFormatterProperty().asString(new FormatHelper().convertToCurrency(newVal.equals("") ? 0 : Integer.parseInt(newVal)));
        // });
    }
    
    public void setupStokJual() {
        List<StokJual> list_stok_jual = new ArrayList<StokJual>();
        
        if(modal.getId() == null) {
            List<Produk> list_produk = new Produk().getData(); 
            for(Produk produk : list_produk) {
                StokJual stok_jual = new StokJual();
                stok_jual.setProduk(produk);
                stok_jual.setStatus();
                list_stok_jual.add(stok_jual);
            }
        } else {
            StokJual stok = new StokJual();
            stok.setModal(modal);
            list_stok_jual.addAll(stok.getData());
        }
        
        initialData.addAll(list_stok_jual);
        cashierModalTable.setItems(initialData);
        cashierModalTable.setEditable((action == "edit") ? false : true);
    }
    
    public void setupColumn() {
        namaProdukCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.3));
        stockCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.2));
        hargaCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.25));
        statusCol.prefWidthProperty().bind(cashierModalTable.widthProperty().multiply(0.25));
        
        //set property of each column to get data from the model Produk
        namaProdukCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("namaProduk"));
        stockCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("sisaStok"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("hargaProduk"));
        statusCol.setCellValueFactory(new PropertyValueFactory<StokJual, String>("status"));
    }
    
    private void editData() {
        stockCol.setCellFactory(TextFieldTableCell.<StokJual>forTableColumn());
        stockCol.setOnEditCommit(event -> {
            StokJual stok_jual = event.getTableView().getItems().get(event.getTablePosition().getRow());
            stok_jual.setJumlahStokAwal(Integer.parseInt(event.getNewValue()) + stok_jual.getJumlahStokSekarang());
            stok_jual.setStatus();
            
            if(modal.getId() != null) stok_jual.update();
        });
    }
    
    public void openModal() {
        this.showAndWait();
    }
    
    public void closeModal() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
    
    public void updateState() {
        if(modal.getId() == null) {
            buttons.getChildren().remove(close_cashier_button);
            open_modal_output.getChildren().remove(modal_label);
            list_modal.getChildren().remove(close_modal_output);
        } else {
            buttons.getChildren().remove(action_button);
            open_modal_output.getChildren().remove(open_modal_input);
            modal_label.setText(new FormatHelper().convertToRupiah(modal.getJumlahModalMasuk()));
            
            if((action == "edit")) {
                list_modal.getChildren().remove(open_modal_output);
            } else {
                buttons.getChildren().remove(close_cashier_button);
                list_modal.getChildren().remove(close_modal_output);
            }
        }
    }

    public void setController(PenjualanController controller) {
        this.parent_controller = controller;
    }
    
    public void setModal(Modal modal) {
        this.modal = modal;
    }

    @FXML
    public void close(ActionEvent e) {
        this.closeModal();
    }
    
    @FXML
    public void openKasir(ActionEvent e) throws IOException {
        if (this.open_modal_input.getText().isEmpty()) {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Harap masukkan modal terlebih dahulu");
            errorAlert.openModal();
            return;
        }
        
        Modal modal = new Modal(this.open_modal_input.getText(), "Buka");
        
        for(int i = 0; i < cashierModalTable.getItems().size(); i++) {
            modal.setStokJual(cashierModalTable.getItems().get(i));
        }

        if (modal.save()) {
            SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Modal ditambahkan");
            successAlert.openModal();

            this.parent_controller.updateModal(this.open_modal_input.getText());
            this.closeModal();
            return;

        } else {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Modal gagal ditambahkan");
            errorAlert.openModal();
        }
    }
    
    @FXML
    private void closeKasir(ActionEvent e) throws IOException {
        if (this.close_modal_input.getText().isEmpty()) {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Harap masukkan modal terlebih dahulu");
            errorAlert.openModal();
            return;
        }
        
        modal.setJumlahPenarikanModal(this.close_modal_input.getText());
        modal.setStatusKasir("tutup");
        
        for(int i = 0; i < cashierModalTable.getItems().size(); i++) {
            StokJual stok = cashierModalTable.getItems().get(i);
            stok.setJumlahStokTutup(String.valueOf(stok.getJumlahStokAwal() - stok.getJumlahStokSekarang()));
            modal.setStokJual(stok);
        }

        if (modal.update()) {
            SuccessAlert successAlert = new SuccessAlert("Success", (Node) e.getSource(), "Berhasil menutup kasir");
            successAlert.openModal();

            this.parent_controller.closeCashier();
            this.closeModal();
            return;
        } else {
            ErrorAlert errorAlert = new ErrorAlert("Error", (Node) e.getSource(), "Gagal menutup kasir");
            errorAlert.openModal();
        }
    }
}
