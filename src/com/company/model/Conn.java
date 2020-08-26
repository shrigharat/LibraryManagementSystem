package com.company.model;

import java.sql.*;

public class Conn {
    public Connection conn;
    public Statement statement;
    public Conn() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:library.db");
            statement = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Couldn't connect to database : " + e.getMessage());
        }
    }
    public void close() {
        try {
            conn.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't close connection : "+ exception.getMessage());
        }
    }
}
