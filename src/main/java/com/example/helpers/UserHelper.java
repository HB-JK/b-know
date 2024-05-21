package com.example.helpers;

import com.example.model.Admin;

public class UserHelper {
    public UserHelper() {}
    
    public Admin getAdmin() {
        Admin admin = new Admin();
        admin.getAccount();
        
        return admin;
    }
}
