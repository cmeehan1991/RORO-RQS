package Connections;


import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cmeehan
 */
public class DBConnection {
    private Connection DBConnection;
    public Connection connect() {
       /*try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection Success");
        }
        catch (ClassNotFoundException cnfe){
            System.out.println("Connection Fali" + cnfe);
        }
        String url = "jdbc:mysql://localhost:3306/kline";
        try {
            DBConnection = (Connection) DriverManager.getConnection(url, "root","");
            System.out.println("Database Connected");
        }
        catch(SQLException se){
            System.out.println("No Database" + se);
        }*/
        
        
        //Have to uncomment the below section before building for distribution
        
        /**
         * Live server connection
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection Success");
        }
        catch (ClassNotFoundException cnfe){
            System.out.println("Connection Fail " + cnfe);
        }
       
        
        try{
            InetAddress addr; 
            Socket sock = new Socket("100.3.79.178", 3306);
            addr = sock.getInetAddress();
            System.out.println("Connected to "+addr);
            sock.close();
            
        }catch(java.io.IOException e){
            System.out.println("Error: "+e);
        }
        
        String url = "jdbc:mysql://100.3.79.178:3306/kline";
        String username = "pccrate";
        String password = "pccror)1";
        try {
            DBConnection = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Database Connected");
        }
        catch(SQLException se){
            System.out.println("No Database " + se);
            
        }
              
       return DBConnection;  
    }
}
