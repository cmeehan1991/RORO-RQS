/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RORO;

import Connections.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author cmeehan
 */
public class UserInformationActivity {

    protected final static Connection conn = new DBConnection().connect();
    private final Calendar calReturn = Calendar.getInstance();
    private String username, userID;

    public void UserInformation(String userID, String username) {
        this.userID = userID;
        this.username = username;
        UpdateUserInformationActivity(userID, username);
    }

    private String currentDate() {
        //Get today's date
        String today = new SimpleDateFormat("YYYY-MM-dd").format(calReturn.getTime());
        return today;
    }

    private String attentionDate() {
        //Set the date to 14 days (2 weeks) back from today
        Calendar calAttentionDateReturn = Calendar.getInstance();
        calAttentionDateReturn.add(Calendar.DATE, -14);
        String attentionDate = new SimpleDateFormat("YYYY-MM-dd").format(calAttentionDateReturn.getTime());
        return attentionDate;
    }

    private String newYears() {
        //Get 01/01/YYYY for current year
        String newYears = new SimpleDateFormat("YYYY-01-01").format(calReturn.getTime());
        return newYears;
    }

    private String firstOfWeek() {
        //Get the first day of this week (Sunday)
        calReturn.set(Calendar.DAY_OF_WEEK, 1);
        String firstOfWeek = new SimpleDateFormat("YYYY-MM-dd").format(calReturn.getTime());
        return firstOfWeek;
    }

    private String totalQuotesToDate(String userID) {

        String SQL = "SELECT COUNT(ID) AS TOTAL FROM allquotes WHERE user_ID=?";
        String totalQuotes = null;
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalQuotes = String.valueOf(rs.getInt("TOTAL"));
            }
        } catch (Exception ex) {
            System.out.println("Error" + ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        return totalQuotes;
    }
    
    private String totalQuotesThisWeek(String userID, String firstOfWeek, String today){
        // Returns the number of quotes the user has done that week (SUN - SAT)
        String SQL = "SELECT COUNT(*) AS 'TOTAL' FROM allquotes WHERE user_ID=? AND DATE_QUOTED >= ? AND DATE_QUOTED <= ?";
        String totalQuotes = null;
        try{
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, userID);
            ps.setString(2, firstOfWeek);
            ps.setString(3, today+" 23:59");
            ResultSet rs = ps.executeQuery();
            System.out.println("Today: "+today+" First of Week: "+firstOfWeek);
            if(rs.next()){
                totalQuotes = String.valueOf(rs.getInt("TOTAL"));
            }
        }catch(Exception ex){
            System.out.println("Error "+ex.getMessage());
        }
        return totalQuotes;
    }
    
    private String totalQuotesCYTD(String userID, String newYears, String today) {
        String totalQuotesCYTD = null;
        String SQL = "SELECT COUNT(*) AS TOTAL FROM allquotes WHERE user_ID=? AND (DATE_QUOTED >= ? AND DATE_QUOTED <= ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, userID);
            ps.setString(2, newYears);
            ps.setString(3, today);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalQuotesCYTD = String.valueOf(rs.getInt("TOTAL"));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return totalQuotesCYTD;
    }

    private String totalOutstanding() {
        String totalOutstandingQuotes = null;

//Get out-standing quotes:
        String SQL = "SELECT COUNT(ID) AS 'OUTSTANDING' FROM allquotes WHERE user_ID =? AND (booked !=? AND deny !=? AND feedback !=?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, userID);
            ps.setBoolean(2, true);
            ps.setBoolean(3, true);
            ps.setBoolean(4, true);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalOutstandingQuotes = rs.getString("OUTSTANDING");
            }
            return totalOutstandingQuotes;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String totalBookings(String userID) {
        String totalBookings = null;
        //Get bookings to date
        String SQL = "SELECT COUNT(ID) AS 'BOOKINGS' FROM allquotes WHERE bookedUserID=? AND booked=?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, userID);
            ps.setBoolean(2, true);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalBookings = String.valueOf(rs.getInt("BOOKINGS"));
            }
            return totalBookings;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String totalBookingsCYTD(String newYears, String today) {
        String totalBookingsCYTD = null;
        String SQL = "SELECT COUNT(ID) AS 'Bookings_CYTD' FROM allquotes WHERE (bookedUserID=? AND booked=?) AND IF(DATE_UPDATED !=?, DATE_UPDATED>=? AND DATE_UPDATED <=?, DATE_QUOTED >=? AND DATE_QUOTED<=?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, userID);
            ps.setBoolean(2, true);
            ps.setString(3, "");
            ps.setString(4, newYears);
            ps.setString(5, today);
            ps.setString(6, newYears);
            ps.setString(7, today);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalBookingsCYTD = String.valueOf(rs.getInt("Bookings_CYTD"));
            }
            return totalBookingsCYTD;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private ResultSet outstandingTable() {
        ResultSet rs = null;
        String SQL = "SELECT ID AS 'Quote ID', DATE_FORMAT(DATE_QUOTED, '%Y-%m-%d') AS 'Date Quoted', customerName AS 'Company', CONCAT(comm_class,', ', handling_instructions) AS 'Commodity Type', comm_description AS 'Commodity Description' FROM allquotes WHERE booked != ? AND deny != ? AND feedback != ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setBoolean(1, true);
            ps.setBoolean(2, true);
            ps.setBoolean(3, true);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }

    private ResultSet requiringAttentionTable(String attentionDate) {
        ResultSet rs = null;
        String SQL = "SELECT ID AS 'Quote ID', IF(DATE_UPDATED !=?, DATE_FORMAT(DATE_UPDATED, '%Y-%m-%d'), DATE_FORMAT(DATE_QUOTED, '%Y-%m-%d')) AS 'Date Last Updated', customerName AS 'Company', CONCAT(comm_class,', ', handling_instructions) AS 'Commodity Type', comm_description AS 'Commodity Description' FROM allquotes WHERE booked!=? AND deny !=? AND feedback != ? AND IF(DATE_QUOTED=?, DATE_UPDATED <= ?, DATE_QUOTED <=?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, null);
            ps.setBoolean(2, true);
            ps.setBoolean(3, true);
            ps.setBoolean(4, true);
            ps.setString(5, "");
            ps.setString(6, attentionDate);
            ps.setString(7, attentionDate);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }
   
    private String bookingRatio() {
        int quotes = (int) (Integer.parseInt(totalQuotesToDate(userID)));
        int bookings = (int) (Integer.parseInt(totalBookings(userID)));
        int ratio; 
        if(bookings == 0){
            ratio = 0;
        }else{
        ratio = quotes / bookings;
        }
        String bookingRatio = String.valueOf(ratio) + ":1";
        return bookingRatio;
    }
    
    private ResultSet pendingResponseTable(){
        ResultSet rs = null;
        String SQL = "SELECT ID AS 'Quote ID', IF(DATE_UPDATED = '', IF(DATEDIFF("+currentDate()+",DATE_QUOTED) < '1', '0', DATEDIFF("+currentDate()+",DATE_QUOTED)), IF(DATEDIFF("+currentDate()+",DATE_UPDATED)< '1','0',DATEDIFF("+currentDate()+",DATE_UPDATED))) AS 'Days Stagnant', IF(DATE_UPDATED='', DATE_FORMAT(DATE_QUOTED, '%Y-%m-%d'), DATE_FORMAT(DATE_UPDATED, '%Y-%m-%d')) AS 'Last Updated', customerName AS 'Company Name', comm_class AS 'Commodity Class' FROM allquotes WHERE MTD_APPROVAL = 'Pending' OR SPACE_APPROVAL = 'Pending' OR OVERSEAS_RESPONSE = 'Pending' ORDER BY 'Days Stagnant';";
        try{
            PreparedStatement ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }

    public void UpdateUserInformationActivity(String userID, String username) {
        this.userID = userID;
        this.username = username;
        String attentionDate = attentionDate(), newYears = newYears(), today = currentDate(), firstOfWeek = firstOfWeek();
        MainMenu.totalQuotesLabel.setText(totalQuotesToDate(userID));
        MainMenu.totalQuotesCYTDLabel.setText(totalQuotesCYTD(userID, newYears, today));
        MainMenu.totalQuotesWeekLabel.setText(totalQuotesThisWeek(userID, firstOfWeek, today));
        MainMenu.outstandingLabel.setText(totalOutstanding());
        MainMenu.totalBookingsCYTDLabel.setText(totalBookingsCYTD(newYears, today));
        MainMenu.bookedToDateLabel.setText(totalBookings(userID));
        MainMenu.bookingRatioLabel.setText(bookingRatio());
        MainMenu.outstandingQuotesTable.setModel(DbUtils.resultSetToTableModel(outstandingTable()));
        MainMenu.requireAttentionTable.setModel(DbUtils.resultSetToTableModel(requiringAttentionTable(attentionDate)));
        MainMenu.quotesPendingResponseTable.setModel(DbUtils.resultSetToTableModel(pendingResponseTable()));
    }

}
