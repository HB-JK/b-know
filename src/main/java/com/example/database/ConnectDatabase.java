package com.example.database;

import java.sql.*;

public class ConnectDatabase {
    private String url = "jdbc:mysql://localhost:3306/bknow";
    private String username = "root";
    private String password = "";
    
    public ConnectDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
