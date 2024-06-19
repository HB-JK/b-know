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
    private int jumlah_bayar, kembalian;
    private String updated_at;
    private List<DetailPenjualan> list_detail_penjualan = new ArrayList<DetailPenjualan>();
    
    public Penjualan() {}
    
    // instantiate penjualan from database
    public Penjualan(Object object){
        try{
            List<String> data = (ArrayList<String>) object;
            
            this.setId(String.valueOf(data.get(0)));
            this.nomorFaktur.set(data.get(2).toString());
            this.totalHarga.set(String.valueOf(data.get(4)));
            this.createdAt.set(data.get(7).toString());
            this.namaCustomer.set((data.get(3) == null) ? "Tidak ada nama" : data.get(3).toString());
        } catch ( Exception e ) {
            new LogError(ErrorLevel.ERROR, e.getMessage() + " pada constructor model Penjualan");
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
        this.namaCustomer.set(namaCustomer);
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
    
    // Getter and Setter for 'jumlah_bayar'    
    public int getJumlahBayar() {
        return jumlah_bayar;
    }

    public void setJumlahBayar(int jumlah_bayar) {
        this.jumlah_bayar = jumlah_bayar;
    }
    
    // Getter and Setter for 'kembalian'    
    public int getKembalian() {
        return kembalian;
    }

    public void setKembalian() {
        this.kembalian = getJumlahBayar() - getTotalHarga();
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
    
    public String getUniqueCode() {
        try{
            List<String> data = this.getLatestData();
            int index = (data.size() > 0) ? Integer.parseInt(data.get(0).toString()) : 1;
            
            return "P-" + String.format("%07d", index + 1);
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            
            return null;
        }
    }

    // Getter and Setter for 'list_detail_penjualan'
    public DetailPenjualan[] getListDetailPenjualan() {
        if(!list_detail_penjualan.isEmpty()) {
            DetailPenjualan[] list_detail = new DetailPenjualan[list_detail_penjualan.size()];
            for(int i = 0; i < list_detail_penjualan.size(); i++) {
                DetailPenjualan detail = list_detail_penjualan.get(i);
                detail.setPenjualan(this);
                list_detail[i] = detail;
            }
            
            return list_detail;
        } else {
            return new DetailPenjualan[0];
        }
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

    public List<Penjualan> getData(String tanggal_awal, String tanggal_akhir) {
        try{
            List<Penjualan> data = new ArrayList<Penjualan>();
            
            String query = String.format(
                "SELECT * FROM %1$s WHERE DATE(created_at) between '%2$s' AND '%3$s'",
                table, tanggal_awal, tanggal_akhir
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
    
    public List<String> getLatestData() {
        try {
            List<String> data = (List<String>) this.database.getSingleDataByDesc(table);
            
            if(data.size() > 0) {
                this.setId(String.valueOf(data.get(0)));
            }
            
            return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.CRITICAL, e.getMessage() + " di function getLatestData() di model penjualan");
            
            return null;
        }
    }
    
    public void getTotalPenjualan() {
        try{
            List<Penjualan> data = new ArrayList<Penjualan>();
            
            String query = String.format(
                "SELECT SUM(total_harga) FROM %1$s WHERE MONTH(created_at) = %2$s;",
                table, this.date_helper.getCurrentMonth()
            );
            ArrayList<Object> data_fetch = new ConnectDatabase().getDataQuery(query);
            
            System.out.println(data_fetch);
            // for(Object detail : data_fetch) {
            //     data.add(new Penjualan(detail));
            // }
            
            // return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage() + " di model Penjualan");
            // return null;
        }
    }
    
    public boolean save() {
        try {
            this.setCreatedAt(this.date_helper.getDatabaseTimestamp());
            String nama_customer = (this.getNamaCustomer() == null) ? null : "'" + this.getNamaCustomer() + "'";
            
            String query = String.format(
                "INSERT INTO %1$s (id_admin, nomor_faktur, nama_customer, total_harga, jumlah_pembayaran, kembalian, created_at) VALUES ('%2$s', '%3$s', %4$s, %5$d, %6$d, %7$d, '%8$s');",
                table, this.user_helper.getAdmin().getId(), this.getUniqueCode(), nama_customer, this.getTotalHarga(), this.getJumlahBayar(), this.getKembalian(), this.getCreatedAt()
            );
            
            int rs = this.database.createUpdateQuery(query);
            String failed_detail_saving = null;
            
            if(rs == 1 ) {
                this.getLatestData();
                
                if(this.getId() != null) {
                    for(DetailPenjualan detail: getListDetailPenjualan()) {
                        if(!detail.save()) {
                            failed_detail_saving += "Gagal menyimpan data " + detail.getProduk().getNama() + " \n";
                        }
                    }
                    
                    if(failed_detail_saving != null) throw new Exception(failed_detail_saving);
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            new LogError(ErrorLevel.ERROR, e.getMessage() + " di function save pada model penjualan");
            
            return false;
        }
    }
}