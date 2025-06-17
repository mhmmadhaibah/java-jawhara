/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.custom;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author mhmmadhaibah
 */
public class ActionTableCellRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        ActionTablePanel actionTabelPanel = new ActionTablePanel();
        
        if (isSelected)
        {
            actionTabelPanel.setBackground(table.getSelectionBackground());
        }
        else
        {
            actionTabelPanel.setBackground(Color.WHITE);
        }
        
        return actionTabelPanel;
    }
}
