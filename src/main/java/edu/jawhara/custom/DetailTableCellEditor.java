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
public class DetailTableCellEditor extends DefaultCellEditor
{
    private final DetailTableEvent event;
    
    public DetailTableCellEditor(DetailTableEvent event)
    {
        super(new JCheckBox());
        this.event = event;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        DetailTablePanel detailTablePanel = new DetailTablePanel();
        detailTablePanel.setBackground(table.getSelectionBackground());
        detailTablePanel.initEvent(event, row);
        
        return detailTablePanel;
    }
}
