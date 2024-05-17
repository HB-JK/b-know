package com.example.obj;

import java.sql.Timestamp;

public class Produk {
    private String table = "produk";
    private int idProduk;
    private String kodeProduk;
    private String nama;
    private int hargaProduk;
    private String satuan;
    private int sisaStok;
    private Timestamp createdAt;

    // Setter methods
    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHargaProduk(int hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setSisaStok(int sisaStok) {
        this.sisaStok = sisaStok;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Getter methods
    public int getIdProduk() {
        return idProduk;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaProduk() {
        return hargaProduk;
    }

    public String getSatuan() {
        return satuan;
    }

    public int getSisaStok() {
        return sisaStok;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}

