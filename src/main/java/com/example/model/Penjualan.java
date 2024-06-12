package com.example.model;

import java.util.ArrayList;
import java.util.List;

import com.example.database.ConnectDatabase;
import com.example.enums.ErrorLevel;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Penjualan extends BaseModel {
    private String table = "penjualan";
    private String id;
    private Admin admin;
    private StringProperty nomorFaktur = new SimpleStringProperty();
    private StringProperty namaCustomer = new SimpleStringProperty();
    private StringProperty totalHarga = new SimpleStringProperty();
    private StringProperty createdAt = new SimpleStringProperty();
    private String updated_at;
    private List<DetailPenjualan> list_detail_penjualan;
    
    public Penjualan() {}
    
    // instantiate penjualan from database
    public Penjualan(Object object){
        try{
            List<String> data = (ArrayList<String>) object;
            
            this.setId(String.valueOf(data.get(0)));
            this.nomorFaktur.set(data.get(2).toString());
            this.totalHarga.set(String.valueOf(data.get(4)));
            this.createdAt.set(data.get(5).toString());
            this.namaCustomer.set((data.get(3) == null) ? "Tidak ada nama" : data.get(3).toString());
            
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

    // Getter and Setter for 'admin'
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Getter and Setter for 'nomorFaktur'
    public final StringProperty nomorFakturProperty() {
        return nomorFaktur;
    }

    public String getNomorFaktur() {
        return nomorFaktur.get();
    }

    public void setNomorFaktur(String nomorFaktur) {
        this.nomorFaktur.set(nomorFaktur);
    }

    // Getter and Setter for 'namaCustomer'
    public final StringProperty namaCustomerProperty() {
        return namaCustomer;
    }
    
    public String getNamaCustomer() {
        return namaCustomer.get();
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer.set(namaCustomer);;
    }

    // Getter and Setter for 'totalHarga'
    public final StringProperty totalHargaProperty() {
        return new SimpleStringProperty(
            this.format_helper.convertToRupiah(Integer.parseInt(totalHarga.get()))
        );
    }
    
    public int getTotalHarga() {
        return (totalHarga.get() == null) ? 0 : Integer.parseInt(totalHarga.get());
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga.set(totalHarga);
    }

    // Getter and Setter for 'createdAt'
    public final StringProperty createdAtProperty() {
        return new SimpleStringProperty(
            this.date_helper.getDatabaseDate(createdAt.get())
        );
    }
    
    public String getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }

    // Getter and Setter for 'updated_at'
    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    // Getter and Setter for 'deleted_at'
    public StringProperty jumlahProdukProperty() {
        int totalProduk = 0;
        
        // for(DetailPenjualan detail : list_detail_penjualan) {
        //     totalProduk++;
        // }
        
        return new SimpleStringProperty(
            String.valueOf(totalProduk)
        );
    }

    // Getter and Setter for 'list_detail_penjualan'
    public List<DetailPenjualan> getListDetailPenjualan() {
        return list_detail_penjualan;
    }

    public void setListDetailPenjualan(DetailPenjualan detail_penjualan) {
        this.list_detail_penjualan.add(detail_penjualan);
    }
    
    public List<Penjualan> getData() {
        try{
            List<Penjualan> data = new ArrayList<Penjualan>();
            
            String query = String.format(
                "SELECT * FROM %1$s WHERE DATE(created_at)='%2$s' AND id_admin=%3$s",
                table, this.date_helper.getTodayDatabaseDate(), this.user_helper.getAdmin().getId()
            );
            ArrayList<Object> data_fetch = new ConnectDatabase().getDataQuery(query);
            
            for(Object detail : data_fetch) {
                data.add(new Penjualan(detail));
            }
            
            return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage() + " di model Penjualan");
            return null;
        }
    }
}