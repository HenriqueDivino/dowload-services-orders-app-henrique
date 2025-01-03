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

/**
 * Usability related with Users registers
 * @author Henrique Divino
 * @version 1.1
 */

public class UserScreen extends javax.swing.JInternalFrame{
    
    /**
     * Methods responsible for all functionality of User screen
     */

    private static final long serialVersionUID = 1L;

    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form UserScreen
     */
    public UserScreen(){

        initComponents();
        connection = JavaConnection.connector();

    }

    private void read(){ // Method makes possible database query

        String sql = "Select * from userstb where userid = ?";

        try{

            pst = connection.prepareStatement(sql);
            pst.setString(1, txtUserFieldId.getText());
            rs = pst.executeQuery();

            if(rs.next()){

                txtUserFieldName.setText(rs.getString(2));
                txtUserFieldPhone.setText(rs.getString(3));
                txtUserFieldLogin.setText(rs.getString(4));
                txtUserFieldPassword.setText(rs.getString(5));
                cbProfile.setSelectedItem(rs.getString(6)); // Code related to combo box

            }else{

                JOptionPane.showMessageDialog(null, "User is not registered");

                blank();
                
                cbProfile.setSelectedItem(null);

            }

        }catch(HeadlessException | SQLException e){

            JOptionPane.showMessageDialog(null, e);

        }

    }

    private void add(){ // Adds user in database user table

        String sql = "insert into userstb(userid, user, phone, login, password, profile) values(?, ?, ?, ?, ?, ?)";

        try{

            pst = connection.prepareStatement(sql);
            pst.setString(1, txtUserFieldId.getText());
            pst.setString(2, txtUserFieldName.getText());
            pst.setString(3, txtUserFieldPhone.getText());
            pst.setString(4, txtUserFieldLogin.getText());
            pst.setString(5, txtUserFieldPassword.getText());
            pst.setString(6, (String) cbProfile.getSelectedItem());

            if(txtUserFieldId.getText().isEmpty()||txtUserFieldName.getText().isEmpty()||txtUserFieldLogin.getText().isEmpty()||txtUserFieldPassword.getText().isEmpty()){
                
                JOptionPane.showMessageDialog(null, "Fill in the required fields");
                
                txtUserFieldId.setText(null);
                txtUserFieldName.setText(null);
                txtUserFieldPhone.setText(null);
                txtUserFieldLogin.setText(null);
                txtUserFieldPassword.setText(null);
                cbProfile.setSelectedItem(null);
                
            }else{
            
                int added = pst.executeUpdate();
                if(added > 0){

                    JOptionPane.showMessageDialog(null, "User added successfully");

                    blank();
                    
                    cbProfile.setSelectedItem(null);

                }
               
            }

        }catch(HeadlessException | SQLException e){

            JOptionPane.showMessageDialog(null, e);

        }

    }

    private void update(){ // Update data in database
     
        String sql = "update userstb set user = ?, phone = ?,login = ?, password = ?, profile = ? where userid = ?";
        
        try{
            
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtUserFieldName.getText());
            pst.setString(2, txtUserFieldPhone.getText());
            pst.setString(3, txtUserFieldLogin.getText());
            pst.setString(4, txtUserFieldPassword.getText());
            pst.setString(5, (String) cbProfile.getSelectedItem());
            pst.setString(6, txtUserFieldId.getText());

            if(txtUserFieldId.getText().isEmpty()||txtUserFieldName.getText().isEmpty()||txtUserFieldLogin.getText().isEmpty()||txtUserFieldPassword.getText().isEmpty()){
                
                JOptionPane.showMessageDialog(null, "Fill in the required fields");
                
                txtUserFieldId.setText(null);
                txtUserFieldName.setText(null);
                txtUserFieldPhone.setText(null);
                txtUserFieldLogin.setText(null);
                txtUserFieldPassword.setText(null);
                cbProfile.setSelectedItem(null);
                
            }else{
            
                int updated = pst.executeUpdate();
                
                if(updated > 0){

                    JOptionPane.showMessageDialog(null, "Update completed successfully");

                    blank();
                    
                    cbProfile.setSelectedItem(null);

                }
               
            }
            
        }catch(HeadlessException | SQLException e){
            
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    private void delete(){
        
        if(txtUserFieldId.getText().isEmpty()||txtUserFieldName.getText().isEmpty()||txtUserFieldLogin.getText().isEmpty()||txtUserFieldPassword.getText().isEmpty()||cbProfile.getSelectedItem() == null) {
            
            JOptionPane.showMessageDialog(null, "Fill in the required fields");
            
        }else{
        
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove user?", "WARNING", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_NO_OPTION){

                String sql = "delete from userstb where userid = ?";

                try{

                    pst = connection.prepareStatement(sql);
                    pst.setString(1, txtUserFieldId.getText());

                    int deleted = pst.executeUpdate();

                    if(deleted > 0){

                        JOptionPane.showMessageDialog(null, "User removed successfully");

                        blank();

                        cbProfile.setSelectedItem(null);

                    }

                }catch(HeadlessException | SQLException e){

                    JOptionPane.showMessageDialog(null, e);

                }
                
            }
            
        }
        
    }
        
    private void blank(){
    
        txtUserFieldId.setText(null);
        txtUserFieldName.setText(null);
        txtUserFieldPhone.setText(null);
        txtUserFieldLogin.setText(null);
        txtUserFieldPassword.setText(null);
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUserId = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblUserPassword = new javax.swing.JLabel();
        lblProfile = new javax.swing.JLabel();
        txtUserFieldId = new javax.swing.JTextField();
        txtUserFieldName = new javax.swing.JTextField();
        txtUserFieldPhone = new javax.swing.JTextField();
        txtUserFieldLogin = new javax.swing.JTextField();
        txtUserFieldPassword = new javax.swing.JTextField();
        cbProfile = new javax.swing.JComboBox<>();
        btnUserCreate = new javax.swing.JButton();
        btnUserUpdate = new javax.swing.JButton();
        btnUserRead = new javax.swing.JButton();
        btnUserDelete = new javax.swing.JButton();
        lblUserAsteriskRequirement = new javax.swing.JLabel();
        lblUserAsteriskId = new javax.swing.JLabel();
        lblUserAsteriskName = new javax.swing.JLabel();
        lblUserAsteriskLogin = new javax.swing.JLabel();
        lblUserAsteriskPassword = new javax.swing.JLabel();
        lblUserAsteriskProfile = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setClosable(true);
        setForeground(java.awt.Color.white);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("User");
        setName("User"); // NOI18N
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1000, 576));
        setVisible(true);

        lblUserId.setText("ID");

        lblUser.setText("Name");

        lblPhone.setText("Phone");

        lblLogin.setText("Login");

        lblUserPassword.setText("Password");

        lblProfile.setText("Profile");

        cbProfile.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { null, "Admin", "Regular" }));

        btnUserCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/add.png"))); // NOI18N
        btnUserCreate.setToolTipText("Add");
        btnUserCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserCreate.setPreferredSize(new java.awt.Dimension(74, 74));
        btnUserCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCreateActionPerformed(evt);
            }
        });

        btnUserUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/edit.png"))); // NOI18N
        btnUserUpdate.setToolTipText("Update");
        btnUserUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserUpdate.setPreferredSize(new java.awt.Dimension(74, 74));
        btnUserUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserUpdateActionPerformed(evt);
            }
        });

        btnUserRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/search.png"))); // NOI18N
        btnUserRead.setToolTipText("Read");
        btnUserRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserRead.setPreferredSize(new java.awt.Dimension(74, 74));
        btnUserRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserReadActionPerformed(evt);
            }
        });

        btnUserDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/delete.png"))); // NOI18N
        btnUserDelete.setToolTipText("Delete");
        btnUserDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserDelete.setPreferredSize(new java.awt.Dimension(74, 74));
        btnUserDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDeleteActionPerformed(evt);
            }
        });

        lblUserAsteriskRequirement.setForeground(new java.awt.Color(0, 19, 106));
        lblUserAsteriskRequirement.setText("* Required fields");

        lblUserAsteriskId.setForeground(new java.awt.Color(0, 19, 106));
        lblUserAsteriskId.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUserAsteriskId.setText("*");
        lblUserAsteriskId.setPreferredSize(new java.awt.Dimension(4, 25));

        lblUserAsteriskName.setForeground(new java.awt.Color(0, 19, 106));
        lblUserAsteriskName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUserAsteriskName.setText("*");
        lblUserAsteriskName.setPreferredSize(new java.awt.Dimension(4, 65));

        lblUserAsteriskLogin.setForeground(new java.awt.Color(0, 19, 106));
        lblUserAsteriskLogin.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUserAsteriskLogin.setText("*");
        lblUserAsteriskLogin.setPreferredSize(new java.awt.Dimension(4, 25));

        lblUserAsteriskPassword.setForeground(new java.awt.Color(0, 19, 106));
        lblUserAsteriskPassword.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUserAsteriskPassword.setText("*");
        lblUserAsteriskPassword.setPreferredSize(new java.awt.Dimension(4, 25));

        lblUserAsteriskProfile.setForeground(new java.awt.Color(0, 19, 106));
        lblUserAsteriskProfile.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUserAsteriskProfile.setText("*");
        lblUserAsteriskProfile.setPreferredSize(new java.awt.Dimension(4, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnUserRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUserAsteriskPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserAsteriskId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUserPassword)
                    .addComponent(lblPhone)
                    .addComponent(lblUserId))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUserFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(lblUserAsteriskProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUserFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(lblUserAsteriskLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtUserFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127)
                        .addComponent(lblUserAsteriskName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLogin)
                            .addComponent(lblProfile))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUserFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(206, 206, 206))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserAsteriskRequirement)
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(lblUserAsteriskRequirement)
                        .addGap(73, 73, 73))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUserAsteriskName, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblUserAsteriskId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(34, 34, 34)))
                .addComponent(lblUserAsteriskLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUserAsteriskPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserAsteriskProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(281, 281, 281))
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUserId)
                        .addComponent(txtUserFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUser)
                        .addComponent(txtUserFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhone)
                    .addComponent(txtUserFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogin)
                    .addComponent(txtUserFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserPassword)
                    .addComponent(txtUserFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProfile)
                    .addComponent(cbProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUserRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );

        setBounds(0, 0, 850, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUserReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserReadActionPerformed

        read(); // Makes read button work

    }//GEN-LAST:event_btnUserReadActionPerformed

    private void btnUserCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCreateActionPerformed

        add(); // Makes create button work

    }//GEN-LAST:event_btnUserCreateActionPerformed

    private void btnUserUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserUpdateActionPerformed
        
        update(); // Makes update button work
        
    }//GEN-LAST:event_btnUserUpdateActionPerformed

    private void btnUserDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDeleteActionPerformed
        delete(); // Makes delete button work
    }//GEN-LAST:event_btnUserDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btnUserCreate;
    private static javax.swing.JButton btnUserDelete;
    private static javax.swing.JButton btnUserRead;
    private static javax.swing.JButton btnUserUpdate;
    private static javax.swing.JComboBox<String> cbProfile;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblUser;
    private static javax.swing.JLabel lblUserAsteriskId;
    private static javax.swing.JLabel lblUserAsteriskLogin;
    private static javax.swing.JLabel lblUserAsteriskName;
    private static javax.swing.JLabel lblUserAsteriskPassword;
    private static javax.swing.JLabel lblUserAsteriskProfile;
    private static javax.swing.JLabel lblUserAsteriskRequirement;
    private javax.swing.JLabel lblUserId;
    private javax.swing.JLabel lblUserPassword;
    private javax.swing.JTextField txtUserFieldId;
    private static javax.swing.JTextField txtUserFieldLogin;
    private static javax.swing.JTextField txtUserFieldName;
    private static javax.swing.JTextField txtUserFieldPassword;
    private static javax.swing.JTextField txtUserFieldPhone;
    // End of variables declaration//GEN-END:variables
}
