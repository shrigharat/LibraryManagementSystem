package com.company;

import com.company.model.Conn;
import com.company.model.Home;
import com.company.model.LoginUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main{
    public static void main(String[] args) {
        Conn conn = new Conn();
        String sql = "SELECT status FROM logStatus WHERE id=1";
        try {
            PreparedStatement statement = conn.conn.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                int status = results.getInt(1);
                if(status == 1) {
                    System.out.println("Sending to home");
                    new Home().setVisible(true);
                } else if(status == 0) {
                    System.out.println("Sending to login");
                    new LoginUser().setVisible(true);
                }
            } else {
                System.out.println("Couldn't retrieve status value");
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't retrieve status value  : " + exception.getMessage());
        }

    }
}

