package com.example.helpers;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateHelper {
    public Date date;
    
    public DateHelper() {
        this.date = new Date();
    }
    
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(date.getTime());
    }
    
    public String getDatabaseTimestamp() {
        return new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(this.getCurrentTimestamp());
    }
}
