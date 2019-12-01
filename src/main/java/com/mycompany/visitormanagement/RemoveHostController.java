/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visitormanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static com.mycompany.visitormanagement.MainController.styleAlert;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Gadget
 */
public class RemoveHostController implements Initializable {

    @FXML
    private JFXTextField removeHostName;
    @FXML
    private JFXButton removebutton;
    
    private databaseHandler handler;
    private MainController mainc;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = databaseHandler.getInstance();
        mainc = new MainController(); 
    }    

    @FXML
    private void handleRemoveHost(ActionEvent event) {
        String name = removeHostName.getText();
        String phone =null;
        String email = null;
        String qu = "SELECT * FROM HOST WHERE name = '" + name + "'";
        ResultSet rs = handler.execQuery(qu);
        try {
            if(!rs.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No such member");
                styleAlert(alert);
                alert.showAndWait();
            }
            else{
                phone = rs.getString("phone");
                email = rs.getString("email");
                qu = "DELETE FROM HOST WHERE name = '" + name + "'" ;
                if(handler.execAction(qu)){
                    //success
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Removed");
                    styleAlert(alert);
                    alert.showAndWait();
                    
                    
                }
                else{
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Error!");
                    styleAlert(alert);
                    alert.showAndWait();
                }
               
            }
        } catch (SQLException ex) {
            //Logger.getLogger(RemoveHostController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void cancelRemoveHost(ActionEvent event) {
        ((Stage)removeHostName.getScene().getWindow()).close();
    }
    
}
