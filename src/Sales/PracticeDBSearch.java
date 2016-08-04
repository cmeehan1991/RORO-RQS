/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import Connections.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connections.DBConnection;


/**
 *
 * @author cmeehan
 */
public class PracticeDBSearch {

    public PracticeDBSearch() {
        Connection conn = new DBConnection().connect();

        String allquoteSQL = "SELECT * FROM allquotes WHERE ID=1220";

        try {
            PreparedStatement ps = conn.prepareStatement(allquoteSQL);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String ID = rs.getString("ID");
                String tradeLan = rs.getString("tradeLane");
                
                System.out.println("Tradelane quoteID commodity");
                String packingListSQL = "SELECT * FROM packingList WHERE quoteID='"+ID+"';";
                try{
                    PreparedStatement ps1 = conn.prepareStatement(packingListSQL);
                    ResultSet rs1 = ps1.executeQuery(packingListSQL);
                    while(rs1.next()){
                        String plID = rs1.getString("quoteID");
                        String plComm = rs1.getString("commodity");
                            
                            System.out.println(tradeLan + " "+ plID + " "+plComm);
                        
                        
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                
            }
            
            
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
