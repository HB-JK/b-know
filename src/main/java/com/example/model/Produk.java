package com.example.model;

public class Produk {
    private String table = "produk";
    private int id;
    private String kode_produk;
    private String nama;
    private int harga_produk;
    private String satuan;
    private int sisa_stok;
    private String created_at;
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for 'kode_produk'
    public String getKodeProduk() {
        return kode_produk;
    }

    public void setKodeProduk(String kode_produk) {
        this.kode_produk = kode_produk;
    }

    // Getter and Setter for 'nama'
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter and Setter for 'harga_produk'
    public int getHargaProduk() {
        return harga_produk;
    }

    public void setHargaProduk(int harga_produk) {
        this.harga_produk = harga_produk;
    }

    // Getter and Setter for 'satuan'
    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    // Getter and Setter for 'sisa_stok'
    public int getSisaStok() {
        return sisa_stok;
    }

    public void setSisaStok(int sisa_stok) {
        this.sisa_stok = sisa_stok;
    }

    // Getter and Setter for 'created_at'
    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
}

