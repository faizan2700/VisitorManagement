/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visitormanagement;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public final class databaseHandler {
    private static databaseHandler handler;
    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private databaseHandler(){
        createConnection();
        //deleteAllTables();
        setupTable();
    }
    
    public static databaseHandler getInstance(){
        if(handler==null){
            handler = new databaseHandler();
        }
        return handler;
    }
    
    private static void createConnection() {
    try {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        conn = DriverManager.getConnection(DB_URL);
    }
    catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
        JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    }
    
    void setupTable(){

        String TABLE1 = "VISITOR";
        String qu = "";
        try{
            stmt = (Statement) conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet table1 = dbm.getTables(null,null,TABLE1.toUpperCase(),null);
            
            if(table1.next()){
                //System.out.println("Table "+TABLE2 + " already existis Ready for go!");
            }
            else{
                 qu = " CREATE TABLE " + TABLE1 + " ("
                + "        checkintime varchar(200) primary key,\n"
                +"         name varchar(200),\n"
                +"         phone varchar(200),\n"
                +"         email varchar(200),\n"
                +"         checkouttime varchar(200),\n"
                +"         host varchar(200),\n"
                +"         location varchar(200)"
                        + " )";
                 
                stmt.execute(qu);
                //System.out.println("Table " + TABLE2 + " Created!");
            }
        } catch(SQLException e){
            //System.out.println("table 2 visitors! error");
            System.out.println(qu);
            System.err.println(e.getMessage() + ".........setup database");
        }
        
        
        String TABLE2 = "ADMIN";
        qu = "";
        try{
            stmt = (Statement) conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet table2 = dbm.getTables(null,null,TABLE2.toUpperCase(),null);
            
            if(table2.next()){
                //System.out.println("Table "+TABLE2 + " already existis Ready for go!");
            }
            else{
                 qu = " CREATE TABLE " + TABLE2 + " ("
                + "        username varchar(200) primary key,\n"
                +"         password varchar(300)"
                        + " )";
                 
                stmt.execute(qu);
                //System.out.println("Table " + TABLE2 + " Created!");
            }
        } catch(SQLException e){
            //System.out.println("table 2 visitors! error");
            System.out.println(qu);
            System.err.println(e.getMessage() + ".........setup database");
        }
        
        
        String TABLE3 = "HOST";
        qu = "";
        try{
            stmt = (Statement) conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet table3 = dbm.getTables(null,null,TABLE3.toUpperCase(),null);
            
            if(table3.next()){
                //System.out.println("Table "+TABLE2 + " already existis Ready for go!");
            }
            else{
                 qu = " CREATE TABLE " + TABLE3 + " ("
                + "        name varchar(200) primary key,\n"
                +"         phone varchar(300),\n"
                +"         email varchar(100)"
                        + " )";
                 
                stmt.execute(qu);
                //System.out.println("Table " + TABLE2 + " Created!");
            }
        } catch(SQLException e){
            //System.out.println("table 2 visitors! error");
            System.out.println(qu);
            System.err.println(e.getMessage() + ".........setup database");
        }
        
    }
    
    void deleteAllTables(){
        try {
            stmt = (Statement) conn.createStatement();
        stmt.execute("DROP TABLE VISITOR");
        System.out.println("deleted");
        } catch (SQLException ex) {
            System.out.println("delete pobelm");
            //Logger.getLogger(databaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            //System.out.println(query + " the execquery at databse handler");
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            //System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }
    
}
