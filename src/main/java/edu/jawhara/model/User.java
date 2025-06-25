/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.model;

/**
 *
 * @author mhmmadhaibah
 */
public class User
{
    private static int userId;
    private static String name;
    private static String role;
    private static String username;
    private static String password;
    
    public static int getUserId()
    {
        return userId;
    }
    
    public static void setUserId(int input)
    {
        userId = input;
    }
    
    public static String getName()
    {
        return name;
    }
    
    public static void setName(String input)
    {
        name = input;
    }
    
    public static String getRole()
    {
        return role;
    }
    
    public static void setRole(String input)
    {
        role = input;
    }
    
    public static String getUsername()
    {
        return username;
    }
    
    public static void setUsername(String input)
    {
        username = input;
    }
    
    public static String getPassword()
    {
        return password;
    }
    
    public static void setPassword(String input)
    {
        password = input;
    }
}
