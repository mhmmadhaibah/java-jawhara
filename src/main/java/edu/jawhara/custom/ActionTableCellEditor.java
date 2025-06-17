/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.custom;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author mhmmadhaibah
 */
public class ActionTableCellEditor extends DefaultCellEditor
{
    private final ActionTableEvent event;
    
    public ActionTableCellEditor(ActionTableEvent event)
    {
        super(new JCheckBox());
        this.event = event;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        ActionTablePanel actionTablePanel = new ActionTablePanel();
        actionTablePanel.setBackground(table.getSelectionBackground());
        actionTablePanel.initEvent(event, row);
        
        return actionTablePanel;
    }
}
