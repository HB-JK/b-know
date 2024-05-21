package com.example.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.helpers.HashHelper;

public class Admin extends BaseModel{
    private String table = "admin";
    private int id; 
    private String nama, email;
    private String created_at, updated_at;
    public HashHelper hash = new HashHelper();
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
            ResultSet data = this.database.getDataQuery(query);
            
            return data.next();
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public void getAccount() {
        try {
            ResultSet data = this.database.getAllData(table);
            
            if(data.next()){
                this.setId(data.getInt(1));
                this.setNama(data.getString(2));
                this.setEmail(data.getString(3));
                this.setCreatedAt(data.getString(6));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
