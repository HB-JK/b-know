package com.example.obj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.example.helpers.HashHelper;

public class Admin extends Model{
    private String table = "admin";
    private String nama, email;
    private Timestamp createdAt, updatedAt;
    public HashHelper hash = new HashHelper();
    
    public Admin() {
        
    }
    
    public boolean verifyAccount(String email, String password) {
        try {
            String hashed_password = new Admin().hash.getMd5(password);
            
            String query = String.format("SELECT * FROM admin WHERE email='%1$s' AND password='%2$s'", email, hashed_password);
            ResultSet data = super.database.executeQuery(query);
            
            return data.next();
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
}
