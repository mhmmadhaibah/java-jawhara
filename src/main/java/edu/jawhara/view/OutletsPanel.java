/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.jawhara.view;

import edu.jawhara.custom.ActionTableCellEditor;
import edu.jawhara.custom.ActionTableCellRenderer;
import edu.jawhara.custom.ActionTableEvent;
import edu.jawhara.custom.ActionTableEventAdapter;
import edu.jawhara.custom.EditDeleteActionTablePanel;
import edu.jawhara.model.Loading;
import edu.jawhara.model.MyConnection;
import edu.jawhara.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mhmmadhaibah
 */
public class OutletsPanel extends javax.swing.JPanel {
    private static final Connection conn = MyConnection.getConnection();

    private final boolean adminFlag;

    private DefaultTableModel outletsTableModel;
    private DefaultTableColumnModel outletsTableColumnModel;

    /**
     * Creates new form OutletsPanel
     */
    public OutletsPanel() {
        initComponents();
        
        adminFlag = User.getRole().equals("Admin");
        refreshOutlets();
    }

    @Override
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);
        
        if (aFlag)
        {
            refreshOutlets();
        }
    }

    private void refreshOutlets()
    {
        rButton1.setVisible(adminFlag);
        
        Loading.infiniteLoading(jPanel1, "tablePanel");
        
        outletsTableModel = (DefaultTableModel) jTable1.getModel();
        outletsTableColumnModel = (DefaultTableColumnModel) jTable1.getColumnModel();
        
        resetOutletsTable();
        loadOutletsTable();
        
        customOutletsTable();
    }

    private void resetOutletsTable()
    {
        outletsTableModel.getDataVector().removeAllElements();
        outletsTableModel.fireTableStructureChanged();
        outletsTableModel.fireTableDataChanged();
        outletsTableModel.setRowCount(0);
    }

    private void loadOutletsTable()
    {
        try
        {
            String sqlq = "SELECT * FROM outlets ORDER BY id ASC";
            
            PreparedStatement stmt = conn.prepareStatement(sqlq);
            ResultSet rslt = stmt.executeQuery();
            
            while (rslt.next())
            {
                Object[] data = new Object[4];
                data[0] = rslt.getString("id");
                data[1] = rslt.getString("name");
                data[2] = rslt.getString("description");
                data[3] = null;
                
                outletsTableModel.addRow(data);
            }
            
            jLabel3.setText(String.valueOf(outletsTableModel.getRowCount()));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void customOutletsTable()
    {
        ActionTableEvent actionTableEvent = new ActionTableEventAdapter() {
            @Override
            public void onEdit(int row)
            {
                int outletId = Integer.parseInt(outletsTableModel.getValueAt(row, 0).toString());
                
                UpdateOutletFrame updateOutletFrame = new UpdateOutletFrame(outletId);
                updateOutletFrame.setVisible(true);
            }
            
            @Override
            public void onDelete(int row)
            {
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are sure want to delete ?",
                    "Delete Outlets",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION)
                {
                    int outletId = Integer.parseInt(outletsTableModel.getValueAt(row, 0).toString());
                    
                    try
                    {
                        String sqlq = "DELETE FROM outlets WHERE id = ?";
                        PreparedStatement stmt = conn.prepareStatement(sqlq);
                        
                        stmt.setInt(1, outletId);
                        int rslt = stmt.executeUpdate();
                        
                        if (rslt > 0)
                        {
                            JOptionPane.showMessageDialog(null, "Outlet deleted successfully.");
                            refreshOutlets();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Something wrong!");
                        }
                    }
                    catch (SQLException e)
                    {
                        JOptionPane.showMessageDialog(null, e);
                        e.printStackTrace();
                    }
                }
            }
        };
        
        outletsTableColumnModel.getColumn(3).setCellRenderer(new ActionTableCellRenderer(EditDeleteActionTablePanel.class));
        outletsTableColumnModel.getColumn(3).setCellEditor(new ActionTableCellEditor(actionTableEvent, EditDeleteActionTablePanel.class));
        
        outletsTableColumnModel.getColumn(3).setPreferredWidth(165);
        outletsTableColumnModel.getColumn(3).setMaxWidth(165);
        outletsTableColumnModel.getColumn(3).setMinWidth(165);
        
        outletsTableColumnModel.getColumn(1).setPreferredWidth(300);
        outletsTableColumnModel.getColumn(1).setMaxWidth(300);
        outletsTableColumnModel.getColumn(1).setMinWidth(300);
        
        if (!adminFlag)
        {
            outletsTableColumnModel.removeColumn(outletsTableColumnModel.getColumn(3));
        }
        
        outletsTableColumnModel.removeColumn(outletsTableColumnModel.getColumn(0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rButton2 = new rojerusan.RSMaterialButtonRectangle();
        rButton1 = new rojerusan.RSMaterialButtonRectangle();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Outlets");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Total");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("0");

        rButton2.setBackground(new java.awt.Color(51, 51, 51));
        rButton2.setText("Refresh");
        rButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton2ActionPerformed(evt);
            }
        });

        rButton1.setText("New Outlet");
        rButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton1ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());
        jPanel1.add(new edu.jawhara.view.InfinitePanel(), "infinitePanel");

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setIntercellSpacing(new java.awt.Dimension(10, 5));
        jTable1.setOpaque(false);
        jTable1.setRowHeight(55);
        jTable1.setShowGrid(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 36));
        jTable1.getTableHeader().setBackground(new java.awt.Color(51, 51, 51));
        jTable1.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", 0, 16));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, "tablePanel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton1ActionPerformed
        CreateOutletFrame createOutletFrame = new CreateOutletFrame();
        createOutletFrame.setVisible(true);
    }//GEN-LAST:event_rButton1ActionPerformed

    private void rButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton2ActionPerformed
        refreshOutlets();
    }//GEN-LAST:event_rButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private rojerusan.RSMaterialButtonRectangle rButton1;
    private rojerusan.RSMaterialButtonRectangle rButton2;
    // End of variables declaration//GEN-END:variables
}
