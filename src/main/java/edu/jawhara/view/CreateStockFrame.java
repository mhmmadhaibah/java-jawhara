/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.jawhara.view;

import edu.jawhara.custom.ActionTableCellEditor;
import edu.jawhara.custom.ActionTableCellRenderer;
import edu.jawhara.custom.ActionTableEvent;
import edu.jawhara.custom.ActionTableEventAdapter;
import edu.jawhara.custom.DeleteActionTablePanel;
import edu.jawhara.model.MyConnection;
import edu.jawhara.model.MyReport;
import edu.jawhara.model.User;
import edu.jawhara.model.Validator;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mhmmadhaibah
 */
public class CreateStockFrame extends javax.swing.JFrame {
    private static final Connection conn = MyConnection.getConnection();

    private final ArrayList<String[]> outletList = new ArrayList<>();
    private final ArrayList<String[]> categoryList = new ArrayList<>();
    private final ArrayList<String[]> productList = new ArrayList<>();

    private DefaultTableModel stocksTableModel;
    private DefaultTableColumnModel stocksTableColumnModel;

    /**
     * Creates new form CreateStockFrame
     */
    public CreateStockFrame() {
        initComponents();
        
        loadOutlets();
        loadCategories();
        loadProducts();
        
        loadOutletForm();
        loadCategoryForm();
        loadProductForm();
        
        stocksTableModel = (DefaultTableModel) jTable1.getModel();
        stocksTableColumnModel = (DefaultTableColumnModel) jTable1.getColumnModel();
        
        customStockDetailsTable();
    }

    private void loadOutlets()
    {
        try
        {
            String sqlq = "SELECT * FROM outlets";
            PreparedStatement stmt = conn.prepareStatement(sqlq);
            ResultSet rslt = stmt.executeQuery();
            
            while (rslt.next())
            {
                outletList.add(new String[] {
                    rslt.getString("id"),
                    rslt.getString("name")
                });
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void loadCategories()
    {
        try
        {
            String sqlq = "SELECT * FROM categories";
            PreparedStatement stmt = conn.prepareStatement(sqlq);
            ResultSet rslt = stmt.executeQuery();
            
            while (rslt.next())
            {
                categoryList.add(new String[] {
                    rslt.getString("id"),
                    rslt.getString("name")
                });
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void loadProducts()
    {
        try
        {
            String sqlq = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sqlq);
            ResultSet rslt = stmt.executeQuery();
            
            while (rslt.next())
            {
                productList.add(new String[] {
                    rslt.getString("id"),
                    rslt.getString("category_id"),
                    rslt.getString("name")
                });
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void loadOutletForm()
    {
        for (String[] outlet : outletList)
        {
            jComboBox4.addItem(outlet[1]);
        }
    }

    private void loadCategoryForm()
    {
        for (String[] category : categoryList)
        {
            jComboBox1.addItem(category[1]);
        }
    }

    private void loadProductForm()
    {
        int categoryId = 0;
        for (String[] category : categoryList)
        {
            if (category[1].equals(jComboBox1.getSelectedItem()))
            {
                categoryId = Integer.parseInt(category[0]);
                break;
            }
        }
        
        jComboBox2.removeAllItems();
        for (String[] product : productList)
        {
            if (product[1].equals(String.valueOf(categoryId)))
            {
                jComboBox2.addItem(product[2]);
            }
        }
    }

    private void customStockDetailsTable()
    {
        ActionTableEvent actionTableEvent = new ActionTableEventAdapter() {
            @Override
            public void onDelete(int row)
            {
                if (row != -1 && row < stocksTableModel.getRowCount())
                {
                    stocksTableModel.removeRow(row);
                }
            }
        };
        
        stocksTableColumnModel.getColumn(2).setCellRenderer(new ActionTableCellRenderer(DeleteActionTablePanel.class));
        stocksTableColumnModel.getColumn(2).setCellEditor(new ActionTableCellEditor(actionTableEvent, DeleteActionTablePanel.class));
        
        stocksTableColumnModel.getColumn(2).setPreferredWidth(104);
        stocksTableColumnModel.getColumn(2).setMaxWidth(104);
        stocksTableColumnModel.getColumn(2).setMinWidth(104);
        
        stocksTableColumnModel.getColumn(1).setPreferredWidth(100);
        stocksTableColumnModel.getColumn(1).setMaxWidth(100);
        stocksTableColumnModel.getColumn(1).setMinWidth(100);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        rButton1 = new rojerusan.RSMaterialButtonRectangle();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        rButton2 = new rojerusan.RSMaterialButtonRectangle();
        jLabel9 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Create New Stock");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(452, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(452, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Data Product");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Category");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox1.setFocusable(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Product Name");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox2.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Quantity");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        rButton1.setBackground(new java.awt.Color(51, 51, 51));
        rButton1.setText("Add");
        rButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Data Stock");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Type");

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IN", "OUT" }));
        jComboBox3.setSelectedItem("OUT");
        jComboBox3.setFocusable(false);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        rButton2.setBackground(new java.awt.Color(51, 51, 51));
        rButton2.setText("Submit");
        rButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Outlet");

        jComboBox4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox4.setFocusable(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Notes");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox3, 0, 200, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Stock Details");

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        loadProductForm();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void rButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton1ActionPerformed
        String fProduct = jComboBox2.getSelectedItem().toString();
        String fQuantity = jTextField1.getText().trim();
        
        if ("".equals(fQuantity) || !Validator.isNumeric(fQuantity))
        {
            JOptionPane.showMessageDialog(rButton1, "Quantity must be numeric.");
            return;
        }
        
        if (Integer.parseInt(fQuantity) <= 0)
        {
            JOptionPane.showMessageDialog(rButton1, "Quantity must be at least 1.");
            return;
        }
        
        boolean duplicate = false;
        for (int i = 0; i < stocksTableModel.getRowCount(); i++)
        {
            if (stocksTableModel.getValueAt(i, 0).toString().equals(fProduct))
            {
                duplicate = true;
                break;
            }
        }
        
        if (!duplicate)
        {
            stocksTableModel.addRow(new Object[]{fProduct, fQuantity, null});
        }
        else
        {
            JOptionPane.showMessageDialog(rButton1, "The same product already exists.");
        }
    }//GEN-LAST:event_rButton1ActionPerformed

    private void rButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton2ActionPerformed
        String fType = jComboBox3.getSelectedItem().toString();
        String fNotes = jTextArea1.getText().trim();
        
        if (stocksTableModel.getRowCount() <= 0)
        {
            JOptionPane.showMessageDialog(rButton2, "Please add stock row first.");
            return;
        }
        
        if (stocksTableModel.getRowCount() > 30)
        {
            JOptionPane.showMessageDialog(rButton2, "Stock rows cannot be more than 30.");
            return;
        }
        
        int outletId = 0;
        for (String[] outlet : outletList)
        {
            if (outlet[1].equals(jComboBox4.getSelectedItem().toString()))
            {
                outletId = Integer.parseInt(outlet[0]);
                break;
            }
        }
        
        try
        {
            String sqlq = "INSERT INTO transactions (user_id, outlet_id, type, notes) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlq, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, User.getUserId());
            stmt.setString(3, fType);
            
            if (fType.equals("OUT"))
            {
                stmt.setInt(2, outletId);
            }
            else
            {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            if (!fNotes.isEmpty())
            {
                stmt.setString(4, fNotes);
            }
            else
            {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }
            
            int rows = stmt.executeUpdate();
            ResultSet rslt = stmt.getGeneratedKeys();
            
            if (rows > 0 && rslt.next())
            {
                for (int i = 0; i < stocksTableModel.getRowCount(); i++)
                {
                    int productId = 0;
                    for (String[] product : productList)
                    {
                        if (stocksTableModel.getValueAt(i, 0).toString().equals(product[2]))
                        {
                            productId = Integer.parseInt(product[0]);
                            break;
                        }
                    }
                    
                    String sqlq2 = "INSERT INTO transaction_details (transaction_id, product_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement stmt2 = conn.prepareStatement(sqlq2);
                    
                    stmt2.setInt(1, rslt.getInt(1));
                    stmt2.setInt(2, productId);
                    stmt2.setInt(3, Integer.parseInt(stocksTableModel.getValueAt(i, 1).toString()));
                    stmt2.executeUpdate();
                    
                    String operator = fType.equals("IN") ? "+" : "-";
                    String sqlq3 = "UPDATE product_stocks SET quantity = quantity " + operator + " ? WHERE product_id = ?";
                    PreparedStatement stmt3 = conn.prepareStatement(sqlq3);
                    
                    stmt3.setInt(1, Integer.parseInt(stocksTableModel.getValueAt(i, 1).toString()));
                    stmt3.setInt(2, productId);
                    stmt3.executeUpdate();
                }
                
                MyReport.viewReport("StockTransactionsReceipt", new HashMap() {{ put("TRANSACTION_ID", rslt.getInt(1)); }}, false);
                dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(rButton2, "Something wrong!");
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(rButton2, e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_rButton2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        boolean outFlag = jComboBox3.getSelectedItem().toString().equals("OUT");
        jLabel9.setVisible(outFlag);
        jComboBox4.setVisible(outFlag);
        
        if (!getSize().equals(new Dimension(1936, 1048)))
        {
            setSize(getSize().width, (!outFlag ? 872 : 931));
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateStockFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateStockFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateStockFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateStockFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateStockFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private rojerusan.RSMaterialButtonRectangle rButton1;
    private rojerusan.RSMaterialButtonRectangle rButton2;
    // End of variables declaration//GEN-END:variables
}
