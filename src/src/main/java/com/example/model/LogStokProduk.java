package com.example.model;

public class LogStokProduk extends BaseModel {
    private String table = "log_stok_produk";
    private int id;
    private Admin admin;
    private Produk produk;
    private int stok_produk;
    private String tipe_transaksi, keterangan;
    private String created_at, updated_at;
    
    public LogStokProduk() {
        
    }
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setIdLogStokProduk(int id) {
        this.id = id;
    }

    // Getter and Setter for 'admin'
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Getter and Setter for 'produk'
    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    // Getter and Setter for 'stok_produk'
    public int getStokProduk() {
        return stok_produk;
    }

    public void setStokProduk(int stok_produk) {
        this.stok_produk = stok_produk;
    }

    // Getter and Setter for 'tipe_transaksi'
    public String getTipeTransaksi() {
        return tipe_transaksi;
    }

    public void setTipeTransaksi(String tipe_transaksi) {
        this.tipe_transaksi = tipe_transaksi;
    }

    // Getter and Setter for 'keterangan'
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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
}
