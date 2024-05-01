package com.example.components;

import java.io.IOException;

import com.example.App;

import javafx.beans.DefaultProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@DefaultProperty(value = "extension")
public class LeftSidebar extends AnchorPane {
    @FXML
    private VBox extension;
    @FXML
    private ImageView logo;
    @FXML
    private AnchorPane dashboard, penjualan, produk, rekap_penjualan, rekap_produk, logout;
    @FXML
    private ImageView dashboard_icon, penjualan_icon, produk_icon, rekap_penjualan_icon, rekap_produk_icon, logout_icon;
    @FXML
    private ImageView[] list_icon;

    public LeftSidebar() {
        try {
            this.getStylesheets().add(getClass().getResource("/assets/css/left_sidebar.css").toExternalForm());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/component/left_sidebar.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

            this.logo.setImage(new Image(getClass().getResource("/assets/img/logo.png").toExternalForm()));

            this.list_icon = new ImageView[] {
                    dashboard_icon,
                    penjualan_icon,
                    produk_icon,
                    rekap_penjualan_icon,
                    rekap_produk_icon,
                    logout_icon
            };

            this.addIcons();
            this.setIconPosition();
        } catch (IOException exc) {
            // handle exception
        }
    }

    public void setIconPosition() {
        for (ImageView icon : this.list_icon) {
            AnchorPane.setTopAnchor(icon, 12.0);
            AnchorPane.setLeftAnchor(icon, 5.0);
        }
    }

    public void addIcons() {
        this.dashboard_icon
                .setImage(new Image(getClass().getResource("/assets/img/icons/view-dashboard.png").toExternalForm()));
        this.penjualan_icon.setImage(new Image(getClass().getResource("/assets/img/icons/cart.png").toExternalForm()));
        this.produk_icon.setImage(
                new Image(getClass().getResource("/assets/img/icons/briefcase-variant.png").toExternalForm()));
        this.rekap_penjualan_icon
                .setImage(new Image(getClass().getResource("/assets/img/icons/note-text.png").toExternalForm()));
        this.rekap_produk_icon
                .setImage(new Image(getClass().getResource("/assets/img/icons/archive.png").toExternalForm()));
        this.logout_icon
                .setImage(new Image(getClass().getResource("/assets/img/icons/logout-variant.png").toExternalForm()));
    }

    public void setActiveClass(String path) {
        if (path == "dashboard")
            dashboard.getStyleClass().addAll("active");
        else if (path == "penjualan")
            penjualan.getStyleClass().addAll("active");
        else if (path == "daftar_produk")
            produk.getStyleClass().addAll("active");
        else if (path == "rekap_penjualan")
            rekap_penjualan.getStyleClass().addAll("active");
        else if (path == "rekap_produk")
            rekap_produk.getStyleClass().addAll("active");
    }

    @FXML
    public void changeToDashboard(ActionEvent e) {
        try {
            App.setRoot("dashboard");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void changeToDaftarProduk(ActionEvent e) {
        try {
            App.setRoot("daftar_produk");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    public void changeToPenjualan(ActionEvent e) {
        try {
            App.setRoot("penjualan");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void changeToRekapPenjualan(ActionEvent e) {
        try {
            App.setRoot("rekap_penjualan");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    public void changeToRekapProduk(ActionEvent e) {
        try {
            App.setRoot("rekap_produk");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}