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
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import rojerusan.RSMaterialButtonRectangle;

/**
 *
 * @author mhmmadhaibah
 */
public class ProductsPanel extends javax.swing.JPanel {
    private static final Connection conn = MyConnection.getConnection();
    private final boolean adminFlag = User.getRole().equals("Admin");

    private DefaultTableModel productsTableModel;
    private DefaultTableColumnModel productsTableColumnModel;

    private String categorySelected;

    /**
     * Creates new form ProductsPanel
     */
    public ProductsPanel() {
        initComponents();
        refreshSuperProducts();
    }

    @Override
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);
        
        if (aFlag)
        {
            refreshSuperProducts();
        }
    }

    private void refreshSuperProducts()
    {
        rButton1.setVisible(adminFlag);
        rButton2.setVisible(adminFlag);
        
        rButton4.setVisible(false);
        rButton5.setVisible(false);
        
        cButton1.setVisible(false);
        cButton2.setVisible(false);
        cButton3.setVisible(false);
        cButton4.setVisible(false);
        cButton5.setVisible(false);
        
        refreshCategories();
        refreshProducts();
    }

    private void refreshCategories() {
        Loading.infiniteLoading(jPanel1, "chairPanel");
        
        try
        {
            RSMaterialButtonRectangle _cButton_ = new RSMaterialButtonRectangle();
            _cButton_.setText("All");
            _cButton_.setBounds(6, 6, 120, 60);
            _cButton_.addActionListener(evt -> {
                rButton4.setVisible(false);
                rButton5.setVisible(false);
                
                rTextField1.setText("");
                categorySelected = null;
                
                refreshProducts();
            });
            
            JPanel cPanel = new JPanel();
            cPanel.setLayout(null);
            cPanel.add(_cButton_);
            
            String sqlq = "SELECT * FROM categories ORDER BY id ASC";
            PreparedStatement stmt = conn.prepareStatement(sqlq);
            ResultSet rslt = stmt.executeQuery();
            
            int i = 0;
            int x = 132;
            while (rslt.next())
            {
                String categoryName = rslt.getString("name");
                
                RSMaterialButtonRectangle cButton = new RSMaterialButtonRectangle();
                cButton.setText(categoryName);
                cButton.setBounds(x, 6, 120, 60);
                cButton.addActionListener(evt -> {
                    rButton4.setVisible(adminFlag);
                    rButton5.setVisible(adminFlag);
                    
                    rTextField1.setText("");
                    categorySelected = categoryName;
                    
                    refreshProducts();
                });
                
                cPanel.add(cButton);
                x += 120;
                x += 6;
                i++;
            }
            
            cPanel.setPreferredSize(new Dimension(x, 60));
            jScrollPane1.setViewportView(cPanel);
            
            jLabel3.setText(String.valueOf(i));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void refreshProducts() {
        Loading.infiniteLoading(jPanel4, "tablePanel");
        
        productsTableModel = (DefaultTableModel) jTable1.getModel();
        productsTableColumnModel = (DefaultTableColumnModel) jTable1.getColumnModel();
        
        resetProductsTable();
        loadProductsTable();
        
        customProductsTable();
    }

    private void resetProductsTable()
    {
        productsTableModel.getDataVector().removeAllElements();
        productsTableModel.fireTableStructureChanged();
        productsTableModel.fireTableDataChanged();
        productsTableModel.setRowCount(0);
    }

    private void loadProductsTable()
    {
        try
        {
            String sqlq = "SELECT p.id, s.name AS supplier, p.name, c.name AS category, ps.quantity ";
            sqlq += "FROM products p JOIN product_stocks ps ON p.id = ps.product_id ";
            sqlq += "JOIN suppliers s ON s.id = p.supplier_id ";
            sqlq += "JOIN categories c ON c.id = p.category_id ";
            
            if (rTextField1.getText().trim().equals("0"))
            {
                sqlq += "WHERE ps.quantity <= 0 ";
            }
            else if (!rTextField1.getText().trim().isEmpty())
            {
                sqlq += "WHERE p.name LIKE '%" + rTextField1.getText().trim() + "%' ";
                sqlq += "OR s.name LIKE '%" + rTextField1.getText().trim() + "%' ";
                sqlq += "OR c.name LIKE '%" + rTextField1.getText().trim() + "%' ";
                sqlq += "OR ps.quantity LIKE '%" + rTextField1.getText().trim() + "%' ";
            }
            else if (categorySelected != null)
            {
                sqlq += "WHERE c.name = '" + categorySelected + "' ";
            }
            
            sqlq += "ORDER BY p.id ASC";
            
            PreparedStatement stmt = conn.prepareStatement(sqlq.trim());
            ResultSet rslt = stmt.executeQuery();
            
            while (rslt.next())
            {
                Object[] data = new Object[6];
                data[0] = rslt.getString("id");
                data[1] = rslt.getString("supplier");
                data[2] = rslt.getString("name");
                data[3] = rslt.getString("category");
                data[4] = rslt.getString("quantity");
                data[5] = null;
                
                productsTableModel.addRow(data);
            }
            
            jLabel6.setText(String.valueOf(productsTableModel.getRowCount()));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void customProductsTable()
    {
        ActionTableEvent actionTableEvent = new ActionTableEventAdapter() {
            @Override
            public void onEdit(int row)
            {
                int productId = Integer.parseInt(productsTableModel.getValueAt(row, 0).toString());
                
                UpdateProductFrame updateProductFrame = new UpdateProductFrame(productId);
                updateProductFrame.setVisible(true);
            }
            
            @Override
            public void onDelete(int row)
            {
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    ("Delete " + productsTableModel.getValueAt(row, 2) + " ?"),
                    "Delete Product",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION)
                {
                    int productId = Integer.parseInt(productsTableModel.getValueAt(row, 0).toString());
                    
                    try
                    {
                        String sqlq = "DELETE FROM products WHERE id = ?";
                        PreparedStatement stmt = conn.prepareStatement(sqlq);
                        
                        stmt.setInt(1, productId);
                        int rslt = stmt.executeUpdate();
                        
                        if (rslt > 0)
                        {
                            String sqlq2 = "DELETE FROM product_stocks WHERE product_id = ?";
                            PreparedStatement stmt2 = conn.prepareStatement(sqlq2);
                            
                            stmt2.setInt(1, productId);
                            stmt2.executeUpdate();
                            
                            JOptionPane.showMessageDialog(null, "Product deleted successfully.");
                            refreshProducts();
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
        
        productsTableColumnModel.getColumn(5).setCellRenderer(new ActionTableCellRenderer(EditDeleteActionTablePanel.class));
        productsTableColumnModel.getColumn(5).setCellEditor(new ActionTableCellEditor(actionTableEvent, EditDeleteActionTablePanel.class));
        
        productsTableColumnModel.getColumn(5).setPreferredWidth(165);
        productsTableColumnModel.getColumn(5).setMaxWidth(165);
        productsTableColumnModel.getColumn(5).setMinWidth(165);
        
        if (!adminFlag)
        {
            productsTableColumnModel.removeColumn(productsTableColumnModel.getColumn(5));
        }
        
        productsTableColumnModel.getColumn(4).setPreferredWidth(100);
        productsTableColumnModel.getColumn(4).setMaxWidth(100);
        productsTableColumnModel.getColumn(4).setMinWidth(100);
        
        productsTableColumnModel.getColumn(3).setPreferredWidth(200);
        productsTableColumnModel.getColumn(3).setMaxWidth(200);
        productsTableColumnModel.getColumn(3).setMinWidth(200);
        
        productsTableColumnModel.getColumn(1).setPreferredWidth(200);
        productsTableColumnModel.getColumn(1).setMaxWidth(200);
        productsTableColumnModel.getColumn(1).setMinWidth(200);
        
        productsTableColumnModel.removeColumn(productsTableColumnModel.getColumn(0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rTextField1 = new rojerusan.RSMetroTextPlaceHolder();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rButton1 = new rojerusan.RSMaterialButtonRectangle();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        cButton1 = new rojerusan.RSMaterialButtonRectangle();
        cButton2 = new rojerusan.RSMaterialButtonRectangle();
        cButton3 = new rojerusan.RSMaterialButtonRectangle();
        cButton4 = new rojerusan.RSMaterialButtonRectangle();
        cButton5 = new rojerusan.RSMaterialButtonRectangle();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rButton2 = new rojerusan.RSMaterialButtonRectangle();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        rButton3 = new rojerusan.RSMaterialButtonRectangle();
        rButton4 = new rojerusan.RSMaterialButtonRectangle();
        rButton5 = new rojerusan.RSMaterialButtonRectangle();

        rTextField1.setForeground(new java.awt.Color(51, 51, 51));
        rTextField1.setBorderColor(new java.awt.Color(51, 51, 51));
        rTextField1.setBotonColor(new java.awt.Color(51, 51, 51));
        rTextField1.setPhColor(new java.awt.Color(51, 51, 51));
        rTextField1.setPlaceholder("  Search");
        rTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rTextField1KeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Categories");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Total");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("4");

        rButton1.setText("New Category");
        rButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton1ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());
        jPanel1.add(new edu.jawhara.view.InfinitePanel(), "infinitePanel");

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1116, 72));

        cButton1.setText("All");

        cButton2.setText("Bergo");

        cButton3.setText("Dress");

        cButton4.setText("Scarf");

        cButton5.setText("Pashmina");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel2, "chairPanel");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("Products");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Total");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("0");

        rButton2.setText("New Product");
        rButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton2ActionPerformed(evt);
            }
        });

        jPanel4.setLayout(new java.awt.CardLayout());
        jPanel4.add(new edu.jawhara.view.InfinitePanel(), "infinitePanel");

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Supplier", "Product Name", "Category", "Quantity", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel5, "tablePanel");

        rButton3.setBackground(new java.awt.Color(51, 51, 51));
        rButton3.setText("Refresh");
        rButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton3ActionPerformed(evt);
            }
        });

        rButton4.setBackground(new java.awt.Color(255, 153, 0));
        rButton4.setText("Edit");
        rButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton4ActionPerformed(evt);
            }
        });

        rButton5.setBackground(new java.awt.Color(255, 0, 51));
        rButton5.setText("Delete");
        rButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)))
                    .addComponent(rButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton1ActionPerformed
        CreateCategoryFrame createCategoryFrame = new CreateCategoryFrame();
        createCategoryFrame.setVisible(true);
    }//GEN-LAST:event_rButton1ActionPerformed

    private void rButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton2ActionPerformed
        CreateProductFrame createProductFrame = new CreateProductFrame();
        createProductFrame.setVisible(true);
    }//GEN-LAST:event_rButton2ActionPerformed

    private void rButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton3ActionPerformed
        rButton4.setVisible(false);
        rButton5.setVisible(false);
        
        rTextField1.setText("");
        categorySelected = null;
        
        refreshCategories();
        refreshProducts();
    }//GEN-LAST:event_rButton3ActionPerformed

    private void rButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton4ActionPerformed
        UpdateCategoryFrame updateCategoryFrame = new UpdateCategoryFrame(categorySelected);
        updateCategoryFrame.setVisible(true);
    }//GEN-LAST:event_rButton4ActionPerformed

    private void rButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rButton5ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
            null,
            ("Delete " + categorySelected + " ?"),
            "Delete Category",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION)
        {
            try
            {
                String sqlq = "DELETE FROM categories WHERE name = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlq);
                
                stmt.setString(1, categorySelected);
                int rslt = stmt.executeUpdate();
                
                if (rslt > 0)
                {
                    JOptionPane.showMessageDialog(null, "Category deleted successfully.");
                    
                    rButton4.setVisible(false);
                    rButton5.setVisible(false);
                    categorySelected = null;
                    
                    refreshCategories();
                    refreshProducts();
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
    }//GEN-LAST:event_rButton5ActionPerformed

    private void rTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rTextField1KeyReleased
        rButton4.setVisible(false);
        rButton5.setVisible(false);
        categorySelected = null;
        
        refreshProducts();
    }//GEN-LAST:event_rTextField1KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle cButton1;
    private rojerusan.RSMaterialButtonRectangle cButton2;
    private rojerusan.RSMaterialButtonRectangle cButton3;
    private rojerusan.RSMaterialButtonRectangle cButton4;
    private rojerusan.RSMaterialButtonRectangle cButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private rojerusan.RSMaterialButtonRectangle rButton1;
    private rojerusan.RSMaterialButtonRectangle rButton2;
    private rojerusan.RSMaterialButtonRectangle rButton3;
    private rojerusan.RSMaterialButtonRectangle rButton4;
    private rojerusan.RSMaterialButtonRectangle rButton5;
    private rojerusan.RSMetroTextPlaceHolder rTextField1;
    // End of variables declaration//GEN-END:variables
}
