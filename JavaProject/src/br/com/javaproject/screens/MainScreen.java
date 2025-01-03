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

import br.com.javaproject.dal.JavaConnection;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Usability related with Main screen
 * @author Henrique Divino
 * @version 1.1
 */
public class MainScreen extends javax.swing.JFrame {
    
    /**
     * Methods responsible for all functionality of Main screen
     */

    private static final long serialVersionUID = 1L;

   Connection connection = null;
   PreparedStatement pst = null;
   ResultSet rs = null;
    
    /**
     * Creates new form MainScreen
     */
    public MainScreen() {
        
        initComponents();
        
        connection = JavaConnection.connector();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneDesktop = new javax.swing.JDesktopPane();
        lblLogo = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuRegister = new javax.swing.JMenu();
        menuItemRegisterCustomers = new javax.swing.JMenuItem();
        menuItemSO = new javax.swing.JMenuItem();
        menuItemUsers = new javax.swing.JMenuItem();
        menuReport = new javax.swing.JMenu();
        menuItemReportCustomers = new javax.swing.JMenuItem();
        menuItemServices = new javax.swing.JMenuItem();
        menuOptions = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SO System - Main Screen");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        paneDesktop.setBackground(new java.awt.Color(0, 0, 0));
        paneDesktop.setForeground(new java.awt.Color(0, 0, 0));
        paneDesktop.setPreferredSize(new java.awt.Dimension(1000, 576));

        javax.swing.GroupLayout paneDesktopLayout = new javax.swing.GroupLayout(paneDesktop);
        paneDesktop.setLayout(paneDesktopLayout);
        paneDesktopLayout.setHorizontalGroup(
            paneDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        paneDesktopLayout.setVerticalGroup(
            paneDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/wave.png"))); // NOI18N
        lblLogo.setPreferredSize(new java.awt.Dimension(16, 16));

        lblUser.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("User");

        lblDate.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblDate.setText("Date");

        menuRegister.setText("Register");

        menuItemRegisterCustomers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        menuItemRegisterCustomers.setText("Customers");
        menuItemRegisterCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRegisterCustomersActionPerformed(evt);
            }
        });
        menuRegister.add(menuItemRegisterCustomers);

        menuItemSO.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        menuItemSO.setText("SO");
        menuItemSO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSOActionPerformed(evt);
            }
        });
        menuRegister.add(menuItemSO);

        menuItemUsers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        menuItemUsers.setText("Users");
        menuItemUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemUsersActionPerformed(evt);
            }
        });
        menuRegister.add(menuItemUsers);

        menuBar.add(menuRegister);

        menuReport.setText("Report");

        menuItemReportCustomers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        menuItemReportCustomers.setText("Customers");
        menuItemReportCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemReportCustomersActionPerformed(evt);
            }
        });
        menuReport.add(menuItemReportCustomers);

        menuItemServices.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        menuItemServices.setText("Services");
        menuItemServices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemServicesActionPerformed(evt);
            }
        });
        menuReport.add(menuItemServices);

        menuBar.add(menuReport);

        menuOptions.setText("Options");

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuOptions.add(menuItemExit);

        menuBar.add(menuOptions);

        menuHelp.setText("Help");

        menuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        menuItemAbout.setText("About");
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblDate)
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblUser)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(lblUser)
                .addGap(15, 15, 15)
                .addComponent(lblDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        setSize(new java.awt.Dimension(1216, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        
        // Shows a dialog box containing Yes and No options
        int exit = JOptionPane.showConfirmDialog(null, "Do you really want to exit ?", "Attention", JOptionPane.YES_NO_OPTION);
        
        if(exit == JOptionPane.YES_OPTION){
            
            System.exit(0);
        
        }
        
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        
        // Requests About window
        
        AboutScreen about = new AboutScreen();
        about.setVisible(true);
        
    }//GEN-LAST:event_menuItemAboutActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        // Changes lblDate to current date
        
        Date date = new Date();
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
        lblDate.setText(formatter.format(date));
        
    }//GEN-LAST:event_formWindowActivated

    private void menuItemUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemUsersActionPerformed
        
        UserScreen user = new UserScreen();
        user.setVisible(true);
        paneDesktop.add(user);
        
    }//GEN-LAST:event_menuItemUsersActionPerformed

    private void menuItemRegisterCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRegisterCustomersActionPerformed
        
        CustomerScreen customer = new CustomerScreen(); // Makes customer menu button work
        customer.setVisible(true);
        paneDesktop.add(customer);
        
    }//GEN-LAST:event_menuItemRegisterCustomersActionPerformed

    private void menuItemSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSOActionPerformed
        
        // Makes SO button works
        SoScreen sO = new SoScreen();
        sO.setVisible(true);
        paneDesktop.add(sO);
        
    }//GEN-LAST:event_menuItemSOActionPerformed

    private void menuItemReportCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemReportCustomersActionPerformed
        
        // Makes print button work
        int confirm = JOptionPane.showConfirmDialog(null, "Confirm report print?", "WARNING", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            // Prints report with Jasper Framework
            try {
                
                // Use of JasperPrint class
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/ireport/Customer Report.jasper"), null, connection);
                
                // Use of JAsperViewer
                JasperViewer.viewReport(print, false);
                
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, e);
                
            }
            
        }
                 
    }//GEN-LAST:event_menuItemReportCustomersActionPerformed

    private void menuItemServicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemServicesActionPerformed
        
        int confirm = JOptionPane.showConfirmDialog(null, "Confirm report print?", "WARNING", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            try {
                
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/ireport/Services.jasper"), null, connection);
                
                JasperViewer.viewReport(print, false);
                
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, e);
                
            }
            
        }
           
    }//GEN-LAST:event_menuItemServicesActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainScreen().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel lblDate;
    public static javax.swing.JLabel lblLogo;
    public static javax.swing.JLabel lblUser;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemRegisterCustomers;
    private javax.swing.JMenuItem menuItemReportCustomers;
    private javax.swing.JMenuItem menuItemSO;
    private javax.swing.JMenuItem menuItemServices;
    public static javax.swing.JMenuItem menuItemUsers;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JMenu menuRegister;
    public static javax.swing.JMenu menuReport;
    private javax.swing.JDesktopPane paneDesktop;
    // End of variables declaration//GEN-END:variables
}