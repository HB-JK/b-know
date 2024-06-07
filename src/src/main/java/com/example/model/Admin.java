package com.example.model;

import java.util.ArrayList;
import java.util.List;

import com.example.enums.ErrorLevel;
import com.example.helpers.HashHelper;

public class Admin extends BaseModel{
    private String table = "admin";
    private String id, nama, email, role_admin;
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
    
    // Getter and Setter for 'role_admin'
    public String getAdminRole() {
        return role_admin;
    }

    public void setAdminRole(String role_admin) {
        this.role_admin = role_admin;
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
            
            if(data.size() > 0) {
                this.setAccount(data.get(0));
                return true;
            }
            
            return false;
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            e.printStackTrace();
            
            return false;
        }
    }
    
    public void setAccount(Object obj) {
        try{
            List<String> data = (ArrayList) obj;
            
            this.setId(String.valueOf(data.get(0)));
            this.setNama(data.get(1).toString());
            this.setEmail(data.get(2).toString());
            this.setAdminRole(data.get(4).toString().equals("y") ? "superadmin" : "kasir");
        } catch (Exception e) {
            new LogError(ErrorLevel.CRITICAL, e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getAccount(int id) {
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
