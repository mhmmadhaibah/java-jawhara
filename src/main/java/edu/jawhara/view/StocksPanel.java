/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edu.jawhara.view;

import edu.jawhara.custom.ActionTableCellEditor;
import edu.jawhara.custom.ActionTableCellRenderer;
import edu.jawhara.custom.ActionTableEvent;
import edu.jawhara.custom.ActionTableEventAdapter;
import edu.jawhara.custom.DetailsActionTablePanel;
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
public class StocksPanel extends javax.swing.JPanel {
    private static final Connection conn = MyConnection.getConnection();

    private DefaultTableModel stocksTableModel;
    private DefaultTableColumnModel stocksTableColumnModel;

    /**
     * Creates new form StocksPanel
     */
    public StocksPanel() {
        initComponents();
        refreshStocks();
    }

    @Override
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);
        
        if (aFlag)
        {
            refreshStocks();
        }
    }

    private void refreshStocks() {
        Loading.infiniteLoading(jPanel1, "tablePanel");
        
        stocksTableModel = (DefaultTableModel) jTable1.getModel();
        stocksTableColumnModel = (DefaultTableColumnModel) jTable1.getColumnModel();
        
        resetStocksTable();
        loadStocksTable();
        
        customStocksTable();
    }

    private void resetStocksTable()
    {
        stocksTableModel.getDataVector().removeAllElements();
        stocksTableModel.fireTableStructureChanged();
        stocksTableModel.fireTableDataChanged();
        stocksTableModel.setRowCount(0);
    }

    private void loadStocksTable()
    {
        try
        {
            String sqlq = "SELECT t.id, u.username AS staff, t.type, t.timestamp ";
            sqlq += "FROM transactions t JOIN users u ON t.user_id = u.id ORDER BY t.timestamp DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sqlq.trim());
            ResultSet rslt = stmt.executeQuery();
            
            while (rslt.next())
            {
                Object[] data = new Object[6];
                data[0] = String.valueOf(rslt.getInt("id"));
                data[1] = rslt.getString("staff");
                data[2] = rslt.getString("type");
                data[3] = rslt.getTimestamp("timestamp").toString().split("\\.")[0];
                data[4] = null;
                data[5] = null;
                
                stocksTableModel.addRow(data);
            }
            
            jLabel3.setText(String.valueOf(stocksTableModel.getRowCount()));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void customStocksTable()
    {
        ActionTableEvent actionTableEvent1 = new ActionTableEventAdapter() {
            @Override
            public void onDetails(int row)
            {
                int stockId = Integer.parseInt(stocksTableModel.getValueAt(row, 0).toString());
                
                StockDetailsFrame stockDetailsFrame = new StockDetailsFrame(stockId);
                stockDetailsFrame.setVisible(true);
            }
        };
        
        stocksTableColumnModel.getColumn(4).setCellRenderer(new ActionTableCellRenderer(DetailsActionTablePanel.class));
        stocksTableColumnModel.getColumn(4).setCellEditor(new ActionTableCellEditor(actionTableEvent1, DetailsActionTablePanel.class));
        
        stocksTableColumnModel.getColumn(4).setPreferredWidth(104);
        stocksTableColumnModel.getColumn(4).setMaxWidth(104);
        stocksTableColumnModel.getColumn(4).setMinWidth(104);
        
        ActionTableEvent actionTableEvent2 = new ActionTableEventAdapter() {
            @Override
            public void onEdit(int row)
            {
                int stockId = Integer.parseInt(stocksTableModel.getValueAt(row, 0).toString());
                
                UpdateStockFrame updateStockFrame = new UpdateStockFrame(stockId);
                updateStockFrame.setVisible(true);
            }
            
            @Override
            public void onDelete(int row)
            {
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are sure want to delete ?",
                    "Delete Stock",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION)
                {
                    int stockId = Integer.parseInt(stocksTableModel.getValueAt(row, 0).toString());
                    
                    try
                    {
                        String sqlq = """
                            SELECT td.*, t.type
                                FROM transactions t JOIN transaction_details td
                                ON t.id = td.transaction_id WHERE t.id = ?
                            """;
                        
                        PreparedStatement stmt = conn.prepareStatement(sqlq.trim());
                        
                        stmt.setInt(1, stockId);
                        ResultSet rslt = stmt.executeQuery();
                        
                        while (rslt.next())
                        {
                            String sqlq2 = "DELETE FROM transaction_details WHERE id = ?";
                            PreparedStatement stmt2 = conn.prepareStatement(sqlq2);
                            
                            stmt2.setInt(1, rslt.getInt("id"));
                            stmt2.executeUpdate();
                            
                            String operator = rslt.getString("type").equals("OUT") ? "+" : "-";
                            String sqlq3 = "UPDATE product_stocks SET quantity = quantity " + operator + " ? WHERE product_id = ?";
                            PreparedStatement stmt3 = conn.prepareStatement(sqlq3);
                            
                            stmt3.setInt(1, rslt.getInt("quantity"));
                            stmt3.setInt(2, rslt.getInt("product_id"));
                            stmt3.executeUpdate();
                        }
                        
                        String sqlq4 = "DELETE FROM transactions WHERE id = ?";
                        PreparedStatement stmt4 = conn.prepareStatement(sqlq4);
                        
                        stmt4.setInt(1, stockId);
                        int rslt4 = stmt4.executeUpdate();
                        
                        if (rslt4 > 0)
                        {
                            JOptionPane.showMessageDialog(null, "Stock deleted successfully.");
                            refreshStocks();
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
        
        stocksTableColumnModel.getColumn(5).setCellRenderer(new ActionTableCellRenderer(EditDeleteActionTablePanel.class));
        stocksTableColumnModel.getColumn(5).setCellEditor(new ActionTableCellEditor(actionTableEvent2, EditDeleteActionTablePanel.class));
        
        stocksTableColumnModel.getColumn(5).setPreferredWidth(165);
        stocksTableColumnModel.getColumn(5).setMaxWidth(165);
        stocksTableColumnModel.getColumn(5).setMinWidth(165);
        
        if (!User.getRole().equals("Admin"))
        {
            stocksTableColumnModel.removeColumn(stocksTableColumnModel.getColumn(5));
        }
        
        stocksTableColumnModel.removeColumn(stocksTableColumnModel.getColumn(0));
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
        rButton1 = new rojerusan.RSMaterialButtonRectangle();
        rButton2 = new rojerusan.RSMaterialButtonRectangle();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Stocks");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Total");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("0");

        rButton1.setText("New Stock");
        rButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton1ActionPerformed(evt);
            }
        });

        rButton2.setBackground(new java.awt.Color(51, 51, 51));
        rButton2.setText("Refresh");
        rButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton2ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());
        jPanel1.add(new edu.jawhara.view.InfinitePanel(), "infinitePanel");

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Staff", "Type", "Date", "Action", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
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
                .addComponent(jScrollPane1)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 874, Short.MAX_VALUE)
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
        CreateStockFrame createStockFrame = new CreateStockFrame();
        createStockFrame.setVisible(true);
    }//GEN-LAST:event_rButton1ActionPerformed

    private void rButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton2ActionPerformed
        refreshStocks();
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
