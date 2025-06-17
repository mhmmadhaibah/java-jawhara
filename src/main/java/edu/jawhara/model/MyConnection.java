/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mhmmadhaibah
 */
public class MyConnection
{
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL    = "jdbc:mysql://localhost:3306/jawhara";
    private static final String DB_USER   = "root";
    private static final String DB_PASS   = "";
    
    public static Connection getConnection()
    {
        Connection conn = null;
        
        try
        {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        
        return conn;
    }
}
