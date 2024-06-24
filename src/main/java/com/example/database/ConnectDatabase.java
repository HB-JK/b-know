package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    
    public ArrayList<Object> getAllData(String table_name) {
        ArrayList<Object> listData = new ArrayList<Object>();
        
        try{
            String query = String.format("SELECT * FROM %1$s", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                ResultSetMetaData meta_data = rs.getMetaData();
                
                List<Object> data = new ArrayList<Object>();
                
                for(int i = 1; i <= meta_data.getColumnCount(); i++) {
                    if(isNumberType(meta_data.getColumnType(i))) {
                        data.add(rs.getInt(i));
                    } else {
                        data.add(rs.getString(i));
                    }
                }
                
                listData.add(data);
            }
            
            // this.stmt.close();
            // this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
        
        return listData;
    }
    
    public ArrayList<Object> getAllDataByDesc(String table_name) {
        ArrayList<Object> listData = new ArrayList<Object>();
        
        try{
            String query = String.format("SELECT * FROM %1$s DESC", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                ResultSetMetaData meta_data = rs.getMetaData();
                
                List<Object> data = new ArrayList<Object>();
                
                for(int i = 1; i <= meta_data.getColumnCount(); i++) {
                    data.add(rs.getString(i));
                }
                
                listData.add(data);
            }
            
            // this.stmt.close();
            // this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
        
        return listData;
    }
    
    public Object getSingleData(String table_name) {
        Object data_fetch = new Object();
        
        try{
            String query = String.format("SELECT * FROM %1$s LIMIT 1", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                ResultSetMetaData meta_data = rs.getMetaData();
                
                List<Object> data = new ArrayList<Object>();
                
                for(int i = 1; i <= meta_data.getColumnCount(); i++) {
                    data.add(rs.getString(i));
                }
                
                data_fetch = data;
            }
            
            // this.stmt.close();
            // this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
        
        return data_fetch;
    }
    
    public Object getSingleDataByDesc(String table_name) {
        Object data_fetch = new Object();
        
        try{
            String query = String.format("SELECT * FROM %1$s ORDER BY id_%1$s DESC LIMIT 1", table_name);
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                ResultSetMetaData meta_data = rs.getMetaData();
                
                List<Object> data = new ArrayList<Object>();
                
                for(int i = 1; i <= meta_data.getColumnCount(); i++) {
                    data.add(rs.getString(i));
                }
                
                data_fetch = data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
        
        return data_fetch;
    }
    
    public Object findById(String table_name, String id) {
        Object data_fetch = new Object();
        
        try{
            String query = String.format("SELECT * FROM %1$s WHERE id_%1$s=%2$s", table_name, id);
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                ResultSetMetaData meta_data = rs.getMetaData();
                
                List<String> data = new ArrayList<String>();
                
                for(int i = 1; i <= meta_data.getColumnCount(); i++) {
                    data.add(rs.getString(i));
                }
                
                data_fetch = data;
            }
            
            // this.stmt.close();
            // this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
        
        return data_fetch;
    }
    
    public ArrayList<Object> getDataQuery(String query) {
        ArrayList<Object> listData = new ArrayList<Object>();
        try{
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                ResultSetMetaData meta_data = rs.getMetaData();
                
                List<Object> data = new ArrayList<Object>();
                
                for(int i = 1; i <= meta_data.getColumnCount(); i++) {
                    data.add(rs.getString(i));
                }
                
                listData.add(data);
            }
            
            // this.stmt.close();
            // this.connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
            return null;
        }
        
        return listData;
    }
    
    public int createUpdateQuery(String query) {
        try{
            int rs = this.stmt.executeUpdate(query);
            
            return rs;
        } catch (SQLException e) {     
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getIntDataByQuery(String query) {
        int totalInt = 0;
        try{
            ResultSet rs = this.stmt.executeQuery(query);
            
            while(rs.next()) {
                totalInt += rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            
            return 0;
        }
        
        return totalInt;
    }
    
    public boolean isNumberType(int column_type_number) {
        if(column_type_number == -5) return true;
        if(column_type_number == 4) return true;
        if(column_type_number == -6) return true;
        if(column_type_number == 5) return true;
        return false;
    }
}
