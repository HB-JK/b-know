package com.example.database;

import java.sql.*;

public class ConnectDatabase {
    private Credential credential = new Credential();
    private String url = "jdbc:mysql://localhost:3306/" + credential.database_name;
    private String username = credential.username;
    private String password = credential.password;
    
    public ConnectDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
