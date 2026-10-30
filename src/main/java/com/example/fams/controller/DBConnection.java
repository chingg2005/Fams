package com.example.fams.controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/your_database";
    private static String USER = "root";
    private static String PASS = "your_password";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
