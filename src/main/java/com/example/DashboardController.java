package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar; // Assuming LeftSidebar is in the same package
import com.example.obj.Produk;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class DashboardController implements Initializable {

    @FXML
    private LeftSidebar sidebar; // Assuming LeftSidebar is defined in a separate class

    @FXML
    private PieChart totalPenjualanChart;

    @FXML
    private Label total_penjualan, total_produk, total_pendapatan;

    @FXML
    private TableView<Produk> produkHariIniTableView;
    
    @FXML TableColumn<Void, Void> number_property, nama_produk_property, jumlah_property;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("dashboard");
        total_penjualan.setText("70");
        total_produk.setText("3");
        total_pendapatan.setText("2.875.000");

        // Set sales data to chart and labels
        PieChart.Data pangsit_mayo = new PieChart.Data("Pangsit Mayoo", 3);
        PieChart.Data pangsit_sosis = new PieChart.Data("Pangsit Sosis Ayam", 1);
        PieChart.Data pangsit_bakso = new PieChart.Data("Pangsit Sosis Ayam", 2);
        totalPenjualanChart.getData().addAll(pangsit_bakso, pangsit_mayo, pangsit_sosis);
        
        this.setColumnSize();
    }
    
    public void setColumnSize() {
        number_property.prefWidthProperty().bind(produkHariIniTableView.widthProperty().multiply(0.2));
        nama_produk_property.prefWidthProperty().bind(produkHariIniTableView.widthProperty().multiply(0.5));
        jumlah_property.prefWidthProperty().bind(produkHariIniTableView.widthProperty().multiply(0.3));
    }
}
