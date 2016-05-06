/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RORO;

import static RORO.DBSearch.includeInResultsTable;
import static RORO.DBSearch.parametersTable;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author cmeehan
 */
public class DBSearchActivity {

    // Runs the database search
    public void SubmitSearch() {

        //Establish connection to the DB
        Connection conn = new DBConnection().connect();

        Boolean includePL = DBSearch.includePackingList.isSelected();
        String queryName = DBSearch.queryNameTextField.getText();
        MainMenu.queryNameTextField.setText(queryName);
        MainMenu.dateTextField.setText(DBSearch.dateTextField.getText());
        MainMenu.queryTitleLabel.setText(queryName);
        if (queryName.equals("")) {
            JOptionPane.showMessageDialog(null, "You must enter a query name");
        } else {
            //Get the row count of the included fields table
            int resultRows = includeInResultsTable.getRowCount();
            String[] Selections = new String[resultRows]; //Declare the array selections and set it's size equal to the number of rows in the selected fields table
            for (int includeResults = 0; includeResults < resultRows; includeResults++) {
                Object includeField = includeInResultsTable.getValueAt(includeResults, 0);//Get each value at each row in the selected fields table
                String fields = null; //Declare variable fields as a String
                switch (String.valueOf(includeField)) { //Sets the values equal to the proper
                    case "Quote ID":
                        fields = "allquotes.ID";
                        break;
                    case "Customer Name":
                        fields = "customerName AS 'Customer Name'";
                        break;
                    case "Quote Contact Name":
                        fields = "contactName AS 'Quote Contact Name'";
                        break;
                    case "Quote Contact Email":
                        fields = "contactEmail AS 'Quote Contact Email'";
                    case "Trade Lane":
                        fields = "tradeLane AS 'Trade Lane'";
                        break;
                    case "POL":
                        fields = "pol AS 'POL'";
                        break;
                    case "POD":
                        fields = "pod AS 'POD'";
                        break;
                    case "Commodity Class":
                        fields = "comm_class AS 'Commodity Class'";
                        break;
                    case "Handling Instructions":
                        fields = "handling_instructions AS 'Handling Insructions'";
                        break;
                    case "Accessories":
                        fields = "accessories AS 'Accessories'";
                        break;
                    case "Mafi Minimum":
                        fields = "mafiMinimumCharge AS 'Mafi Minimum'";
                        break;
                    case "Commodity Description":
                        fields = "comm_description AS 'Commodity Description'";
                        break;
                    case "OFT":
                        fields = "CONCAT(rate, ' per ', rate_unit) AS 'OFT'";
                        break;
                    case "BAF":
                        fields = "CONCAT(baf, '%') AS 'BAF'";
                        break;
                    case "ECA BAF":
                        fields = "CONCAT(eca_baf, ' ', eca_unit) AS 'ECA BAF'";
                        break;
                    case "THC":
                        fields = "CONCAT (thc, ' ', thc_unit) AS  'THC'";
                        break;
                    case "WFG":
                        fields = "CONCAT(wfg,' ', wfg_unit)  AS 'WFG'";
                        break;
                    case "War Risk":
                        fields = "war_risk AS 'War Risk'";
                        break;
                    case "Documentation Fee":
                        fields = "doc_fee AS 'Documentation Fee'";
                        break;
                    case "Contract Rate":
                        fields = "contract_rate AS 'Contract Rate'";
                        break;
                    case "Spot Rate":
                        fields = "spot_rate AS 'Spot Rate'";
                        break;
                    case "Booked":
                        fields = "booked AS 'Booked'";
                        break;
                    case "Declined":
                        fields = "deny AS 'Declined'";
                        break;
                    case "Comments":
                        fields = "comments AS 'Comments'";
                        break;
                    case "Quote Date":
                        fields = "allquotes.date as 'Date'";
                        break;
                    case "Date Updated":
                        fields = "allquotes.dateUpdated";
                        break;
                    case "Booking Number":
                        fields = "bookingNumber AS 'Booking Number'";
                        break;
                    case "Duplicate":
                        fields = "duplicateRate AS 'Duplicate'";
                        break;
                    default:
                        break;
                }

                Selections[includeResults] = fields;
            }
            Object resultTable = null;
            if (resultRows - 1 < 0) {
                resultTable = "";
            } else {
                resultTable = includeInResultsTable.getValueAt(resultRows - 1, 0);
            }
            String id = "ID";
            String tradeLane = "tradeLane";
            String pol = "pol";
            String pod = "pod";
            String commClass = "comm_class";
            String handling = "handling_instructions";
            String accessories = "accessories";
            String contract = "contract";
            String spotRate = "spot_rate";
            String booked = "booked";
            String declined = "deny";
            String sql;
            String items;

            Object andOr;
            Object parenthases1;
            Object columnActual;
            Object column;
            Object operator;
            Object value;
            Object parenthases2;

            int rows = parametersTable.getRowCount();
            String[] whereStatement = new String[rows];
            String selectedFields = null;

            for (int r = 0; r < rows; r++) {
                andOr = parametersTable.getValueAt(r, 0);
                parenthases1 = parametersTable.getValueAt(r, 1);
                columnActual = parametersTable.getValueAt(r, 2);
                column = null;
                operator = parametersTable.getValueAt(r, 3);
                value = parametersTable.getValueAt(r, 4);
                parenthases2 = parametersTable.getValueAt(r, 5);
                switch (String.valueOf(columnActual)) {
                    case "Quote ID":
                        column = "allquotes." + id;
                        break;
                    case "Trade Lane":
                        column = "allquotes." + tradeLane;
                        break;
                    case "POL":
                        column = "allquotes." + pol;
                        break;
                    case "POD":
                        column = "allquotes." + pod;
                        break;
                    case "Commodity Class":
                        column = "allquotes." + commClass;
                        break;
                    case "Handling Instructions":
                        column = "allquotes." + handling;
                        break;
                    case "Accessores":
                        column = "allquotes." + accessories;
                        break;
                    case "Contract Rates":
                        column = "allquotes." + contract;
                        break;
                    case "Booked":
                        column = "allquotes." + booked;
                        break;
                    case "Decline":
                        column = "allquotes." + declined;
                        break;
                    case "Quote Date":
                        column = "allquotes.date";
                        break;
                    case "Duplicate":
                        column = "allquotes.duplicateRate";
                        break;
                    default:
                        break;
                }
                if (andOr.equals("null")) {
                    andOr = "";
                }
                if (operator == null) {
                    operator = " ";
                }
                if (value == null) {
                    value = "";
                }
                if (parenthases2 == null) {
                    parenthases2 = "";
                }
                String likeFields;

                if (operator.equals("ENDS WITH")) {
                    StringBuilder o = new StringBuilder();
                    o.append("LIKE");
                    StringBuilder v = new StringBuilder();
                    v.append("%");
                    v.append(value);
                    whereStatement[r] = " " + andOr + " " + parenthases1 + column + " " + o + " '" + v + "'" + parenthases2;
                } else if (operator.equals("LIKE")) {
                    StringBuilder o = new StringBuilder();
                    o.append("LIKE ");
                    String str = String.valueOf(value);
                    String[] a = str.split("");
                    List<String> selectFields = Arrays.asList(a);
                    likeFields = selectFields.stream().map(i -> (i)).collect(Collectors.joining("%"));
                    whereStatement[r] = " " + andOr + " " + parenthases1 + column + " " + o + " '%" + likeFields + "'" + parenthases2;
                } else if (operator.equals("BEGINS WITH")) {
                    StringBuilder o = new StringBuilder();
                    o.append("LIKE");
                    StringBuilder v = new StringBuilder();
                    v.append(value);
                    v.append("%");
                    whereStatement[r] = " " + andOr + " " + parenthases1 + column + " " + o + " '" + v + "'" + parenthases2;
                } else {
                    whereStatement[r] = " " + andOr + " " + parenthases1 + column + " " + operator + " '" + value + "'" + parenthases2;
                }

                if (whereStatement[r] == null || whereStatement[r].equals("null")) {
                    whereStatement[r] = "";
                }

                System.out.println(r);
            }
            if (rows >= 0) {
                if (Selections.length < 1) {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to return all fields?");
                    if (response == JOptionPane.YES_OPTION) {
                        selectedFields = "*";
                    }

                } else {
                    List<String> selectFields = Arrays.asList(Selections);
                    selectedFields = selectFields.stream().map(i -> (i)).collect(Collectors.joining(", "));
                }

                StringBuilder where = new StringBuilder();
                for (String w : whereStatement) {
                    where.append(w);
                }
                if (includePL == true) {
                    sql = "SELECT " + selectedFields + ", commodity AS 'Commodity', quantity AS 'Quantity', l AS 'Length(cm)', w AS 'Width(cm)', h AS 'Height(cm)', kgs AS 'Kilograms', length_inches AS 'Length(in)', width_inches AS 'Width(in)', height_inches AS 'Height(in)', lbs AS 'Pounds', cbm AS '(M3)', IF(l!='null' AND l!='', ((l*w)/10000/6.37),((length_inches*width_inches)*6.4516/10000/6.37)) AS 'RT', cbm*quantity AS 'Total M3' FROM allquotes, packingList WHERE allquotes.ID = packingList.quoteID AND " + where + " AND packingList.commodity != '';";
                } else {
                    sql = "SELECT " + selectedFields + " FROM allquotes WHERE " + where + ";";
                }

                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    // Save search on submit
                    MainMenu.searchResultsTable.setModel(DbUtils.resultSetToTableModel(rs));
                    //this.dispose();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(sql);
            }
        }
    }

    // This will allow the user to save their search
    public void SaveAndSubmitSearch() {
        // Declare variables
        String searchName, username, date, saveSearchAs, and_or, open_parenthases, column_name, operator, value, close_parenthases, include_in_results;
        Boolean packingList;
        int parametersTableRowCount = DBSearch.parametersTable.getRowCount();
        int rtRowCount = DBSearch.includeInResultsTable.getRowCount();

        // Initialize the variables
        searchName = DBSearch.queryNameTextField.getText();
        username = MainMenu.usernameLabel.getText();
        date = DBSearch.dateTextField.getText();
        packingList = DBSearch.includePackingList.isSelected();

        if (searchName.equals("")) {
            JOptionPane.showMessageDialog(null, "You must add a name");
            DBSearch.queryNameTextField.setBackground(Color.yellow);
        } else {

            saveSearchAs = JOptionPane.showInputDialog(null, "Save the Search As:", username + "_" + searchName + "_" + date);

            // Connect to the Database
            Connection conn = new DBConnection().connect();

            // SQL to insert search information into the database
            String SaveSearchSql = "INSERT INTO saved_searches_name (query_name, date, user, packingList) VALUES(?,?,?,?)";

            // SQL to insert parameters into database for saved search
            String SaveParametersSql = "INSERT INTO saved_search_parameters (and_or, open_parenthases, column_name, operator, value, close_parenthases,search_ID) VALUES(?,?,?,?,?,?,?)";

            // SQL to insert returned values into database for saved search
            String IncludeResultsSql = "INSERT INTO saved_search_results (include_in_results, search_ID) VALUES(?,?)";

            try {
                //Save the search name
                PreparedStatement psSaveSearchSQL = conn.prepareStatement(SaveSearchSql, Statement.RETURN_GENERATED_KEYS);
                psSaveSearchSQL.setString(1, saveSearchAs);
                psSaveSearchSQL.setString(2, date);
                psSaveSearchSQL.setString(3, username);
                psSaveSearchSQL.setBoolean(4, packingList);
                psSaveSearchSQL.executeUpdate();

                ResultSet search_ID = psSaveSearchSQL.getGeneratedKeys();
                int lastKey = 1;
                while (search_ID.next()) {
                    lastKey = search_ID.getInt(1);
                }

                for (int ptRow = 0; ptRow < parametersTableRowCount; ptRow++) {
                    and_or = (String) DBSearch.parametersTable.getValueAt(ptRow, 0);
                    open_parenthases = (String) DBSearch.parametersTable.getValueAt(ptRow, 1);
                    column_name = (String) DBSearch.parametersTable.getValueAt(ptRow, 2);
                    operator = (String) DBSearch.parametersTable.getValueAt(ptRow, 3);
                    value = (String) DBSearch.parametersTable.getValueAt(ptRow, 4);
                    close_parenthases = (String) DBSearch.parametersTable.getValueAt(ptRow, 5);

                    // Save the parameters
                    try {
                        PreparedStatement psSaveParametersSql = conn.prepareStatement(SaveParametersSql);
                        psSaveParametersSql.setString(1, and_or);
                        psSaveParametersSql.setString(2, open_parenthases);
                        psSaveParametersSql.setString(3, column_name);
                        psSaveParametersSql.setString(4, operator);
                        psSaveParametersSql.setString(5, value);
                        psSaveParametersSql.setString(6, close_parenthases);
                        psSaveParametersSql.setInt(7, lastKey);
                        psSaveParametersSql.executeUpdate();
                        psSaveParametersSql.closeOnCompletion();
                    } catch (Exception ex) {
                        System.out.println("Error (psSaveParametersSql): " + ex.getMessage());
                    }
                }

                for (int resultsRow = 0; resultsRow < rtRowCount; resultsRow++) {
                    include_in_results = (String) DBSearch.includeInResultsTable.getValueAt(resultsRow, 0);
                    try {
                        PreparedStatement psIncludeResultsSql = conn.prepareStatement(IncludeResultsSql);
                        psIncludeResultsSql.setString(1, include_in_results);
                        psIncludeResultsSql.setInt(2, lastKey);
                        psIncludeResultsSql.executeUpdate();
                        psIncludeResultsSql.closeOnCompletion();
                    } catch (Exception ex) {
                        System.out.println("Error (psIncludeResultsSql: " + ex.getMessage());
                    }
                }
                psSaveSearchSQL.closeOnCompletion();
                conn.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            SubmitSearch();
        }
    }
}
