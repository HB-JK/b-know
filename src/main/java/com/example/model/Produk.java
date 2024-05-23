package com.example.model;

import java.util.ArrayList;
import java.util.List;

import com.example.enums.ErrorLevel;

public class Produk extends BaseModel {
    private String table = "produk";
    private int id;
    private String kode_produk;
    private String nama;
    private int harga_produk;
    private String satuan;
    private int sisa_stok;
    private String created_at, updated_at;
    
    public Produk() {}
    
    public Produk(String nama, String harga_produk, String satuan, String sisa_stok) {
        this.setNama(nama);
        this.setHargaProduk(Integer.parseInt(harga_produk));
        this.setSisaStok(Integer.parseInt(sisa_stok));
        this.setSatuan(satuan);
        this.setCreatedAt(this.date_helper.getDatabaseTimestamp());
    }
    
    public Produk(Object object){
        ArrayList<String> data = new ArrayList<String>();
        this.id = Integer.parseInt(data.get(0).toString());
        this.kode_produk = data.get(1).toString();
        this.nama = data.get(2).toString();
        this.harga_produk = Integer.parseInt(data.get(3).toString());
        this.satuan = data.get(4).toString();
        this.sisa_stok = Integer.parseInt(data.get(5).toString());
        this.created_at = data.get(6).toString();
    }
    
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
    
    // Getter and Setter for 'updated_at'
    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
    
    public String getUniqueKode() {
        try{
            List<String> data = this.getLatestData();
            int index = (data.size() > 0) ? Integer.parseInt(data.get(0).toString()) : 1;
            
            return "PROD-" + String.format("%03d", index);
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            
            return null;
        }
    }
    
    public List<String> getLatestData() {
        try {
            List<String> data = (List<String>) this.database.getSingleDataByDesc(table);
            
            return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.CRITICAL, e.getMessage());
            
            return null;
        }
    }
    
    public boolean save() {
        try{
            String query = String.format(
                "INSERT INTO %1$s(kode_produk, nama, harga_produk, satuan, sisa_stok, created_at, updated_at) VALUES('%2$s', '%3$s', %4$d, '%5$s', %6$d, '%7$s', %8$s)",
                table, this.getUniqueKode(), nama, harga_produk, satuan, sisa_stok, created_at, updated_at
            );
            
            int rs = this.database.createUpdateQuery(query);
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            
            return false;
        }
    }
}

