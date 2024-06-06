package com.example.model;

import com.example.enums.ErrorLevel;

public class DetailPenjualan extends BaseModel {
    private String table = "detail_penjualan";
    private int id;
    private Penjualan penjualan;
    private Produk produk;
    private int jumlah_produk, harga_jual, diskon, total_harga;
    private String created_at, updated_at, deleted_at;
    
    public DetailPenjualan() {}
    
    public DetailPenjualan(Penjualan penjualan, Produk produk, int jumlah_produk, int harga_jual, int diskon) {
        this.setPenjualan(penjualan);
        this.setProduk(produk);
        this.setJumlahProduk(jumlah_produk);
        this.setHargaJual(harga_jual);
        this.setDiskon(diskon);
        this.setTotalHarga(this.jumlah_produk * this.harga_jual - this.diskon);
        this.setCreatedAt(date_helper.getDatabaseTimestamp());
    }
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    // Getter and Setter for 'jumlah_produk'
    public int getJumlahProduk() {
        return jumlah_produk;
    }

    public void setJumlahProduk(int jumlah_produk) {
        this.jumlah_produk = jumlah_produk;
    }

    // Getter and Setter for 'harga_jual'
    public int getHargaJual() {
        return harga_jual;
    }

    public void setHargaJual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    // Getter and Setter for 'diskon'
    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    // Getter and Setter for 'total_harga'
    public int getTotalHarga() {
        return total_harga;
    }

    public void setTotalHarga(int total_harga) {
        this.total_harga = total_harga;
    }

    // Getter and Setter for 'created_at'
    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    // Getter and Setter for 'updated_at'
    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    // Getter and Setter for 'deleted_at'
    public String getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(String deleted_at) {
        this.deleted_at = deleted_at;
    }
    
    public void save() {
        try {
            String query = String.format(
                "INSERT INTO detail_penjualan (id_penjualan, id_produk, nama_customer, jumlah_produk, harga_jual, diskon, total_harga, created_at, updated_at) VALUES ('1', '4', '10', '7000', '0', '70000', '2024-06-07 05:34:50', NULL);"
            );
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        }
    }
}
