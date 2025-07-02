/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.model;

import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author mhmmadhaibah
 */
public class MyReport
{
    public static void viewReport(String fileName, boolean isExitOnClose)
    {
        MyReport.viewReport(fileName, new HashMap(), isExitOnClose);
    }
    
    public static void viewReport(String fileName, HashMap parameters, boolean isExitOnClose)
    {
        Connection conn = MyConnection.getConnection();
        
        MyUtils myUtils = new MyUtils();
        URL fileUrl = myUtils.getResource("/reports/" + fileName + ".jasper");
        URL iconImageUrl = myUtils.getResource("/images/coffee_stain.png");
        
        Map params = new HashMap();
        params.put("TITTLE", "Jawhara Syar`i");
        params.put("ICON_IMAGE", iconImageUrl.toString());
        params.putAll(parameters);
        
        try
        {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileUrl);
            JasperPrint  jasperPrint  = JasperFillManager.fillReport(jasperReport, params, conn);
            
            JasperViewer.viewReport(jasperPrint, isExitOnClose);
        }
        catch(JRException e)
        {
            e.printStackTrace();
        }
    }
}
