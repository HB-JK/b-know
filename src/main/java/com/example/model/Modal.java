package com.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Modal extends BaseModel {
    private String table = "modal";
    private int id, id_admin;
    private Admin admin;
    private int jumlah_modal_masuk, jumlah_penarikan_modal;
    private String status_kasir;
    private String created_at, updated_at;
    
    public Modal() {}
    
    public Modal(String jumlah_modal_masuk, String status_kasir) {
        this.setAdmin(this.user_helper.getAdmin());
        this.setJumlahModalMasuk(Integer.parseInt(jumlah_modal_masuk));
        this.setStatusKasir(status_kasir);
        this.setCreatedAt(date_helper.getDatabaseTimestamp());
    }
    
    // Getter and Setter for 'id'
    public int getIdModal() {
        return id;
    }

    public void setIdModal(int id) {
        this.id = id;
    }

    // Getter and Setter for 'id_admin'
    public int getIdAdmin() {
        return id_admin;
    }

    public void setIdAdmin(int id_admin) {
        this.id_admin = id_admin;
    }

    // Getter and Setter for 'admin'
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Getter and Setter for 'jumlah_modal_masuk'
    public int getJumlahModalMasuk() {
        return jumlah_modal_masuk;
    }

    public void setJumlahModalMasuk(int jumlah_modal_masuk) {
        this.jumlah_modal_masuk = jumlah_modal_masuk;
    }

    // Getter and Setter for 'jumlah_penarikan_modal'
    public int getJumlahPenarikanModal() {
        return jumlah_penarikan_modal;
    }

    public void setJumlahPenarikanModal(int jumlah_penarikan_modal) {
        this.jumlah_penarikan_modal = jumlah_penarikan_modal;
    }

    // Getter and Setter for 'status_kasir'
    public String getStatusKasir() {
        return status_kasir;
    }

    public void setStatusKasir(String status_kasir) {
        this.status_kasir = status_kasir;
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
    
    public boolean save() {
        try{
            String query = String
            .format("INSERT INTO %1$s(id_admin, jumlah_modal_masuk,jumlah_penarikan_modal, status_kasir, created_at, updated_at) VALUES('%2$s', %3$d, %4$d, '%5$s', '%6$s', %7$s)",
            table, admin.getId(), jumlah_modal_masuk, jumlah_penarikan_modal, status_kasir, created_at, updated_at);
            
            int rs = this.database.createUpdateQuery(query);
            return (rs == 1) ? true : false;
            
        } catch (Exception e) {
            return false;
        }
    }
}
