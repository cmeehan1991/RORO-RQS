/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RORO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author cmeehan
 */
public class CustomerNameSearch {

    private final Connection conn = new DBConnection().connect();

    private String searchInput(String searchInput) {
        String result;
        String[] termArray = searchInput.split("");
        List<String> termList = Arrays.asList(termArray);
        result = termList.stream().map(i -> (i)).collect(Collectors.joining("%"));
        result = "%" + result + "%";
        return result;
    }

    protected ResultSet customerNameSearch(String searchInput) {
        ResultSet rs = null;
        String SQL = "SELECT ID, company, DBA, concat(firstname, \" \", lastname) AS name FROM rorocustomers WHERE (ID LIKE ? OR company LIKE ? OR DBA LIKE ? OR firstname LIKE ? OR lastname LIKE ?) ORDER BY company";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            for (int i = 1; i < 6; i++) {
                ps.setString(i, searchInput(searchInput));
            }
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }

}
