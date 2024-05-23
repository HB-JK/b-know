package com.example.model;

public class Penjualan extends BaseModel {
    private String table = "penjualan";
    private int id;
    private Admin admin;
    private String nomor_faktur, nama_customer;
    private int total_harga;
    private String created_at, updated_at, deleted_at;
    private DetailPenjualan[] list_detail_penjualan;
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for 'admin'
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Getter and Setter for 'nomor_faktur'
    public String getNomorFaktur() {
        return nomor_faktur;
    }

    public void setNomorFaktur(String nomor_faktur) {
        this.nomor_faktur = nomor_faktur;
    }

    // Getter and Setter for 'nama_customer'
    public String getNamaCustomer() {
        return nama_customer;
    }

    public void setNamaCustomer(String nama_customer) {
        this.nama_customer = nama_customer;
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

    // Getter and Setter for 'list_detail_penjualan'
    public DetailPenjualan[] getListDetailPenjualan() {
        return list_detail_penjualan;
    }

    public void setListDetailPenjualan(DetailPenjualan[] list_detail_penjualan) {
        this.list_detail_penjualan = list_detail_penjualan;
    }
}
