package com.example.model;

import javafx.beans.property.StringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.enums.ErrorLevel;

import javafx.beans.property.SimpleStringProperty;

public class StokJual extends BaseModel {
    private String table = "stok_jual";
    private StringProperty id = new SimpleStringProperty();
    private StringProperty jumlahStokAwal = new SimpleStringProperty();
    private StringProperty jumlahStokTutup = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private String createdAt, updatedAt;
    private Modal modal;
    private Produk produk;
    
    public StokJual() {
        this.setCreatedAt();
    }
    
    public StokJual(Object object) {
        try{
            List<String> data = (ArrayList<String>) object;
            
            this.id.set(String.valueOf(data.get(0)));
            this.jumlahStokAwal.set(String.valueOf(data.get(3)));
            this.jumlahStokTutup.set(String.valueOf(data.get(4)));
            this.setCreatedAt(data.get(5));
        } catch ( Exception e ) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        }
    }
    
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
    
    public void setCreatedAt() {
        this.createdAt = this.date_helper.getDatabaseTimestamp();
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = this.date_helper.getDatabaseDate(createdAt);
    }

    // Getter and Setter for 'updatedAt'
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = this.date_helper.getDatabaseTimestamp();
    }
    
    // Getter for 'status stok'
    public final StringProperty statusProperty() {
        return status;
    }
    
    public void setStatus() {
        this.status.set((this.getJumlahStokAwal() > 0) ? "Tersedia" : "Tidak tersedia");
    }
    
    // Getter for 'namaProduk'
    public final StringProperty namaProdukProperty() {
        return produk.namaProperty();
    }
    
    // Getter for 'hargaProduk'
    public final StringProperty hargaProdukProperty() {
        return produk.hargaProdukProperty();
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
    
    public boolean save() {
        try {
            String query = String.format(
                "INSERT INTO %1$s (id_modal, id_produk, jumlah_stok_awal, created_at) VALUES ('%2$s', '%3$s', %4$d, '%5$s');",
                table, modal.getId(), produk.getId(), getJumlahStokAwal(), getCreatedAt()
            );
            
            int rs = this.database.createUpdateQuery(query);
            
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            return false;
        }
    }
}
