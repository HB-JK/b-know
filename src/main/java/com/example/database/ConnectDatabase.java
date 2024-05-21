package com.example.database;

import java.sql.*;

public class ConnectDatabase {
    private Connection connection;
    private Statement stmt;
    private String table_name;
    
    public ConnectDatabase() {
        Credential credential = new Credential();
        
        try {
            this.connection = DriverManager.getConnection(credential.database_url, credential.username, credential.password);
            
            System.out.println("Database connected!");
            this.stmt = this.connection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    
    public ResultSet getAllData(String table_name) {
        try{
            String query = String.format("SELECT * FROM %1$s", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            
            return null;
        }
    }
    
    public ResultSet getAllData(String table_name, String sort_column) {
        try{
            String query = String.format("SELECT * FROM %1$s SORT BY %2$s", table_name, sort_column);
            ResultSet rs = this.stmt.executeQuery(query);
            
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            
            return null;
        }
    }
    
    public ResultSet getAllDataByDesc(String table_name) {
        try{
            String query = String.format("SELECT * FROM %1$s DESC", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            
            return null;
        }
    }
    
    public ResultSet getAllDataByDesc(String table_name, String sort_column) {
        try{
            String query = String.format("SELECT * FROM %1$s SORT BY %2$s DESC", table_name, sort_column);
            ResultSet rs = this.stmt.executeQuery(query);
            
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            
            return null;
        }
    }
    
    public ResultSet getSingleData(String table_name) {
        try{
            String query = String.format("SELECT * FROM %1$s SORT BY %2$s LIMIT 1", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            
            return null;
        }
    }
    
    public ResultSet findById(String table_name, int id) {
        try{
            String query = String.format("SELECT * FROM %1$s WHERE id_%1$s=%2$s", table_name, id);
            ResultSet rs = this.stmt.executeQuery(query);
            
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            
            return null;
        }
    }
    
    public ResultSet getDataQuery(String query) {
        try{
            ResultSet rs = this.stmt.executeQuery(query);
            // this.connection.close();
            
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return null;
        }
    }
    
    public int createUpdateQuery(String query) {
        try{
            int rs = this.stmt.executeUpdate(query);
            // this.connection.close();
            
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return 0;
        }
    }
}
