package com.example.obj;

public class LogError {
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
}
