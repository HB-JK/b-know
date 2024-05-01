package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar; // Assuming LeftSidebar is in the same package
import com.example.obj.Produk;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class DashboardController implements Initializable {

    @FXML
    private ImageView logo;

    @FXML
    private LeftSidebar sidebar; // Assuming LeftSidebar is defined in a separate class

    @FXML
    private PieChart totalPenjualanChart;

    @FXML
    private Label totalPenjualanLabel;

    @FXML
    private Label totalProdukLabel;

    @FXML
    private Label totalPendapatanHariIniLabel;

    @FXML
    private TableView<Produk> produkHariIniTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("dashboard");
        // Initialize sales data
        int totalPenjualan = 70;
        int totalProduk = 5;
        double totalPendapatanHariIni = 2875000;

        // Set sales data to chart and labels
        PieChart.Data slice = new PieChart.Data("Total Penjualan", totalPenjualan);
        // slice.getStyleClass().add("total-penjualan-slice");
        totalPenjualanChart.getData().add(slice);

        // Set product data to table
        // produkHariIniTableView.setItems(ProdukData.getData());

        // You can potentially add logic to set the active class for the sidebar here
        // if LeftSidebar provides such functionality
    }
}
