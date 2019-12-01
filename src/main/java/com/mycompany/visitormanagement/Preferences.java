package com.mycompany.visitormanagement;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class Preferences {
    
    public static final String CONFIG_FILE = "config.txt";
    
    String username;
    String password;
    
    databaseHandler handler;
    
    public Preferences(){
        username = "admin";
        password = "admin";
        handler = databaseHandler.getInstance();
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String username){
        this.password = password;
    }
    
    public static void initConfig(){
        Preferences pref = new Preferences();
        Gson gson = new Gson();
        try {
            Writer writer = new FileWriter(CONFIG_FILE);
            gson.toJson(pref,writer);
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public Boolean verify(String username,String password){
        String qu = "SELECT * FROM ADMIN WHERE username = '" + username + "' AND password = '" + password + "'";
        
        try {
            ResultSet rs = handler.execQuery(qu);
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("preferences class --> verify method --> error");
            //Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    

}
