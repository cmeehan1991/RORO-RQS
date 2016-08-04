package UserAccess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cmeehan
 */
import Connections.DBConnection;
import Sales.MainMenu;
import javax.swing.*;
import java.awt.*;
import static java.lang.System.exit;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;

public class NewUserArchive extends javax.swing.JDialog {
    private final Connection CONN = new DBConnection().connect();

    /**
     * Creates new form forgotUsernamePassword
     *
     * @param parent
     * @param modal
     */
    public NewUserArchive(java.awt.Frame parent, boolean modal) {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        salesRegionLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        firstNameTextField = new javax.swing.JTextField();
        lastNameTextField = new javax.swing.JTextField();
        officePhoneTextField = new javax.swing.JTextField();
        titleTextField = new javax.swing.JTextField();
        mobilePhoneTextField = new javax.swing.JTextField();
        regionComboBox = new javax.swing.JComboBox();
        locationComboBox = new javax.swing.JComboBox();
        officePhoneCheckBox = new javax.swing.JCheckBox();
        mobilePhoneCheckBox = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        confirmEmailTextField = new javax.swing.JTextField();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JTextField();
        answerTextField = new javax.swing.JTextField();
        hintComboBox = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Log-In \"K\" Line RORO Quoting");
        setIconImage(null);
        setIconImages(null);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New User");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("\"K\" Line Email:");

        jButton1.setText("Go");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("First Name:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Last Name:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Title:");

        salesRegionLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        salesRegionLabel.setText("Sales Region");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Office Phone:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Mobile Phone:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Office Location:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Username:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Email:");

        emailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        firstNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lastNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTextFieldActionPerformed(evt);
            }
        });

        officePhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        titleTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        mobilePhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        regionComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        regionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "North East", "South East", "Mid West", "West", "Other" }));

        locationComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        locationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Richmond", "Houston", "Los Angeles", "Chicago", "New York", "Charleston" }));

        officePhoneCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        officePhoneCheckBox.setText("Set as primary");

        mobilePhoneCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        mobilePhoneCheckBox.setText("Set as primary");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Password:");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Hint:");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Answer:");

        confirmEmailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        usernameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        passwordTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        answerTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        hintComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        hintComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "When did you start at \"K\" Line?", "What college did you attend?", "What is your favorite food?", "What is your favorite color?", "Name of your first pet?", "Mother's maiden name?", "Father's middle name?" }));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(officePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(officePhoneCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(answerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(hintComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(locationComboBox, 0, 201, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(mobilePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobilePhoneCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(regionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(salesRegionLabel)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                            .addComponent(confirmEmailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {answerTextField, confirmEmailTextField, emailTextField, firstNameTextField, hintComboBox, lastNameTextField, locationComboBox, mobilePhoneTextField, officePhoneTextField, passwordTextField, regionComboBox, titleTextField, usernameTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salesRegionLabel)
                    .addComponent(regionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(officePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(officePhoneCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(mobilePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilePhoneCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(confirmEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(hintComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(answerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // insert new user information
        String mainEmail = emailTextField.getText();
        String newfirstname = firstNameTextField.getText();
        String newlastname = lastNameTextField.getText();
        String newtitle = titleTextField.getText();
        String newregion = regionComboBox.getSelectedItem().toString();
        String newofficePhone = officePhoneTextField.getText();
        Boolean newofficePrimary = officePhoneCheckBox.isSelected();
        String newmobilePhone = mobilePhoneTextField.getText();
        Boolean newmobilePrimary = mobilePhoneCheckBox.isSelected();
        String newlocation = locationComboBox.getSelectedItem().toString();
        String newemail = confirmEmailTextField.getText();
        String newusername = usernameTextField.getText();
        String newpassword = passwordTextField.getText();
        String newhint = hintComboBox.getSelectedItem().toString();
        String newanswer = answerTextField.getText().toLowerCase();

        String SQL = "UPDATE authorized_users SET firstname='" + newfirstname + "', lastname='" + newlastname + "', title='" + newtitle + "', region='" + newregion + "', phone='" + newofficePhone + "', main_primary='" + newofficePrimary + "', mobilePhone='" + newmobilePhone + "', mobile_primary='" + newmobilePrimary + "', officeLocation='" + newlocation + "', email='" + newemail + "', username='" + newusername + "', password='" + newpassword + "', security_question='" + newhint + "', answer='" + newanswer + "' WHERE email='" + mainEmail + "';";

        try {
            PreparedStatement psNew = CONN.prepareStatement(SQL);
            int rsNew = psNew.executeUpdate();

            JOptionPane.showMessageDialog(null, "Thank you for signing up. You will now be redirected to the main page.");

            //Initialize User ID variable and set to null
            String id = null;

            //Sql for selecteing the userID based on the username of person logging into application
            String sqlID = "SELECT userID FROM authorized_users WHERE username='" + newusername + "';";
            try {
                PreparedStatement psID = CONN.prepareStatement(sqlID);
                ResultSet rsID = psID.executeQuery();
                if (rsID.next()) {
                    id = rsID.getString("userID");
                }
            } catch (Exception e) {
                System.out.println("Error with capturing ID");
                System.out.println(e.getMessage());
            }

            //String today = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
            Calendar calReturn = Calendar.getInstance();
            calReturn.add(Calendar.DATE, -14);
            String attentionDate = new SimpleDateFormat("MM/dd/YYYY").format(calReturn.getTime());

        //String attentionDate = 
            String sqlLogIn = "SELECT * FROM authorized_users WHERE username='" + newusername + "' and password='" + newpassword + "';";
            //Get number of quotes;
            String sqlTotalQuotesToDate = "SELECT COUNT(ID) AS TOTAL FROM allquotes WHERE user_ID='" + id + "';";
            //Get out-standing quotes:
            String sqlOutstanding = "SELECT COUNT(ID) AS OUTSTANDING FROM allquotes WHERE user_ID='" + id + "' AND (booked='0' AND deny='0');";
            //Get bookings to date
            String sqlBookings = "SELECT COUNT(ID) AS BOOKINGS FROM allquotes WHERE user_ID='" + id + "' AND booked='1';";

            String sqlOutstandingTable = "SELECT ID, date, customerName, comm_description FROM allquotes WHERE user_ID ='" + id + "' AND (booked='0' AND DENY='0');";

            String sqlRequiringAttention = "SELECT ID, date, customerName, comm_description FROM allquotes WHERE user_ID ='" + id + "' AND (booked='0' AND DENY='0') AND date <= '" + attentionDate + "%';";

            try {
                //Log in SQL handling
                PreparedStatement ps = CONN.prepareStatement(sqlLogIn);
                ResultSet rs = ps.executeQuery(sqlLogIn);
                if (rs.next()) {
                    this.dispose();
                    MainMenu mm = new MainMenu();
                //Get user informatino from database
                    //String id = rs.getString("userID");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String title = rs.getString("title");
                    String region = rs.getString("region");
                    String officePhone = rs.getString("phone");
                    String mobilePhone = rs.getString("mobilePhone");
                    String officeLocation = rs.getString("officeLocation");
                    String email = rs.getString("email");
                //System.out.println(count);
                    //Assign information to labels on userInformation Panel
                    //MainMenu.userInformationLabel.setText(firstName + " " + lastName);
                    MainMenu.userIDLabel.setText(id);
                    MainMenu.titleLabel.setText(title);
                    MainMenu.salesRegionLabel.setText(region);
                    MainMenu.officePhoneLabel.setText(officePhone);
                    MainMenu.mobilePhoneLabel.setText(mobilePhone);
                    MainMenu.officeLocationLabel.setText(officeLocation);
                    MainMenu.emailLabel.setText(email);

                    /*
                     if(user.equals("jluse")){
                     JOptionPane.showMessageDialog(null,"Your request has been disregarded. Please resend with proper subject line");
                     exit(0);
                     }
                
                     if(user.equals("grogers")){
                     JOptionPane.showMessageDialog(null, "Welcome to the K Line vegan club where eating meat is forbidden!");
                     }
                     */
                    //Total quote sql handling
                    PreparedStatement ps1 = CONN.prepareStatement(sqlTotalQuotesToDate);
                    ResultSet rs1 = ps1.executeQuery(sqlTotalQuotesToDate);
                    if (rs1.next()) {
                        int count = rs1.getInt("TOTAL");
                        String totalCount = String.valueOf(count);
                        //System.out.println(count);
                        MainMenu.totalQuotesLabel.setText(totalCount);

                    }

                    //Outstanding quote SQL handling
                    PreparedStatement ps2 = CONN.prepareStatement(sqlOutstanding);
                    ResultSet rs2 = ps2.executeQuery(sqlOutstanding);
                    if (rs2.next()) {
                        int outstandingCount = rs2.getInt("OUTSTANDING");
                        String totalOutstanding = String.valueOf(outstandingCount);
                        //System.out.println(totalOutstanding);
                        MainMenu.outstandingLabel.setText(totalOutstanding);
                    }

                    //Get bookings to date
                    PreparedStatement ps3 = CONN.prepareStatement(sqlBookings);
                    ResultSet rs3 = ps3.executeQuery(sqlBookings);
                    if (rs3.next()) {
                        int totalBookingsCount = rs3.getInt("BOOKINGS");
                        String totalBookings = String.valueOf(totalBookingsCount);
                        System.out.println(totalBookings);
                        MainMenu.bookedToDateLabel.setText(totalBookings);
                    }
                    PreparedStatement ps4 = CONN.prepareStatement(sqlOutstandingTable);
                    ResultSet rs4 = ps4.executeQuery(sqlOutstandingTable);

                    MainMenu.outstandingQuotesTable.setModel(DbUtils.resultSetToTableModel(rs4));
                    TableColumnModel tcm = MainMenu.outstandingQuotesTable.getColumnModel();
                    tcm.getColumn(0).setHeaderValue("Quote ID");
                    tcm.getColumn(1).setHeaderValue("Date Quoted");
                    tcm.getColumn(2).setHeaderValue("Customer Name");
                    tcm.getColumn(3).setHeaderValue("Commodity Description");
                    System.out.println(rs);

                    PreparedStatement ps5 = CONN.prepareStatement(sqlRequiringAttention);
                    ResultSet rs5 = ps5.executeQuery(sqlRequiringAttention);
                    int columns1 = rs5.getMetaData().getColumnCount();

                    MainMenu.requireAttentionTable.setModel(DbUtils.resultSetToTableModel(rs5));
                    TableColumnModel tcm1 = MainMenu.requireAttentionTable.getColumnModel();
                    tcm1.getColumn(0).setHeaderValue("Quote ID");
                    tcm1.getColumn(1).setHeaderValue("Date Quoted");
                    tcm1.getColumn(2).setHeaderValue("Customer Name");
                    tcm1.getColumn(3).setHeaderValue("Commodity Description");

                    int totalBookingsCount = rs3.getInt("BOOKINGS");
                    int count = rs1.getInt("TOTAL");

                    if (totalBookingsCount == 0) {
                        MainMenu.bookingRatioLabel.setText("N/A");
                    } else {
                        String ratio = String.valueOf(count / totalBookingsCount);
                        MainMenu.bookingRatioLabel.setText(ratio + ":1");
                    }

                    MainMenu.usernameLabel.setText(newusername);
                    mm.setVisible(true);
                    mm.show();
                } else {
                    JOptionPane.showMessageDialog(null, "Username & Password Invalid");
                }
            } catch (SQLException | HeadlessException e) {
                System.out.println("Error!");
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
            this.dispose();

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String email = emailTextField.getText();

        String SQL = "SELECT * FROM authorized_users WHERE email='" + email + "';";

        try {
            PreparedStatement ps = CONN.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery(SQL);
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String department = rs.getString("department");
                if (password.equals("password")) {
                    usernameTextField.setText(username);
                    confirmEmailTextField.setText(email);
                    if(department.equals("Customer Service")){
                        salesRegionLabel.setVisible(false);
                        regionComboBox.setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "This email is already associated with a current user. Please enter the correct email or eixt and sign in with the correct associated username and password.");
                    exit(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error :" + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(NewUserArchive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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

            NewUserArchive dialog = new NewUserArchive(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField answerTextField;
    private javax.swing.JTextField confirmEmailTextField;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JComboBox hintComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JComboBox locationComboBox;
    private javax.swing.JCheckBox mobilePhoneCheckBox;
    private javax.swing.JTextField mobilePhoneTextField;
    private javax.swing.JCheckBox officePhoneCheckBox;
    private javax.swing.JTextField officePhoneTextField;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JComboBox regionComboBox;
    private javax.swing.JLabel salesRegionLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

}
