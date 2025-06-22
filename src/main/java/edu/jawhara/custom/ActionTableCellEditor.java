/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.custom;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author mhmmadhaibah
 */
public class ActionTableCellEditor extends DefaultCellEditor
{
    private final ActionTableEvent actionTableEvent;
    private final Class<? extends JPanel> actionTablePanel;
    
    public ActionTableCellEditor(ActionTableEvent actionTableEvent, Class<? extends JPanel> actionTablePanel)
    {
        super(new JCheckBox());
        this.actionTableEvent = actionTableEvent;
        this.actionTablePanel = actionTablePanel;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        try
        {
            JPanel tablePanel = actionTablePanel.getDeclaredConstructor().newInstance();
            tablePanel.setBackground(table.getSelectionBackground());
            
            if (tablePanel instanceof ActionTablePanel actionPanel)
            {
                actionPanel.initEvent(actionTableEvent, row);
            }
            
            return tablePanel;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return new JPanel();
    }
}
