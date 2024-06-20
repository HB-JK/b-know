package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.example.components.LeftSidebar; // Assuming LeftSidebar is in the same package
import com.example.helpers.DateHelper;
import com.example.helpers.FormatHelper;
import com.example.helpers.UserHelper;
import com.example.model.Penjualan;
import com.example.model.Produk;
import com.example.model.StokJual;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardController implements Initializable {

    @FXML
    private LeftSidebar sidebar; // Assuming LeftSidebar is defined in a separate class

    @FXML
    private PieChart totalPenjualanChart;

    @FXML
    private Label total_penjualan, total_pendapatan, today_date;

    @FXML
    private TableView<Produk> produkHariIniTableView;
    
    @FXML TableColumn<Void, Void> number_property, nama_produk_property, jumlah_property;
    
    @FXML private ScrollPane scrollpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidebar.setActiveClass("dashboard");
        Penjualan penjualan = new Penjualan();
        int total_data = (new UserHelper().getAdmin().getAdminRole().equals("kasir")) ?
            penjualan.getData().size() : penjualan.getData(LocalDate.now().toString(),  LocalDate.now().toString()).size();
            
        total_penjualan.setText(String.valueOf(total_data));
        total_pendapatan.setText(new FormatHelper().convertToRupiah(penjualan.getTotalPenjualan()));
        
        scrollpane.setFitToWidth(true);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        this.setColumnSize();
        today_date.setText(new DateHelper().getTodayDate());
        setChartData();
    }
    
    public void setChartData() {
        List<Produk> list_produk = new Produk().getData();
        if(list_produk != null) {
            for(Produk produk : list_produk) {
                List<StokJual> list_stok = new StokJual().getDataByProductId(produk.getId(), String.valueOf(LocalDate.now()));
                int sell_total = 0;
                
                if(list_stok != null) {
                    for(StokJual stok: list_stok) {
                        sell_total += stok.getJumlahStokSekarang();
                    }
                }
                
                PieChart.Data produk_data = new PieChart.Data(produk.getNama(), sell_total);
                totalPenjualanChart.getData().add(produk_data);
            }
        }
    }
    
    public void setColumnSize() {
        number_property.prefWidthProperty().bind(produkHariIniTableView.widthProperty().multiply(0.2));
        nama_produk_property.prefWidthProperty().bind(produkHariIniTableView.widthProperty().multiply(0.5));
        jumlah_property.prefWidthProperty().bind(produkHariIniTableView.widthProperty().multiply(0.3));
    }
}
