/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.custom;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author mhmmadhaibah
 */
public class ActionTableCellRenderer extends DefaultTableCellRenderer
{
    private final Class<? extends JPanel> actionTablePanel;
    
    public ActionTableCellRenderer(Class<? extends JPanel> actionTablePanel)
    {
        this.actionTablePanel = actionTablePanel;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        try
        {
            JPanel tablePanel = actionTablePanel.getDeclaredConstructor().newInstance();
            
            if (isSelected)
            {
                tablePanel.setBackground(table.getSelectionBackground());
            }
            else
            {
                tablePanel.setBackground(Color.WHITE);
            }
            
            return tablePanel;
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return new JPanel();
    }
}
