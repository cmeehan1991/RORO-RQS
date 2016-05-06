package RORO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cmeehan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class newQuoteCustomerName extends javax.swing.JDialog {

    public newQuoteCustomerName() {
        initComponents();
        setIcon();

    }

    protected int tf;

    public newQuoteCustomerName(int tf) {
        this.tf = tf;
    }

    /**
     * Creates new form logIn
     *
     * @param parent
     * @param modal
     */
    public newQuoteCustomerName(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        setLocationRelativeTo(this);
        setIcon();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        existingCustomerTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        SelectCustomer = new javax.swing.JButton();
        cancelCustomerButton = new javax.swing.JButton();
        searchInputField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Log-In \"K\" Line RORO Quoting");
        setIconImage(null);
        setIconImages(null);

        existingCustomerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Company Name", "DBA/Affiliates", "Contact Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        existingCustomerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingCustomerTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(existingCustomerTable);
        if (existingCustomerTable.getColumnModel().getColumnCount() > 0) {
            existingCustomerTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            existingCustomerTable.getColumnModel().getColumn(1).setPreferredWidth(400);
            existingCustomerTable.getColumnModel().getColumn(2).setPreferredWidth(400);
            existingCustomerTable.getColumnModel().getColumn(3).setPreferredWidth(400);
        }

        SelectCustomer.setText("Select");
        SelectCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectCustomerActionPerformed(evt);
            }
        });

        cancelCustomerButton.setText("Cancel");
        cancelCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelCustomerButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Add New Customer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SelectCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelCustomerButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectCustomer)
                    .addComponent(cancelCustomerButton))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName(" \"K\" Line RORO Quoting");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectCustomerActionPerformed
        // TODO add your handling code here:

        Connection conn = new DBConnection().connect();

        int selectedRowIndex = existingCustomerTable.getSelectedRow();
        Object selectedRowCustomer = existingCustomerTable.getValueAt(selectedRowIndex, 1);
        //String firstname = "";
        MainMenu.newQuoteCustomerNameLabel.setText((String) selectedRowCustomer);
        this.dispose();

    }//GEN-LAST:event_SelectCustomerActionPerformed

    private void cancelCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelCustomerButtonActionPerformed
        // TODO add your handling code here:

        MainMenu.newQuoteCustomerNameLabel.setText("N/A");
        this.dispose();
    }//GEN-LAST:event_cancelCustomerButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String searchInput = searchInputField.getText();
        ResultSet rs = new CustomerNameSearch().customerNameSearch(searchInput);
        newQuoteCustomerName.existingCustomerTable.setModel(DbUtils.resultSetToTableModel(rs));

    }//GEN-LAST:event_jButton2ActionPerformed


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Opens the new customer quick add form 
        newCustomerQuickAddForm ncqa = new newCustomerQuickAddForm();
        ncqa.setVisible(true);
        ncqa.show();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void searchInputField(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.print("Hello");
        }
    }

    private void existingCustomerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingCustomerTableMouseClicked

        JTable table = (JTable) evt.getSource();
        Point p = evt.getPoint();
        int row = table.rowAtPoint(p);
        if (evt.getClickCount() == 2) {
            Object value = existingCustomerTable.getValueAt(row, 0);
            System.out.println(value);
        }
    }//GEN-LAST:event_existingCustomerTableMouseClicked

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
    }

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
            java.util.logging.Logger.getLogger(newQuoteCustomerName.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

            newQuoteCustomerName dialog = new newQuoteCustomerName(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SelectCustomer;
    private javax.swing.JButton cancelCustomerButton;
    public static javax.swing.JTable existingCustomerTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchInputField;
    // End of variables declaration//GEN-END:variables

}
