package com.example.model;

import java.util.ArrayList;
import java.util.List;

import com.example.database.ConnectDatabase;
import com.example.enums.ErrorLevel;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class DetailPenjualan extends BaseModel {
    private String table = "detail_penjualan";
    private String id;
    private Penjualan penjualan;
    private Produk produk;
    private StringProperty jumlahProduk = new SimpleStringProperty();
    private StringProperty hargaJual = new SimpleStringProperty();
    private StringProperty diskon = new SimpleStringProperty();
    private StringProperty totalHarga = new SimpleStringProperty();
    private StringProperty createdAt = new SimpleStringProperty();
    private String updated_at;
    
    public DetailPenjualan(String id_penjualan) {}
    
    public DetailPenjualan(Penjualan penjualan, Produk produk, int jumlahProduk, int hargaJual, int diskon) {
        // this.setPenjualan(penjualan);
        // this.setProduk(produk);
        // this.setJumlahProduk(jumlahProduk);
        // this.setHargaJual(hargaJual);
        // this.setDiskon(diskon);
        // this.setTotalHarga(this.jumlahProduk * this.hargaJual - this.diskon);
        this.setCreatedAt(date_helper.getDatabaseTimestamp());
    }
    
    //Constructor for instantiate existing data
    public DetailPenjualan(Object object){
        try{
            List<String> data = (ArrayList<String>) object;
            
            this.setId(data.get(0));
            this.jumlahProduk.set(data.get(3));
            this.hargaJual.set(data.get(4));
            this.diskon.set(String.valueOf(data.get(5)));
            this.totalHarga.set(data.get(6));
            this.createdAt.set(data.get(7));
        } catch ( Exception e ) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        }
    }
    
    // Getter and Setter for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for 'penjualan'
    public Penjualan getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    // Getter and Setter for 'produk'
    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    // Getter and Setter for 'jumlahProduk'
    public final StringProperty jumlahProdukProperty() {
        return jumlahProduk;
    }
    
    public int getJumlahProduk() {
        return (jumlahProduk.get() == null) ? 0 : Integer.parseInt(jumlahProduk.get());
    }

    public void setJumlahProduk(String jumlahProduk) {
        this.jumlahProduk.set(jumlahProduk);;
    }

    // Getter and Setter for 'hargaJual'
    public final StringProperty hargaJualProperty() {
        return hargaJual;
    }
    
    public String getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual.set(hargaJual);;
    }

    // Getter and Setter for 'diskon'
    public final StringProperty diskonProperty() {
        return diskon;
    }
    
    public int getDiskon() {
        return Integer.parseInt(diskon.get());
    }

    public void setDiskon(String diskon) {
        this.diskon.set(diskon);;
    }

    // Getter and Setter for 'totalHarga'
    public final StringProperty totalHargaProperty() {
        return totalHarga;
    }
    
    public int getTotalHarga() {
        return Integer.parseInt(totalHarga.get());
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga.set(totalHarga);;
    }

    // Getter and Setter for 'createdAt'
    public final StringProperty createdAtProperty() {
        return createdAt;
    }
    
    public String getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);;
    }

    // Getter and Setter for 'updated_at'
    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
    
    public List<DetailPenjualan> getData() {
        try{
            List<DetailPenjualan> data = new ArrayList<DetailPenjualan>();
            ArrayList<Object> data_fetch = new ConnectDatabase().getAllData(table);
            
            for(Object detail : data_fetch) {
                data.add(new DetailPenjualan(detail));
            }
            
            return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage() + " di model DetailPenjualan");
            return null;
        }
    }
    
    public void save() {
        try {
            String query = String.format(
                "INSERT INTO detail_penjualan (id_penjualan, id_produk, jumlah_produk, harga_jal, diskon, total_harga, created_at, updated_at) VALUES ('1', '4', '10', '7000', '0', '70000', '2024-06-07 05:34:50', NULL);"
            );
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        }
    }
}
