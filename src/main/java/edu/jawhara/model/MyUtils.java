/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.model;

import java.net.URL;

/**
 *
 * @author mhmmadhaibah
 */
public class MyUtils
{
    public URL getResource(String filePath)
    {
        return getClass().getResource(filePath);
    }
}
