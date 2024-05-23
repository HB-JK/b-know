package com.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.helpers.HashHelper;

public class Admin extends BaseModel{
    private String table = "admin";
    private String id, nama, email;
    private String created_at, updated_at;
    public HashHelper hash = new HashHelper();
    
    
    // Getter and Setter for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for 'nama'
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter and Setter for 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public boolean verifyAccount(String email, String password) {
        try {
            String hashed_password = this.hash.getMd5(password);
            
            String query = String.format("SELECT * FROM %1$s WHERE email='%2$s' AND password='%3$s'", table, email, hashed_password);
            ArrayList<Object> data = this.database.getDataQuery(query);
            
            return (data.size() > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public void getAccount() {
        try {
            List<String> data = ((ArrayList<String>) this.database.getSingleData(table));
            
            if(data.size() > 0){
                this.setId(data.get(0).toString());
                this.setNama(data.get(1));
                this.setEmail(data.get(2));
                this.setCreatedAt(data.get(5));
                this.setUpdatedAt(data.get(6));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
