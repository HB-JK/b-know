package com.example.helpers;

import java.util.HashMap;
import java.util.NoSuchElementException;

import com.example.enums.ErrorLevel;
import com.example.model.Admin;
import com.example.model.LogError;

public class UserHelper {
    private DateHelper date_helper = new DateHelper();
    private JsonHelper json_helper = new JsonHelper();
    private HashMap<String, String> admin = new HashMap<String, String>();
    
    public UserHelper() {}
    
    public boolean saveAdminToJson(Admin admin) {
        try {
            this.admin.put("session_end_time", String.valueOf(date_helper.getFutureDateByDay(1)));
            this.admin.put("identity", admin.getId());
            this.admin.put("email", admin.getEmail());
            this.admin.put("name", admin.getNama());
            this.admin.put("admin_role", admin.getAdminRole());
            
            return json_helper.writeToJson(this.admin, "config/session.json");
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            return false;
        }
    }
    
    public void getAdminFromJson() throws NoSuchElementException {
        try {
            this.admin.putAll(json_helper.getJsonFile("config/session.json"));
            
        } catch (Exception e) {
            new LogError(ErrorLevel.CRITICAL, e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Admin getAdmin() {
        try {
            this.getAdminFromJson();
            
            if(this.admin.size() > 0) {
                Admin admin = new Admin();
                admin.setId(this.admin.get("identity"));
                admin.setEmail(this.admin.get("email"));
                admin.setNama(this.admin.get("name"));
                admin.setAdminRole(this.admin.get("admin_role"));
                
                return admin;
            }
        } catch (Exception e) {
            new LogError(ErrorLevel.CRITICAL, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean checkExpired() {
        this.getAdminFromJson();
        
        if(this.admin.size() > 0 && !isSessionEnd(Long.valueOf(this.admin.get("session_end_time")))) {
            return false;
        }
        
        return true;
    }
    
    private boolean isSessionEnd(Long session_end_time) {
        return date_helper.getCurrentTime() > session_end_time;
    }
}
