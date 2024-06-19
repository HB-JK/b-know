package com.example.helpers;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateHelper {
    public Date date;
    public Calendar calendar;
    
    public DateHelper() {
        this.date = new Date();
    this.calendar = Calendar.getInstance();
    }
    
    public Long getCurrentTime() {
        return date.getTime();
    }
    
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(date.getTime());
    }
    
    public String getDatabaseTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getCurrentTimestamp());
    }
    
    @SuppressWarnings("deprecation")
    public String getDatabaseDate(String timestamp) {
        DateTimeFormatter formatter =  new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter(); 
        LocalDate datetime = LocalDate.parse(timestamp, formatter);
        
        return new SimpleDateFormat("d MMM Y").format((Date.from(datetime.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
    }
    
    public String getTodayDate() {
        return new SimpleDateFormat("d MMM Y").format(this.getCurrentTimestamp());
    }
    
    public String getTodayDatabaseDate() {
        return new SimpleDateFormat("Y-MM-dd").format(this.getCurrentTimestamp());
    }
    
    public Long getFutureDateByDay(int day) {
        calendar.add(Calendar.DAY_OF_MONTH, day);
        
        return calendar.getTimeInMillis();
    }
    
    public Long getFutureDateByHour(int hour) {
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        
        return calendar.getTimeInMillis();
    }
    
    public Long getFutureDateByMinute(int minute) {
        calendar.add(Calendar.MINUTE, minute);
        
        return calendar.getTimeInMillis();
    }
    
    public int getCurrentMonth() {
        return this.calendar.get(Calendar.MONTH) + 1;
    }
}
