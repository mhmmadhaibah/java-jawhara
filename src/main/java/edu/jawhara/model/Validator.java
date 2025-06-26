/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mhmmadhaibah
 */
public class Validator
{
    public static boolean isAtLeast(String input, int length)
    {
        return isMatcher(".{" + length + ",}", input);
    }
    
    public static boolean isName(String input)
    {
        return isMatcher("^[a-zA-Z ]+$", input);
    }
    
    public static boolean isUsername(String input)
    {
        return isMatcher("^[a-zA-Z0-9]+$", input);
    }
    
    public static boolean isEmail(String input)
    {
        return isMatcher("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", input);
    }
    
    public static boolean isPhoneNumber(String input)
    {
        return isMatcher("^[0-9]{10,15}$", input);
    }
    
    public static boolean isNumeric(String input)
    {
        return isMatcher("^[0-9]+$", input);
    }
    
    public static boolean isMatcher(String regex, String input)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
}
