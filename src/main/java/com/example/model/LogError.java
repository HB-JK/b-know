package com.example.model;

public class LogError extends BaseModel {
    enum ErrorLevel {
        EMERGENCY,
        ALERT,
        CRITICAL,
        ERROR,
        WAWRNING,
        NOTICE,
        INFORMATIONAL,
        DEBUG
    }
    
    private String table = "log_error";
    private int id;
    private String error_level, pesan_error;
    private String created_at, updated_at;
    
    public LogError(ErrorLevel error_level, String pesan_error) {
        
    }
    
    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for 'error_level'
    public String getErrorLevel() {
        return error_level;
    }

    public void setErrorLevel(String error_level) {
        this.error_level = error_level;
    }

    // Getter and Setter for 'pesan_error'
    public String getPesanError() {
        return pesan_error;
    }

    public void setPesanError(String pesan_error) {
        this.pesan_error = pesan_error;
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
}
