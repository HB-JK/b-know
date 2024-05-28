package com.example.model;

import com.example.database.ConnectDatabase;
import com.example.helpers.DateHelper;
import com.example.helpers.FormatHelper;
import com.example.helpers.UserHelper;

public class BaseModel{
    public ConnectDatabase database;
    public DateHelper date_helper;
    public UserHelper user_helper;
    public FormatHelper format_helper;
    
    public BaseModel() {
        this.database = new ConnectDatabase();
        this.date_helper = new DateHelper();
        this.user_helper = new UserHelper();
        this.format_helper = new FormatHelper();
    }
}
