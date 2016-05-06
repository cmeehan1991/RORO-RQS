/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RORO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author cmeehan
 */
public class SavedSearches extends javax.swing.JFrame {

    /**
     * Creates new form SavedSearches
     */
    public SavedSearches() {
        initComponents();
        setLocationRelativeTo(this);
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
        savedSearchesTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchByComboBox = new javax.swing.JComboBox();
        searchByTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        selectSavedSearchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        savedSearchesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Search Name", "User", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        savedSearchesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(savedSearchesTable);

        jLabel1.setText("Search By:");

        searchByComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Username", "Date", "Search Name" }));

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        selectSavedSearchButton.setText("Select");
        selectSavedSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSavedSearchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectSavedSearchButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchByTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(searchByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchByTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectSavedSearchButton)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectSavedSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSavedSearchButtonActionPerformed
        try {
            SelectSavedSearch();
        } catch (SQLException ex) {
            Logger.getLogger(SavedSearches.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SavedSearches.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Calendar calendar = Calendar.getInstance();
        String date = new SimpleDateFormat("MM/dd/YYYY").format(calendar.getTime());
        DBSearch.dateTextField.setText(date);

    }//GEN-LAST:event_selectSavedSearchButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SearchSavedSearches();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Selected the saved search from the table by the ID and display it in the DBSearch Menu
    public void SelectSavedSearch() throws SQLException, Exception{
        
        DBSearch dbs = new DBSearch();
        // Get the selected item ID
        int row = savedSearchesTable.getSelectedRow();
        long selectedSearch = (long) savedSearchesTable.getValueAt(row, 0); //Assign the id from the selected row to the variable selectedSearch
        
        // Connect to the database
        Connection conn = new DBConnection().connect();
        
        // SQL to find the search in the database
        String findSavedSearchNameSql = "SELECT * FROM saved_searches_name WHERE ID=?"; //Get all information from the saved_searches_name table
        String getSavedSearchResultsSql = "SELECT include_in_results AS '' FROM saved_search_results WHERE search_ID=?"; // Get all information from the saved_search_results table
        String getSavedSearchParametersSql = "SELECT and_or AS 'And/Or', open_parenthases AS '(', column_name AS 'Column Name', operator AS 'Operator', value AS 'Value', close_parenthases AS ')' FROM saved_search_parameters WHERE search_ID=? ORDER BY ID"; // Get all information from the saved_search_parameters table
        
        try{
            PreparedStatement psFSSNS = conn.prepareStatement(findSavedSearchNameSql);
            psFSSNS.setLong(1, selectedSearch);
            ResultSet rsFSSNS = psFSSNS.executeQuery();
            if(rsFSSNS.next()){ // If returns results
                
                // Assign values to strings
                String queryName = rsFSSNS.getString("query_name");
                Boolean packingList = rsFSSNS.getBoolean("packingList");
                
                //Set values
                DBSearch.queryNameTextField.setText(queryName);
                DBSearch.includePackingList.setSelected(packingList);
            }
            psFSSNS.closeOnCompletion();
        }catch (Exception ex){
            System.out.println("Error! findSavedSearchNameSql: "+ex.getMessage());
        }
        
        try{
            PreparedStatement psGSSRS = conn.prepareStatement(getSavedSearchResultsSql);
            psGSSRS.setLong(1, selectedSearch);
            ResultSet rsGSSRS = psGSSRS.executeQuery();
            
            // Populate the table with the results
            DBSearch.includeInResultsTable.setModel(DbUtils.resultSetToTableModel(rsGSSRS));
            
            
            // Close the SQL upon completion
            psGSSRS.closeOnCompletion();
        }catch(Exception ex){
            System.out.println("Error! getSavedSearchResultsSql: "+ex.getMessage());
        }
        
        try{
            PreparedStatement psGSSPS = conn.prepareStatement(getSavedSearchParametersSql);
            psGSSPS.setLong(1, selectedSearch);
            ResultSet rsGSSPS = psGSSPS.executeQuery();
            // Populate the table with the results
            DBSearch.parametersTable.setModel(DbUtils.resultSetToTableModel(rsGSSPS));
            
            //Close the search upon completion
            psGSSPS.closeOnCompletion();
        }catch(Exception ex){
            System.out.println("Error! getSavedSearchParametersSql: "+ex.getMessage());
        }
           conn.close(); // Close the connection
           
           //Close this window and open the DBSearch window.
           this.dispose();
           dbs.setVisible(true);
           
    }
    
     private void SearchSavedSearches() {
        String userInput, searchBySelection, findRecentSearchSql;
        
        // Establish connection to DB
        Connection conn = new DBConnection().connect();
        
        userInput = searchByTextField.getText();
        searchBySelection = searchByComboBox.getSelectedItem().toString();
        
        // Initialize sql
        findRecentSearchSql = null;
        
       switch(searchBySelection){
           case "Username": 
               findRecentSearchSql = "SELECT ID, query_name AS 'Query Name', date AS 'Date', user AS 'Username' FROM saved_searches_name WHERE user LIKE ?";
               break;
           case "Date":
               findRecentSearchSql = "SELECT ID, query_name AS 'Query Name', date AS 'Date', user AS 'Username' FROM saved_searches_name WHERE date LIKE ?";
               break;
           case "Search Name":
               findRecentSearchSql = "SELECT ID, query_name AS 'Query Name', date AS 'Date', user AS 'Username' FROM saved_searches_name WHERE query_name LIKE ?";
               break;
           default:
               break;
       }
       
       //Prepare the user input for the search by breaking it down and adding % to give the widest results possible
       StringBuilder sb = new StringBuilder();
       String[] userInputSplit = userInput.split("");
       List<String> userInputTogether = Arrays.asList(userInputSplit);
       String finalSearchText = "%"+userInputTogether.stream().map(i->(i)).collect(Collectors.joining("%"))+"%"; 
       System.out.println(finalSearchText);
       //Attempt to search by given values
       try{
           PreparedStatement psFindRecentSearch = conn.prepareStatement(findRecentSearchSql);
           psFindRecentSearch.setString(1, finalSearchText);
           ResultSet rsFindRecentSearch = psFindRecentSearch.executeQuery();
           
           savedSearchesTable.setModel(DbUtils.resultSetToTableModel(rsFindRecentSearch));
           
           psFindRecentSearch.closeOnCompletion();
           conn.close();
       }catch(Exception ex){
           System.out.println("Error! findRecentSearchSql: "+ex.getMessage());
       }
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SavedSearches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SavedSearches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SavedSearches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SavedSearches.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SavedSearches().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable savedSearchesTable;
    private static javax.swing.JComboBox searchByComboBox;
    private javax.swing.JTextField searchByTextField;
    private javax.swing.JButton selectSavedSearchButton;
    // End of variables declaration//GEN-END:variables

   

    
}
