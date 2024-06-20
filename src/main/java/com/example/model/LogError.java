package com.example.model;

import com.example.enums.*;

public class LogError extends BaseModel {
   
    private String table = "log_error";
    private int id;
    private ErrorLevel error_level;
    private String pesan;
    private String created_at, updated_at;
    
    public LogError(ErrorLevel error_level, String pesan) {
        System.out.println(error_level);
        this.setErrorLevel(error_level);
        this.setPesan(pesan);
        this.setCreatedAt(this.date_helper.getDatabaseTimestamp());
        
        this.save();
    }
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for 'error_level'
    public ErrorLevel getErrorLevel() {
        return error_level;
    }

    public void setErrorLevel(ErrorLevel error_level) {
        this.error_level = error_level;
    }

    // Getter and Setter for 'pesan'
    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
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
    
    public void save() {
        try{
            String query = String.format(
                "INSERT INTO %1$s(error_level, pesan, created_at) VALUES('%2$s', '%3$s', '%4$s')",
                table, error_level, pesan, created_at
            );
            
            this.database.createUpdateQuery(query);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
