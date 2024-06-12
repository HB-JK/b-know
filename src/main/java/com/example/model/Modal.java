package com.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.enums.ErrorLevel;
import javafx.beans.property.StringProperty;

import javafx.beans.property.SimpleStringProperty;

public class Modal extends BaseModel {
    private String table = "modal";
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty jumlahModalMasuk = new SimpleStringProperty();
    private final StringProperty jumlahPenarikanModal = new SimpleStringProperty();
    private final StringProperty statusKasir = new SimpleStringProperty();
    private final StringProperty createdAt = new SimpleStringProperty();
    private final StringProperty updatedAt = new SimpleStringProperty();
    private List<StokJual> list_stok_jual = new ArrayList<StokJual>();
    private Admin admin;

    public Modal() {
        this.setAdmin(this.user_helper.getAdmin());
    }

    public Modal(String jumlahModalMasuk, String statusKasir) {
        this.setAdmin(this.user_helper.getAdmin());
        this.setJumlahModalMasuk(jumlahModalMasuk);
        this.setStatusKasir(statusKasir);
        this.setCreatedAt(date_helper.getDatabaseTimestamp());
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

    // Getter and Setter for 'admin'
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Getter and Setter for 'jumlahModalMasuk'
    public final StringProperty jumlahModalMasukProperty() {
        return jumlahModalMasuk;
    }
    
    public int getJumlahModalMasuk() {
        return Integer.parseInt(jumlahModalMasuk.get());
    }

    public void setJumlahModalMasuk(String jumlahModalMasuk) {
        this.jumlahModalMasuk.set(jumlahModalMasuk);
    }

    // Getter and Setter for 'jumlahPenarikanModal'
    public final StringProperty jumlahPenarikanModalProperty() {
        return jumlahPenarikanModal;
    }
    
    public int getJumlahPenarikanModal() {
        return Integer.parseInt(jumlahPenarikanModal.get());
    }

    public void setJumlahPenarikanModal(String jumlahPenarikanModal) {
        this.jumlahPenarikanModal.set(jumlahPenarikanModal);
    }

    // Getter and Setter for 'statusKasir'
    public final StringProperty statusKasirProperty() {
        return statusKasir;
    }
    
    public String getStatusKasir() {
        return statusKasir.get();
    }

    public void setStatusKasir(String statusKasir) {
        this.statusKasir.set(statusKasir);
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
    public final StringProperty updatedAtProperty() {
        return updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt.get();
    }

    public void setUpdatedAt() {
        this.updatedAt.set(this.date_helper.getDatabaseTimestamp());
    }
    
    //Getter and Setter for list_stok_jual
    public StokJual[] getListStokJual() {
        if(!list_stok_jual.isEmpty()) {
            StokJual[] list_stok = new StokJual[list_stok_jual.size()];
            for(int i = 0; i < list_stok_jual.size(); i++) {
                StokJual stok = list_stok_jual.get(i);
                stok.setModal(this);
                list_stok[i] = stok;
            }
            
            return list_stok;
        } else {
            return new StokJual[0];
        }
    }
    
    public void setStokJual(StokJual stok_jual) {
        list_stok_jual.add(stok_jual);
    }
    
    public void getTodayCashier() {
        try {
            String query = String
                .format(
                    "SELECT * FROM %1$s WHERE DATE(created_at)='%2$s' AND status_kasir='buka' AND id_admin='%3$s' ORDER BY id_modal DESC LIMIT 1",
                    table, date_helper.getTodayDatabaseDate(), admin.getId()
                );

            ArrayList<Object> rs = this.database.getDataQuery(query);
            
            if(rs.size() > 0) {
                List<String> data = (ArrayList<String>) rs.get(0);
                this.setId(String.valueOf(data.get(0)));
                this.setJumlahModalMasuk(String.valueOf(data.get(2)));
                this.setJumlahPenarikanModal(String.valueOf(data.get(3)));
                this.setStatusKasir(data.get(5).toString());
                this.setCreatedAt(data.get(6).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            new LogError(ErrorLevel.CRITICAL, e.getMessage());
        }
    }

    public boolean save() {
        try {
            String query = String
                .format(
                    "INSERT INTO %1$s(id_admin, jumlah_modal_masuk, status_kasir, created_at) VALUES('%2$s', %3$d, '%4$s', '%5$s')",
                    table, admin.getId(), getJumlahModalMasuk(), getStatusKasir(), getCreatedAt()
                );

            int rs = this.database.createUpdateQuery(query);
            String failed_stock_saving = null;
            
            if(rs == 1 ) {
                this.getTodayCashier();
                
                if(this.getId() != null) {
                    for(StokJual stok_jual: getListStokJual()) {
                        if(!stok_jual.save()) {
                            failed_stock_saving += "Gagal menyimpan data " + stok_jual.getProduk().getNama() + " \n";
                        }
                    }
                    
                    if(failed_stock_saving != null) throw new Exception(failed_stock_saving);
                }
            }
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            new LogError(ErrorLevel.CRITICAL, e.getMessage());

            return false;
        }
    }

}