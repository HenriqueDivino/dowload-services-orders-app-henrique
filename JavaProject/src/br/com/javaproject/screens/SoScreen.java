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
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Usability for Service Orders related with customers
 * @author Henrique Divino
 * @version 1.1
 */

public class SoScreen extends javax.swing.JInternalFrame {
    
    /**
     * Methods responsible for all functionality of Service Orders screen
     */
    
    private static final long serialVersionUID = 1L;
    
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private String type;
    
    /**
     * Creates new form SoScreen
     */
    public SoScreen() {
        
        initComponents();
        
        connection = JavaConnection.connector();
        
    }
    
    private void search(){
        
        String sql = "select customerid as ID, customer as Customer, phone as Phone from customertb where customer like ?";
              
        try {
           
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtSoFieldSearch.getText() + "%");
            rs = pst.executeQuery();
            
            tbSoCustomers.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
            
        }
    }

    public void fieldFill () {
        
        int fill = tbSoCustomers.getSelectedRow();
        txtSoFieldId.setText(tbSoCustomers.getModel().getValueAt(fill, 0).toString());
        
    }
    
    private void create(){
        
        String sql = "insert into sotb(type, situation, equipment, defect, service, technician, value, customerid) values(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            
            pst = connection.prepareStatement(sql);
            pst.setString(1, type);
            pst.setString(2, cbSoSituation.getSelectedItem().toString());
            pst.setString(3, txtSoFieldEquipment.getText());
            pst.setString(4, txtSoFieldDefect.getText());
            pst.setString(5, txtSoFieldService.getText());
            pst.setString(6, txtSoFieldTechnician.getText());
            pst.setString(7, txtSoFieldValue.getText().replace(",", "."));
            pst.setString(8, txtSoFieldId.getText());
            
            // Code below checks required data       
            if (txtSoFieldId.getText().isEmpty()||txtSoFieldEquipment.getText().isEmpty()||txtSoFieldDefect.getText().isEmpty()||cbSoSituation.getSelectedItem().equals(" ")) {
                
                JOptionPane.showMessageDialog(null, "Fill the required fields");
                
            } else {
                
                int created = pst.executeUpdate();
                
                if (created > 0) {
                    
                    JOptionPane.showMessageDialog(null, "Service Order created succesfully");
                    
                    sOretrieve ();
                    
                    btnSoUpdate.setEnabled(false);
                    btnSoDelete.setEnabled(false);
                    btnSoPrint.setEnabled(true);
                    
                }
                
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    private void read(){
        
        String sOnum = JOptionPane.showInputDialog("SO Number");
        
        String sql = "select soid, date_format(sodate, '%d/%m/%Y - %H:%i'), type, situation, equipment, defect, service, technician, value, customerid from sotb where soid = " + sOnum;
        
        try {
            
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                
                txtSoFieldSoN.setText(rs.getString(1));
                txtSoFieldDate.setText(rs.getString(2));
                
                String rbtnType = rs.getString(3); // Retrieves radio buttons data
                
                if (rbtnType.equals("Budget")) {
                    
                    rBtnSoBudget.setSelected(true);
                    type = "Budget";
                    
                } else {
                    
                    rBtnSoServiceOrder.setSelected(true);
                    type = "Service Order";
                    
                }
                
                cbSoSituation.setSelectedItem(rs.getString(4));
                txtSoFieldEquipment.setText(rs.getString(5));
                txtSoFieldDefect.setText(rs.getString(6));
                txtSoFieldService.setText(rs.getString(7));
                txtSoFieldTechnician.setText(rs.getString(8));
                txtSoFieldValue.setText(rs.getString(9));
                txtSoFieldId.setText(rs.getString(10));
                
                // Deactivates functions
                btnSoCreate.setEnabled(false);
                btnSoRead.setEnabled(false);
                txtSoFieldSearch.setEditable(false);
                tbSoCustomers.setVisible(false);
                
                // Activates functions
                btnSoUpdate.setEnabled((true));
                btnSoDelete.setEnabled((true));
                btnSoPrint.setEnabled((true));
                
            } else {
                
                JOptionPane.showMessageDialog(null, "SO does not exist");
                
            }
            
            
        } catch (SQLSyntaxErrorException e) {
            
            JOptionPane.showMessageDialog(null, "Invalid SO");
            
        } catch (Exception e2) {
            
            JOptionPane.showMessageDialog(null, e2);
            
        }
        
    }
    
    private void update () {
        
        String sql = "update sotb set type = ?, situation = ?, equipment = ?, defect = ?, service = ?, technician = ?, value = ? where soid = ?";
        
        try {
            
            pst = connection.prepareStatement(sql);
            pst.setString(1, type);
            pst.setString(2, cbSoSituation.getSelectedItem().toString());
            pst.setString(3, txtSoFieldEquipment.getText());
            pst.setString(4, txtSoFieldDefect.getText());
            pst.setString(5, txtSoFieldService.getText());
            pst.setString(6, txtSoFieldTechnician.getText());
            pst.setString(7, txtSoFieldValue.getText());
            pst.setString(8, txtSoFieldSoN.getText());
            
            if (txtSoFieldId.getText().isEmpty()||txtSoFieldEquipment.getText().isEmpty()||txtSoFieldDefect.getText().isEmpty()||cbSoSituation.getSelectedItem().equals(" ")) {
                
                JOptionPane.showMessageDialog(null, "Fill the required fields");
                
            } else {
                
                int updated = pst.executeUpdate();
                
                if (updated > 0) {
                    
                    JOptionPane.showMessageDialog(null, "Service Order updated succesfully");

                    blank();
                    
                }
                
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    private void delete () {
        
        if(txtSoFieldId.getText().isEmpty()||txtSoFieldEquipment.getText().isEmpty()||txtSoFieldDefect.getText().isEmpty()){
            
            JOptionPane.showMessageDialog(null, "Fill the required fields");
            
            blank();
            
        } else {
            
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove service order?", "WARNING", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                
                String sql = "delete from sotb where soid = ?";
                
                try {
                    
                    pst = connection.prepareStatement(sql);
                    pst.setString(1, txtSoFieldSoN.getText());
                    
                    int deleted = pst.executeUpdate();
                    
                    if (deleted > 0) {
                        
                        JOptionPane.showMessageDialog(null, "SO deleted succesfully");
                        
                        blank();
                        
                    }
                    
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(null, e);
                    
                }
                                       
            }
            
        }
                  
    }
    
    private void print() {
        
        int confirm = JOptionPane.showConfirmDialog(null, "Confirm Service Order print?", "WARNING", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            try {
                
                Map<String, Object> filter = new HashMap<>();
                filter.put("soid", Integer.parseInt(txtSoFieldSoN.getText()));
                
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/ireport/so.jasper"), filter, connection);
                
                JasperViewer.viewReport(print, false);
                
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, e);
                
            }
            
        }
        
    }
    
    private void sOretrieve () {
        
        String sql = "select max (soid) from sotb";
        
        try {
            
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                
                txtSoFieldSoN.setText(rs.getString(1));
                
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    private void blank(){
        
        txtSoFieldSoN.setText(null);
        txtSoFieldDate.setText(null);
        cbSoSituation.setSelectedItem(" ");
        txtSoFieldSearch.setText(null);
        txtSoFieldId.setText(null);
        ((DefaultTableModel)tbSoCustomers.getModel()).setRowCount(0);
        txtSoFieldEquipment.setText(null);
        txtSoFieldDefect.setText(null);
        txtSoFieldService.setText(null);
        txtSoFieldTechnician.setText(null);
        txtSoFieldValue.setText(null);

        btnSoCreate.setEnabled(true);
        btnSoRead.setEnabled(true);
        txtSoFieldSearch.setEnabled(true);
        tbSoCustomers.setVisible(true);
        btnSoUpdate.setEnabled(false);
        btnSoDelete.setEnabled(false);
        btnSoPrint.setEnabled(false);
                
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdSoBtnGroup = new javax.swing.ButtonGroup();
        pnSo = new javax.swing.JPanel();
        lblSoNumber = new javax.swing.JLabel();
        lblSoDate = new javax.swing.JLabel();
        txtSoFieldDate = new javax.swing.JTextField();
        txtSoFieldSoN = new javax.swing.JTextField();
        rBtnSoBudget = new javax.swing.JRadioButton();
        rBtnSoServiceOrder = new javax.swing.JRadioButton();
        lblSoSituation = new javax.swing.JLabel();
        cbSoSituation = new javax.swing.JComboBox<>();
        lblSoAsteriskDefect = new javax.swing.JLabel();
        pnSoCustomers = new javax.swing.JPanel();
        txtSoFieldSearch = new javax.swing.JTextField();
        lblSoIconSearch = new javax.swing.JLabel();
        sPsO = new javax.swing.JScrollPane();
        tbSoCustomers = new javax.swing.JTable();
        lblSoAsteriskId = new javax.swing.JLabel();
        lblSoId = new javax.swing.JLabel();
        txtSoFieldId = new javax.swing.JTextField();
        lblSoAsteriskEquipment = new javax.swing.JLabel();
        lblSoEquipment = new javax.swing.JLabel();
        txtSoFieldEquipment = new javax.swing.JTextField();
        lblSoDefect = new javax.swing.JLabel();
        txtSoFieldDefect = new javax.swing.JTextField();
        lblSoService = new javax.swing.JLabel();
        txtSoFieldService = new javax.swing.JTextField();
        txtSoFieldTechnician = new javax.swing.JTextField();
        lblSoTechnician = new javax.swing.JLabel();
        txtSoFieldValue = new javax.swing.JTextField();
        lblSoValue = new javax.swing.JLabel();
        btnSoCreate = new javax.swing.JButton();
        btnSoRead = new javax.swing.JButton();
        btnSoUpdate = new javax.swing.JButton();
        btnSoDelete = new javax.swing.JButton();
        btnSoPrint = new javax.swing.JButton();
        lblSoAsteriskRequired = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("SO");
        setPreferredSize(new java.awt.Dimension(1000, 576));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        pnSo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblSoNumber.setText("SO Nº");

        lblSoDate.setText("Date");

        txtSoFieldDate.setEditable(false);

        txtSoFieldSoN.setEditable(false);

        rdSoBtnGroup.add(rBtnSoBudget);
        rBtnSoBudget.setText("Budget");

        rdSoBtnGroup.add(rBtnSoServiceOrder);
        rBtnSoServiceOrder.setText("Service Order");
        rBtnSoServiceOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBtnSoServiceOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnSoLayout = new javax.swing.GroupLayout(pnSo);
        pnSo.setLayout(pnSoLayout);
        pnSoLayout.setHorizontalGroup(
            pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtSoFieldSoN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnSoLayout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(lblSoNumber)))
                    .addComponent(rBtnSoBudget))
                .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSoLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnSoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtSoFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(pnSoLayout.createSequentialGroup()
                                .addComponent(lblSoDate)
                                .addGap(55, 55, 55))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rBtnSoServiceOrder)
                        .addGap(25, 25, 25))))
        );
        pnSoLayout.setVerticalGroup(
            pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSoNumber)
                    .addComponent(lblSoDate))
                .addGap(18, 18, 18)
                .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoFieldSoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(pnSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBtnSoBudget)
                    .addComponent(rBtnSoServiceOrder))
                .addGap(24, 24, 24))
        );

        lblSoSituation.setText("Situation");

        cbSoSituation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Delivery OK", "Reproved Budget", "Waiting Approving", "Waiting Pieces", "Customer Abandon", "At Bench", "Returned" }));

        lblSoAsteriskDefect.setForeground(new java.awt.Color(0, 19, 106));
        lblSoAsteriskDefect.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoAsteriskDefect.setText("*");
        lblSoAsteriskDefect.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblSoAsteriskDefect.setAlignmentY(0.0F);
        lblSoAsteriskDefect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblSoAsteriskDefect.setPreferredSize(new java.awt.Dimension(8, 32));

        pnSoCustomers.setBorder(javax.swing.BorderFactory.createTitledBorder("Customers"));
        pnSoCustomers.setPreferredSize(new java.awt.Dimension(600, 100));

        txtSoFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoFieldSearchKeyReleased(evt);
            }
        });

        lblSoIconSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/glass.png"))); // NOI18N

        tbSoCustomers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Customer", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSoCustomers.setEditingColumn(0);
        tbSoCustomers.setEditingRow(0);
        tbSoCustomers.getTableHeader().setResizingAllowed(false);
        tbSoCustomers.getTableHeader().setReorderingAllowed(false);
        tbSoCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSoCustomersMouseClicked(evt);
            }
        });
        sPsO.setViewportView(tbSoCustomers);

        lblSoAsteriskId.setForeground(new java.awt.Color(0, 19, 106));
        lblSoAsteriskId.setText("*");

        lblSoId.setText("ID");

        txtSoFieldId.setEditable(false);

        javax.swing.GroupLayout pnSoCustomersLayout = new javax.swing.GroupLayout(pnSoCustomers);
        pnSoCustomers.setLayout(pnSoCustomersLayout);
        pnSoCustomersLayout.setHorizontalGroup(
            pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSoCustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSoCustomersLayout.createSequentialGroup()
                        .addComponent(sPsO, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnSoCustomersLayout.createSequentialGroup()
                        .addComponent(txtSoFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoIconSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSoAsteriskId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
        );
        pnSoCustomersLayout.setVerticalGroup(
            pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSoCustomersLayout.createSequentialGroup()
                .addGroup(pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSoCustomersLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblSoId)
                                .addComponent(txtSoFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSoCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSoFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblSoIconSearch))))
                    .addGroup(pnSoCustomersLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblSoAsteriskId, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(sPsO, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        lblSoAsteriskEquipment.setForeground(new java.awt.Color(0, 19, 106));
        lblSoAsteriskEquipment.setText("*");
        lblSoAsteriskEquipment.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblSoAsteriskEquipment.setAlignmentY(0.0F);
        lblSoAsteriskEquipment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblSoEquipment.setText("Equipment");
        lblSoEquipment.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblSoEquipment.setAlignmentY(0.0F);
        lblSoEquipment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblSoDefect.setText("Defect");

        lblSoService.setText("Service");

        lblSoTechnician.setText("Technician");

        txtSoFieldValue.setText("0");

        lblSoValue.setText("Value");

        btnSoCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/add.png"))); // NOI18N
        btnSoCreate.setToolTipText("Create");
        btnSoCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSoCreate.setPreferredSize(new java.awt.Dimension(74, 74));
        btnSoCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoCreateActionPerformed(evt);
            }
        });

        btnSoRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/search.png"))); // NOI18N
        btnSoRead.setToolTipText("Read");
        btnSoRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSoRead.setPreferredSize(new java.awt.Dimension(74, 74));
        btnSoRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoReadActionPerformed(evt);
            }
        });

        btnSoUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/edit.png"))); // NOI18N
        btnSoUpdate.setToolTipText("Update");
        btnSoUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSoUpdate.setEnabled(false);
        btnSoUpdate.setPreferredSize(new java.awt.Dimension(74, 74));
        btnSoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoUpdateActionPerformed(evt);
            }
        });

        btnSoDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/delete.png"))); // NOI18N
        btnSoDelete.setToolTipText("Delete");
        btnSoDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSoDelete.setEnabled(false);
        btnSoDelete.setPreferredSize(new java.awt.Dimension(74, 74));
        btnSoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoDeleteActionPerformed(evt);
            }
        });

        btnSoPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/javaproject/icons/printer.png"))); // NOI18N
        btnSoPrint.setToolTipText("Print SO");
        btnSoPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSoPrint.setEnabled(false);
        btnSoPrint.setPreferredSize(new java.awt.Dimension(74, 74));
        btnSoPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoPrintActionPerformed(evt);
            }
        });

        lblSoAsteriskRequired.setForeground(new java.awt.Color(0, 19, 106));
        lblSoAsteriskRequired.setText("* Required fields");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSoAsteriskDefect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoAsteriskEquipment))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSoService)
                        .addGap(28, 28, 28)
                        .addComponent(txtSoFieldService, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSoDefect)
                        .addGap(33, 33, 33)
                        .addComponent(txtSoFieldDefect, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSoEquipment)
                        .addGap(8, 8, 8)
                        .addComponent(txtSoFieldEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSoValue)
                        .addGap(37, 37, 37)
                        .addComponent(txtSoFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSoTechnician)
                        .addGap(8, 8, 8)
                        .addComponent(txtSoFieldTechnician, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSoCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSoRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSoUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSoPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblSoSituation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSoSituation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnSoCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblSoAsteriskRequired)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnSoCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                        .addComponent(lblSoAsteriskRequired))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoSituation)
                            .addComponent(cbSoSituation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSoRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSoCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSoUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSoDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSoPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSoEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoFieldEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSoDefect)
                                    .addComponent(txtSoFieldDefect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSoAsteriskEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(lblSoAsteriskDefect, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoService)
                            .addComponent(txtSoFieldService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoTechnician)
                            .addComponent(txtSoFieldTechnician, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoValue)
                            .addComponent(txtSoFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        setBounds(0, 0, 1000, 578);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSoFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoFieldSearchKeyReleased
        
        search(); // Makes search box work
        
    }//GEN-LAST:event_txtSoFieldSearchKeyReleased

    private void tbSoCustomersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSoCustomersMouseClicked
        
        fieldFill(); // Shows ID after click
        
    }//GEN-LAST:event_tbSoCustomersMouseClicked

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
        rBtnSoBudget.setSelected(true); // Makes one radio button selected when window opens
        type = "Budget";
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void rBtnSoServiceOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rBtnSoServiceOrderActionPerformed
        
        rBtnSoServiceOrder.setSelected(true); // Makes another radio button selected when window opens
        type = "Service Order";
        
    }//GEN-LAST:event_rBtnSoServiceOrderActionPerformed

    private void btnSoCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoCreateActionPerformed
        
        create(); // Makes create button work
        
    }//GEN-LAST:event_btnSoCreateActionPerformed

    private void btnSoReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoReadActionPerformed
        
        read(); // Makes read button work
        
    }//GEN-LAST:event_btnSoReadActionPerformed

    private void btnSoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoUpdateActionPerformed
        
        update(); // Makes update button work
        
    }//GEN-LAST:event_btnSoUpdateActionPerformed

    private void btnSoDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoDeleteActionPerformed
        
        delete(); // Makes delete button work
        
    }//GEN-LAST:event_btnSoDeleteActionPerformed

    private void btnSoPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoPrintActionPerformed
        
        print(); // Makes print button work
        
    }//GEN-LAST:event_btnSoPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSoCreate;
    private javax.swing.JButton btnSoDelete;
    private javax.swing.JButton btnSoPrint;
    private javax.swing.JButton btnSoRead;
    private javax.swing.JButton btnSoUpdate;
    private javax.swing.JComboBox<String> cbSoSituation;
    private javax.swing.JLabel lblSoAsteriskDefect;
    private javax.swing.JLabel lblSoAsteriskEquipment;
    private javax.swing.JLabel lblSoAsteriskId;
    public static javax.swing.JLabel lblSoAsteriskRequired;
    private javax.swing.JLabel lblSoDate;
    private javax.swing.JLabel lblSoDefect;
    private javax.swing.JLabel lblSoEquipment;
    private javax.swing.JLabel lblSoIconSearch;
    private javax.swing.JLabel lblSoId;
    private javax.swing.JLabel lblSoNumber;
    private javax.swing.JLabel lblSoService;
    private javax.swing.JLabel lblSoSituation;
    private javax.swing.JLabel lblSoTechnician;
    private javax.swing.JLabel lblSoValue;
    private javax.swing.JPanel pnSo;
    private javax.swing.JPanel pnSoCustomers;
    private javax.swing.JRadioButton rBtnSoBudget;
    private javax.swing.JRadioButton rBtnSoServiceOrder;
    private javax.swing.ButtonGroup rdSoBtnGroup;
    private javax.swing.JScrollPane sPsO;
    private javax.swing.JTable tbSoCustomers;
    private javax.swing.JTextField txtSoFieldDate;
    private javax.swing.JTextField txtSoFieldDefect;
    private javax.swing.JTextField txtSoFieldEquipment;
    private javax.swing.JTextField txtSoFieldId;
    private javax.swing.JTextField txtSoFieldSearch;
    private javax.swing.JTextField txtSoFieldService;
    private javax.swing.JTextField txtSoFieldSoN;
    private javax.swing.JTextField txtSoFieldTechnician;
    private javax.swing.JTextField txtSoFieldValue;
    // End of variables declaration//GEN-END:variables
}
