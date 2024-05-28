package com.example.helpers;

import java.util.HashMap;

import com.example.enums.ErrorLevel;
import com.example.model.Admin;
import com.example.model.LogError;

public class UserHelper {
    private DateHelper date_helper = new DateHelper();
    private JsonHelper json_helper = new JsonHelper();
    private HashMap<String, String> admin = new HashMap<String, String>();
    
    public UserHelper() {}
    
    public void saveAdmin(Admin admin) {
        try {
            this.admin.put("session_end_time", String.valueOf(date_helper.getFutureDateByMinute(1)));
            this.admin.put("identity", admin.getId());
            this.admin.put("email", admin.getEmail());
            this.admin.put("name", admin.getNama());
            this.admin.put("admin_role", admin.getIsSuperadmin().equals("y") ? "superadmin" : "kasir");
            
            json_helper.WriteToJson(this.admin, "config/session.json");
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        }
    }
    
    public boolean checkSessionTime(Long session_end_time) {
        return date_helper.getCurrentTime() < session_end_time;
    }
}
