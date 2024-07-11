package com.example.stephen_app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection dataLink;

    public Connection getConnected(){
        String url = "jdbc:mysql://localhost:3306/stephen_data";
        String username = "root";
        String password = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataLink = DriverManager.getConnection(url,username,password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataLink;
    }
}

