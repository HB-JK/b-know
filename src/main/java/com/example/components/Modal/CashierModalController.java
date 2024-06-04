package com.example.components.Modal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.PenjualanController;
import com.example.components.Alert.ErrorAlert;
import com.example.components.Alert.SuccessAlert;
import com.example.helpers.InputTypeHelper;
import com.example.model.Modal;
import com.example.model.Penjualan;

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
import javafx.stage.Stage;

public class CashierModalController extends BaseModalController implements Initializable{
    //Cashier modal property
    private String title;
    private InputTypeHelper input_helper = new InputTypeHelper();
    private PenjualanController parent_controller;

    @FXML // fx:id="fruitCombo"
    private ComboBox<String> fruitCombo;
    //Cashier modal fxml element
    @FXML private Button close_button, action_button;
    @FXML private TextField modal;
    // @FXML private Button action_button;
    @FXML private TableView<Penjualan> invoiceTable;
    
    @FXML TableColumn<Void, Void> noCol, namaProdukCol, stockCol, hargaCol, statusCol;

    @FXML private Label modal_label;
    
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

    public void setupColumn() {
        noCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.15));
        namaProdukCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.3));
        stockCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.15));
        hargaCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
        statusCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(0.2));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input_helper.setToInt(modal);
        this.setupColumn();
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
