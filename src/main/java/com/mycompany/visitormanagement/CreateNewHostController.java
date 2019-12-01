/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visitormanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.visitormanagement.MainController.Host;
import static com.mycompany.visitormanagement.MainController.styleAlert;
import java.net.URL;
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
public class CreateNewHostController implements Initializable {

    @FXML
    private JFXTextField nameHost;
    @FXML
    private JFXTextField phoneHost;
    @FXML
    private JFXTextField emailHost;
    @FXML
    private JFXButton createButton;
    @FXML
    private JFXButton cancelButton;

    
    databaseHandler handler;
    MainController mainc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = databaseHandler.getInstance();
        mainc = new MainController();
    }    

    @FXML
    private void handleCreateButton(ActionEvent event) {
        
        String name = nameHost.getText();
        String phone = phoneHost.getText();
        String email = emailHost.getText();
        Boolean valid = false;
        
        if(name.isEmpty() || phone.isEmpty() || email.isEmpty()){
            //error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter all fileds");
            styleAlert(alert);
            alert.showAndWait();
            return;
        }
        
        String qu = "INSERT INTO HOST  VALUES ("+
            "'" + name + "'," + 
            "'" + phone + "'," + 
            "'" + email + "'" + 
            ")";
        //System.out.println(qu);
        if(handler.execAction(qu)){
            //success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added");
            styleAlert(alert);
            alert.showAndWait();
            valid = true;
            
            
        }
        else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error");
            styleAlert(alert);
            alert.showAndWait();
        }
       
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        ((Stage) nameHost.getScene().getWindow()).close();
    }
    
}
