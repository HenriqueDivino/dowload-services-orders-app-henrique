/*
 * The MIT License
 *
 * Copyright 2024 Henrique Divino.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.com.javaproject.screens;

import java.sql.*;
import br.com.javaproject.dal.JavaConnection;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 * Usability related with Customers registers
 * @author Henrique Divino
 * @version 1.1
 */

public class CustomerScreen extends javax.swing.JInternalFrame {

    /**
     * Methods responsible for all functionality of Customers screen
     */
    
    private static final long serialVersionUID = 1L;
    
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form CustomerScreen
     */
    public CustomerScreen() {
        
        initComponents();
        
        connection = JavaConnection.connector();
        
    }

    private void add(){ // Adds customer in database customer table

        String sql = "insert into customertb(customer, address, phone, email) values(?, ?, ?, ?)";

        try{

            pst = connection.prepareStatement(sql);
            pst.setString(1, txtCustomerFieldName.getText());
            pst.setString(2, txtCustomerFieldAddress.getText());
            pst.setString(3, txtCustomerFieldPhone.getText());
            pst.setString(4, txtCustomerFieldEmail.getText());            

            if(txtCustomerFieldName.getText().isEmpty()||txtCustomerFieldPhone.getText().isEmpty()){
                
                JOptionPane.showMessageDialog(null, "Fill the required fields");
                
                blank();
                
            }else{
            
                int added = pst.executeUpdate();
                if(added > 0){

                    JOptionPane.showMessageDialog(null, "Customer added successfully");

                    blank();

                }
               
            }

        }catch(HeadlessException | SQLException e){

            JOptionPane.showMessageDialog(null, e);

        }

    }
    
    private void search (){
        
        String sql = "select customerid as ID, customer as Customer, address as Address, phone as Phone, email as Email from customertb where customer like ?";
        
        try {
            
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtCustomerFieldSearch.getText() + "%"); // Content of search box
            rs = pst.executeQuery();
            
            tbCustomerSearch.setModel(DbUtils.resultSetToTableModel(rs)); // Employ r2xml.jar library
            
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    public void fieldFill () {
        
        int fill = tbCustomerSearch.getSelectedRow();
        txtCustomerFieldId.setText(tbCustomerSearch.getModel().getValueAt(fill, 0).toString());
        txtCustomerFieldName.setText(tbCustomerSearch.getModel().getValueAt(fill, 1).toString());
        txtCustomerFieldAddress.setText(tbCustomerSearch.getModel().getValueAt(fill, 2).toString());
        txtCustomerFieldPhone.setText(tbCustomerSearch.getModel().getValueAt(fill, 3).toString());
        txtCustomerFieldEmail.setText(tbCustomerSearch.getModel().getValueAt(fill, 4).toString());
        
        btnCustomerCreate.setEnabled(false); // Disables create button to avoid duplicates
        
    }
    
    private void update (){ // Update data in database
        
        String sql = "update customertb set customer = ?, address = ?, phone = ?, email = ? where customerid = ?";
        
        try{
            
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtCustomerFieldName.getText());
            pst.setString(2, txtCustomerFieldAddress.getText());
            pst.setString(3, txtCustomerFieldPhone.getText());
            pst.setString(4, txtCustomerFieldEmail.getText());
            pst.setString(5, txtCustomerFieldId.getText());

            if(txtCustomerFieldName.getText().isEmpty()||txtCustomerFieldPhone.getText().isEmpty()){
                
                JOptionPane.showMessageDialog(null, "Fill the required fields");
                
                blank();
                
            }else{
            
                int updated = pst.executeUpdate();
                
                if(updated > 0){

                    JOptionPane.showMessageDialog(null, "Update completed successfully");

                    blank();
                    
                    btnCustomerCreate.setEnabled(true); // Reactivates create button

                }
               
            }
            
        }catch(HeadlessException | SQLException e){
            
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    private void delete(){
        
        if(txtCustomerFieldName.getText().isEmpty()||txtCustomerFieldPhone.getText().isEmpty()){
                
            JOptionPane.showMessageDialog(null, "Fill the required fields");

            blank();
                    
        }else{
                
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove customer?", "WARNING", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION){

                String sql = "delete from customertb where customerid = ?";

                try{

                    pst = connection.prepareStatement(sql);
                    pst.setString(1, txtCustomerFieldId.getText());

                    int deleted = pst.executeUpdate();
                    
                    if(deleted > 0){

                        JOptionPane.showMessageDialog(null, "Customer removed successfully");

                        blank();

                        btnCustomerCreate.setEnabled(true);

                    }

                }catch(HeadlessException | SQLException e){

                    JOptionPane.showMessageDialog(null, e);
                    
                }

            }
        
        }    
                
    }
    
    private void blank(){ // Method makes search table blank
    
        txtCustomerFieldSearch.setText(null);
        txtCustomerFieldId.setText(null);
        txtCustomerFieldName.setText(null);
        txtCustomerFieldAddress.setText(null);
        txtCustomerFieldPhone.setText(null);
        txtCustomerFieldEmail.setText(null);
        
        ((DefaultTableModel)tbCustomerSearch.getModel()).setRowCount(0);
        
    }    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCustomerName = new javax.swing.JLabel();
        txtCustomerFieldName = new javax.swing.JTextField();
        lblCustomerAsteriskRequired = new javax.swing.JLabel();
        lblCustomerAddress = new javax.swing.JLabel();
        txtCustomerFieldAddress = new javax.swing.JTextField();
        lblCustomerPhone = new javax.swing.JLabel();
        txtCustomerFieldPhone = new javax.swing.JTextField();
        lblCustomerAsteriskPhone = new javax.swing.JLabel();
        lblCustomerAsteriskName = new javax.swing.JLabel();
        lblCustomerEmail = new javax.swing.JLabel();
        txtCustomerFieldEmail = new javax.swing.JTextField();
        btnCustomerCreate = new javax.swing.JButton();
        btnCustomerUpdate = new javax.swing.JButton();
        btnCustomerDelete = new javax.swing.JButton();
        txtCustomerFieldSearch = new javax.swing.JTextField();
        lblCustomerSearch = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCustomerSearch = new javax.swing.JTable();
        lblCustomerId = new javax.swing.JLabel();
        txtCustomerFieldId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Customer");
        setPreferredSize(new java.awt.Dimension(1000, 576));

        lblCustomerName.setText("Customer");
        lblCustomerName.setMaximumSize(new java.awt.Dimension(42, 15));
        lblCustomerName.setMinimumSize(new java.awt.Dimension(42, 15));
        lblCustomerName.setPreferredSize(new java.awt.Dimension(50, 15));

        lblCustomerAsteriskRequired.setForeground(new java.awt.Color(0, 19, 106));
        lblCustomerAsteriskRequired.setText("* Required fields");

        lblCustomerAddress.setText("Address");

        lblCustomerPhone.setText("Phone");

        lblCustomerAsteriskPhone.setForeground(new java.awt.Color(0, 19, 106));
        lblCustomerAsteriskPhone.setText("*");

        lblCustomerAsteriskName.setForeground(new java.awt.Color(0, 19, 106));
        lblCustomerAsteriskName.setText("*");

        lblCustomerEmail.setText("E-mail");

        btnCustomerCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/add.png"))); // NOI18N
        btnCustomerCreate.setToolTipText("Create");
        btnCustomerCreate.setPreferredSize(new java.awt.Dimension(74, 74));
        btnCustomerCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerCreateActionPerformed(evt);
            }
        });

        btnCustomerUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/edit.png"))); // NOI18N
        btnCustomerUpdate.setToolTipText("Update");
        btnCustomerUpdate.setPreferredSize(new java.awt.Dimension(74, 74));
        btnCustomerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerUpdateActionPerformed(evt);
            }
        });

        btnCustomerDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/delete.png"))); // NOI18N
        btnCustomerDelete.setToolTipText("Delete");
        btnCustomerDelete.setPreferredSize(new java.awt.Dimension(74, 74));
        btnCustomerDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerDeleteActionPerformed(evt);
            }
        });

        txtCustomerFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomerFieldSearchKeyReleased(evt);
            }
        });

        lblCustomerSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/glass.png"))); // NOI18N
        lblCustomerSearch.setMaximumSize(new java.awt.Dimension(32, 32));
        lblCustomerSearch.setMinimumSize(new java.awt.Dimension(32, 32));
        lblCustomerSearch.setPreferredSize(new java.awt.Dimension(17, 27));

        tbCustomerSearch = new javax.swing.JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex){

                return false;

            }

        };
        tbCustomerSearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Address", "Phone"
            }
        ));
        tbCustomerSearch.setRequestFocusEnabled(false);
        tbCustomerSearch.getTableHeader().setResizingAllowed(false);
        tbCustomerSearch.getTableHeader().setReorderingAllowed(false);
        tbCustomerSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCustomerSearchMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCustomerSearch);

        lblCustomerId.setText("Customer ID");

        txtCustomerFieldId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCustomerAsteriskName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCustomerAsteriskPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCustomerId)
                            .addComponent(lblCustomerEmail)
                            .addComponent(lblCustomerAddress)
                            .addComponent(lblCustomerPhone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCustomerFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCustomerFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblCustomerAsteriskRequired)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCustomerCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(btnCustomerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(btnCustomerDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCustomerFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerAsteriskRequired)
                    .addComponent(lblCustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerFieldId, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(lblCustomerId))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCustomerFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCustomerFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCustomerAddress)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(lblCustomerAsteriskName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerAsteriskPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCustomerFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCustomerPhone)))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCustomerEmail)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCustomerCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCustomerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCustomerDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        setBounds(0, 0, 850, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCustomerCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerCreateActionPerformed
        
        add(); // Makes create button work
        
    }//GEN-LAST:event_btnCustomerCreateActionPerformed

    private void txtCustomerFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerFieldSearchKeyReleased
        
        search(); // Makes search box work
        
    }//GEN-LAST:event_txtCustomerFieldSearchKeyReleased

    private void tbCustomerSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCustomerSearchMouseClicked
        
        fieldFill(); // Makes possible see table data by click
        
    }//GEN-LAST:event_tbCustomerSearchMouseClicked

    private void btnCustomerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerUpdateActionPerformed
        
        update(); // Makes update button work
        
    }//GEN-LAST:event_btnCustomerUpdateActionPerformed

    private void btnCustomerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerDeleteActionPerformed
        
        delete(); // Makes delete button work
        
    }//GEN-LAST:event_btnCustomerDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomerCreate;
    private javax.swing.JButton btnCustomerDelete;
    private javax.swing.JButton btnCustomerUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCustomerAddress;
    public static javax.swing.JLabel lblCustomerAsteriskName;
    public static javax.swing.JLabel lblCustomerAsteriskPhone;
    public static javax.swing.JLabel lblCustomerAsteriskRequired;
    private javax.swing.JLabel lblCustomerEmail;
    private javax.swing.JLabel lblCustomerId;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblCustomerPhone;
    private javax.swing.JLabel lblCustomerSearch;
    private javax.swing.JTable tbCustomerSearch;
    private javax.swing.JTextField txtCustomerFieldAddress;
    private javax.swing.JTextField txtCustomerFieldEmail;
    private javax.swing.JTextField txtCustomerFieldId;
    private javax.swing.JTextField txtCustomerFieldName;
    private javax.swing.JTextField txtCustomerFieldPhone;
    private javax.swing.JTextField txtCustomerFieldSearch;
    // End of variables declaration//GEN-END:variables
}
