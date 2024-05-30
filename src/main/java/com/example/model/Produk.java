package com.example.model;

import java.util.ArrayList;
import java.util.List;

import com.example.database.ConnectDatabase;
import com.example.enums.ErrorLevel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Produk extends BaseModel {
    private String table = "produk";
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty kodeProduk = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty hargaProduk = new SimpleStringProperty();
    private final StringProperty satuan = new SimpleStringProperty();
    private final StringProperty sisa_stok = new SimpleStringProperty();
    private final StringProperty createdAt = new SimpleStringProperty();
    private final StringProperty updated_at = new SimpleStringProperty();
    private LogStokProduk[] log_stok_produk;
    
    public Produk() {}
    
    public Produk(String nama, String hargaProduk, String satuan) {
        this.setKodeProduk(this.getUniqueCode());
        this.setNama(nama);
        this.setHargaProduk(hargaProduk);
        this.setSatuan(satuan);
        this.setCreatedAt(this.date_helper.getDatabaseTimestamp());
    }
    
    public Produk(Object object){
        try{
            List<String> data = (ArrayList<String>) object;
            
            this.id.set(String.valueOf(data.get(0)));
            this.kodeProduk.set(data.get(1));
            this.nama.set(data.get(2));
            this.hargaProduk.set(String.valueOf(data.get(3)));
            this.satuan.set(data.get(4));
            this.sisa_stok.set(String.valueOf(data.get(5)));
            this.createdAt.set(data.get(6));
        } catch ( Exception e ) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        }
    }
    
    // Getter and Setter for 'id'
    public final StringProperty idProperty() {
        return id;
    }
    
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    // Getter and Setter for 'kodeProduk'
    public final StringProperty kodeProdukProperty() {
        return kodeProduk;
    }
    
    public String getKodeProduk() {
        return kodeProduk.get();
    }
    
    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk.set(kodeProduk);
    }

    // Getter and Setter for 'nama'
    public final StringProperty namaProperty() {
        return nama;
    }
    
    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    // Getter and Setter for 'hargaProduk'
    public final StringProperty hargaProdukProperty() {
        return new SimpleStringProperty(
            this.format_helper.convertToRupiah(Integer.parseInt(hargaProduk.get()))
        );
    }
    
    public int getHargaProduk() {
        return Integer.parseInt(hargaProduk.get());
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk.set(hargaProduk);
    }

    // Getter and Setter for 'satuan'
    public final StringProperty satuanProperty() {
        return satuan;
    }
    
    public String getSatuan() {
        return satuan.get();
    }

    public void setSatuan(String satuan) {
        this.satuan.set(satuan);
    }

    // Getter and Setter for 'sisa_stok'
    public final StringProperty sisaStokProperty() {
        return sisa_stok;
    }
    
    public int getSisaStok() {
        return Integer.parseInt(sisa_stok.get());
    }

    public void setSisaStok(String sisa_stok) {
        this.sisa_stok.set(sisa_stok);
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
    public StringProperty updatedAtProperty() {
        return updated_at;
    }
    
    public String getUpdatedAt() {
        return updated_at.get();
    }

    public void setUpdatedAt() {
        this.updated_at.set(this.date_helper.getDatabaseTimestamp());
    }
    
    public String getUniqueCode() {
        try{
            List<String> data = this.getLatestData();
            int index = (data.size() > 0) ? Integer.parseInt(data.get(0).toString()) : 1;
            
            return "PROD-" + String.format("%03d", index + 1);
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
    
    public List<Produk> getTableData() {
        try{
            List<Produk> data = new ArrayList<Produk>();
            ArrayList<Object> data_fetch = new ConnectDatabase().getAllData("produk");
            
            for(Object produk : data_fetch) {
                data.add(new Produk(produk));
            }
            
            return data;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage() + " di model Produk");
            return null;
        }
    }
    
    public boolean save() {
        try{
            String query = String.format(
                "INSERT INTO %1$s(kode_produk, nama, harga_produk, satuan, created_at, updated_at) VALUES('%2$s', '%3$s', %4$d, '%5$s', '%6$s', %7$s)",
                table, getKodeProduk(), getNama(), getHargaProduk(), getSatuan(),  getCreatedAt(), getUpdatedAt()
            );
            
            int rs = this.database.createUpdateQuery(query);
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            
            return false;
        }
    }
    
    public boolean update() {
        try{
            this.setUpdatedAt();
            
            String query = String.format(
                "UPDATE %1$s SET nama = '%2$s', harga_produk = %3$d, satuan = '%4$s', sisa_stok = %5$d, updated_at = '%6$s' WHERE id_%1$s = '%7$s';",
                table, getNama(), getHargaProduk(), getSatuan(), getSisaStok(), getUpdatedAt(), getId()
            );
            
            int rs = this.database.createUpdateQuery(query);
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            
            return false;
        }
    }
}

