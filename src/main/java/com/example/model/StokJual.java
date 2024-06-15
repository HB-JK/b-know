package com.example.model;

import javafx.beans.property.StringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.ConnectDatabase;
import com.example.enums.ErrorLevel;

import javafx.beans.property.SimpleStringProperty;

public class StokJual extends BaseModel {
    private String table = "stok_jual";
    private StringProperty id = new SimpleStringProperty();
    private StringProperty jumlahStokAwal = new SimpleStringProperty();
    private StringProperty jumlahStokSekarang = new SimpleStringProperty();
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
            this.setProduk(new Produk().getDataById(String.valueOf(data.get(2))));
            this.jumlahStokAwal.set(String.valueOf(data.get(3)));
            this.jumlahStokSekarang.set(String.valueOf(data.get(4)));
            this.jumlahStokTutup.set(String.valueOf(data.get(5)));
            this.setCreatedAt(data.get(6));
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

    public void setJumlahStokAwal(int jumlahStokAwal) {
        this.jumlahStokAwal.set(String.valueOf(jumlahStokAwal));
    }
    
    // Getter and Setter for 'jumlah_stok_awal'
    public final StringProperty jumlahStokSekarangProperty() {
        return jumlahStokSekarang;
    }
    
    public int getJumlahStokSekarang() {
        return (jumlahStokSekarang.get() == null) ? 0 : Integer.parseInt(jumlahStokSekarang.get());
    }

    public void setJumlahStokSekarang(int jumlahStokSekarang) {
        this.jumlahStokSekarang.set(String.valueOf(jumlahStokSekarang));
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
    
    public List<StokJual> getData() {
        try{
            List<StokJual> data = new ArrayList<StokJual>();
            
            String query = String.format(
                "SELECT * FROM %1$s WHERE DATE(created_at)='%2$s' AND id_modal=%3$s",
                table, this.date_helper.getTodayDatabaseDate(), this.getModal().getId()
            );
            ArrayList<Object> data_fetch = new ConnectDatabase().getDataQuery(query);
            
            for(Object detail : data_fetch) {
                data.add(new StokJual(detail));
            }
            
            return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage() + " di model StokJual");
            return null;
        }
    }
    
    public boolean save() {
        try {
            String query = String.format(
                "INSERT INTO %1$s (id_modal, id_produk, jumlah_stok_awal, jumlah_stok_terjual, created_at) VALUES ('%2$s', '%3$s', %4$d, 0, '%5$s');",
                table, modal.getId(), produk.getId(), getJumlahStokAwal(), getCreatedAt()
            );
            
            int rs = this.database.createUpdateQuery(query);
            
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            return false;
        }
    }
    
    public boolean update() {
        try {
            String query = String.format(
                "UPDATE %1$s SET jumlah_stok_terjual=%2$d, jumlah_stok_tutup=%3$d, updated_at='%4$s' WHERE id_%1$s='%5$s';",
                table, this.getJumlahStokSekarang(), this.getJumlahStokTutup(), this.getUpdatedAt(), this.getId()
            );
            
            int rs = this.database.createUpdateQuery(query);
            
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            return false;
        }
    }
}
