package com.example.obj;

import com.example.database.ConnectDatabase;

public class Model{
    public ConnectDatabase database;
    
    public Model() {
        this.database = new ConnectDatabase();
    }
}
