/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visitormanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author Smart Gadget
 */
public class LoginWindowController implements Initializable {

    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label titleLabel;
    public Preferences pref;
    databaseHandler handler;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pref = new Preferences();
        handler = databaseHandler.getInstance();
        //addAdmin("admin",DigestUtils.shaHex("admin"));
        //printAll();
    }    

    @FXML
    private void handleLogin(ActionEvent event) {
        
        titleLabel.setText("Visitor Management System");
        titleLabel.setStyle("-fx-background-color : black; -fx-text-fill : white");
        
        String name = username.getText();
        String pass = DigestUtils.shaHex(password.getText());
        
        if(pref.verify(name,pass)){
            //login
            ((Stage) username.getScene().getWindow()).close();
            loadMain();
        }
        else{
            //invalid
            titleLabel.setText("Invalid Credentials");
            titleLabel.setStyle("-fx-background-color : #d32f2f; -fx-text-fill : white;");
            
        }
    }
    
    @FXML
    private void cancelButton(ActionEvent event) {
        System.exit(0);
    }
    
    void loadMain(){
        String loc = "/fxml/main.fxml";
        String title = "Visitor Management System";
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void addAdmin(String username, String password){
        String qu = "INSERT INTO ADMIN VALUES ("+
                "'" + username + "'," + 
                "'" + password + "'" +
                ")";
        if(handler.execAction(qu)){
            //success           
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added Admin");
            alert.showAndWait();
        }
        else{
            //error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error Occured while adding new admin");
            MainController.styleAlert(alert);
            alert.showAndWait();
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
            System.out.println("LoginWindowController --> verify method --> error");
            //Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void printAll(){
        String qu = "SELECT * FROM ADMIN ";
        try{
            ResultSet rs = handler.execQuery(qu);
            while(rs.next()){
                String user = rs.getString("username");
                String pass = rs.getString("password");
                System.out.println(user + "     " + pass);
            }
        }
        catch(SQLException ex){
            System.out.print("cant print all the username and password");
        }
    }
}
