package com.example.model;

public class StokJual {
    private String table = "stok_jual";
    private int id;
    private int jumlah_stok_awal, jumlah_stok_tutup;
    private String created_at, updated_at;
    private Modal modal;
    private Produk produk;
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for 'jumlah_stok_awal'
    public int getJumlahStokAwal() {
        return jumlah_stok_awal;
    }

    public void setJumlahStokAwal(int jumlah_stok_awal) {
        this.jumlah_stok_awal = jumlah_stok_awal;
    }

    // Getter and Setter for 'jumlah_stok_tutup'
    public int getJumlahStokTutup() {
        return jumlah_stok_tutup;
    }

    public void setJumlahStokTutup(int jumlah_stok_tutup) {
        this.jumlah_stok_tutup = jumlah_stok_tutup;
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
