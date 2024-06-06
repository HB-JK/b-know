package com.example.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class StokJual extends BaseModel {
    private String table = "stok_jual";
    private StringProperty id = new SimpleStringProperty();
    private StringProperty jumlahStokAwal = new SimpleStringProperty();
    private StringProperty jumlahStokTutup = new SimpleStringProperty();
    private String createdAt, updatedAt;
    private Modal modal;
    private Produk produk;
    
    // Getter and Setter for 'id'
    public final StringProperty idProperty() {
        return id;
    }
    
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    // Getter and Setter for 'jumlah_stok_awal'
    public final StringProperty jumlahStokAwalProperty() {
        return new SimpleStringProperty(
            (jumlahStokAwal.get() == null) ? "0" : jumlahStokAwal.get()
        );
    }
    
    public int getJumlahStokAwal() {
        return (jumlahStokAwal.get() == null) ? 0 : Integer.parseInt(jumlahStokAwal.get());
    }

    public void setJumlahStokAwal(String jumlahStokAwal) {
        this.jumlahStokAwal.set(jumlahStokAwal);
    }

    // Getter and Setter for 'jumlahStokTutup'
    public final StringProperty jumlahStokTutupProperty() {
        return jumlahStokTutup;
    }
    
    public int getJumlahStokTutup() {
        return Integer.parseInt(jumlahStokTutup.get());
    }

    public void setJumlahStokTutup(String jumlahStokTutup) {
        this.jumlahStokTutup.set(jumlahStokTutup);
    }

    // Getter and Setter for 'createdAt'
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = this.date_helper.getDatabaseTimestamp();
    }

    // Getter and Setter for 'updatedAt'
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = this.date_helper.getDatabaseTimestamp();
    }
    
    // Getter for 'namaProduk'
    public final StringProperty namaProdukProperty() {
        return produk.namaProperty();
    }
    
    // Getter for 'hargaProduk'
    public final StringProperty hargaProdukProperty() {
        return produk.hargaProdukProperty();
    }
    
    // Getter for 'hargaProduk'
    public final StringProperty statusProperty() {
        return new SimpleStringProperty(
            (this.getJumlahStokAwal() < 1) ? "Tidak tersedia" : "Tersedia"
        );
    }

    // Getter and Setter for 'modal'
    public Modal getModal() {
        return modal;
    }

    public void setModal(Modal modal) {
        this.modal = modal;
    }

    // Getter and Setter for 'produk'
    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }
}
