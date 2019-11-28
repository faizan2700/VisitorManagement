/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visitormanagement;
import Email.SimpleEmail;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Smart Gadget
 */
//"com.mycompany.visitormanagement.MainController"
public class MainController implements Initializable {

    @FXML
    private BorderPane BorderPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu editMenu;
    @FXML
    private Menu helpMenu;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<Visitor> tableView;
    @FXML
    private TableColumn<Visitor, String> nameCol;
    @FXML
    private TableColumn<Visitor, String> phoneCol;
    @FXML
    private TableColumn<Visitor, String> emailCol;
    @FXML
    private TableColumn<Visitor, String> checkinCol;
    @FXML
    private TableColumn<Visitor, String> hostnameCol;
    @FXML
    private TableColumn<Visitor, String> locationCol;
    @FXML
    private Tab visitorPane;
    @FXML
    private AnchorPane visitor;
    @FXML
    private JFXTextField visitorName;
    @FXML
    private JFXTextField visitorPhone;
    @FXML
    private JFXTextField visitorEmail;
    @FXML
    private JFXTextField checkInTime;
    @FXML
    private JFXTextField hostName;
    @FXML
    private JFXTextField hostLocation;
    @FXML
    private JFXButton checkInButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private StackPane stackpane;
    
    databaseHandler databasehandler;
    //list for filling table
    ObservableList<Visitor> list = FXCollections.observableArrayList();
    @FXML
    private Tab visitorPane1;
    @FXML
    private AnchorPane visitor1;
    @FXML
    private JFXTextField checkoutName;
    @FXML
    private JFXTextField visitorPhone1;
    @FXML
    private JFXTextField visitorEmail1;
    @FXML
    private JFXTextField checkInTime1;
    @FXML
    private JFXTextField hostName1;
    @FXML
    private JFXTextField hostLocation1;
    @FXML
    private JFXButton checkoutButton;
    @FXML
    private JFXButton cancelButton1;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = new databaseHandler();
        inittable();
    }   
    
    //********************CONTROLLER FOR CHECK IN WINDOW ********************//
    
    @FXML
    private void handleCheckIn(ActionEvent event) {
        String visName = visitorName.getText();
        String visEmail = visitorEmail.getText();
        String visPhone = visitorPhone.getText();
        String visCheckin = getCurrentTimeUsingCalendar();
        String host = hostName.getText();
        String visLocation = hostLocation.getText();
        String visCheckout = visCheckin;
    
        if(visName.isEmpty() || visEmail.isEmpty() || visPhone.isEmpty() || host.isEmpty() || visLocation.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter all the fields");
            alert.showAndWait();
            return;
        }
        
        String qu = "INSERT INTO VISITOR  VALUES ("+
                "'" + visCheckin + "'," + 
                "'" + visName + "'," + 
                "'" + visPhone + "'," + 
                "'" + visEmail + "'," + 
                "'" + visCheckout + "'," +
                "'" + host + "'," +
                "'" + visLocation + "'" +
                ")";
        //System.out.println(qu);
        if(databasehandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added");
            alert.showAndWait();
        }
        else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
        
        loadData();
        emptyfield();
        sendEmailToHost(visName,visPhone,visEmail,host,visLocation);//implemented in helper functins section in this file
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        emptyfield();
    }
    
    public void emptyfield(){
        visitorPhone.setText("");
        visitorEmail.setText("");
        visitorName.setText("");
        hostName.setText("");
        hostLocation.setText("");
    }
    
    //************* CONTROLLER FOR CHECK IN ENDS ***************//
    
    // **************** CONTROLLER FOR TABLE VIEW *********************//
    
    private void initCol(){
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        locationCol.setCellValueFactory(new PropertyValueFactory("hostlocation"));
        checkinCol.setCellValueFactory(new PropertyValueFactory("checkin"));
        hostnameCol.setCellValueFactory(new PropertyValueFactory("hostname"));
    }
    
    private void inittable() {
        //System.out.println("loading table...now");
        initCol();
        loadData();
    }
    
    private void loadData() {
        
        String qu = "SELECT * FROM VISITOR WHERE checkouttime = checkintime";
        ResultSet rs = databasehandler.execQuery(qu);
        list.clear();
        try{
            while(rs.next()){
                String name = rs.getString("name");
                String host = rs.getString("host");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String location = rs.getString("location");
                String checkintime = rs.getString("checkintime");
                //System.out.println("for table " + email);
                list.add(new Visitor(name,phone,email,checkintime,host,location));
                //System.out.println("size of list is : " + list.size());
            }
        }
        
        catch(SQLException e){
            //Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,e);
            System.out.println("Error in fxmlcontroller loadData() method");
        }
        tableView.getItems().setAll(list);
    }
    
    @FXML
    private void handleCancel1(ActionEvent event) {
        emptyfield1();
    }
    
    public void emptyfield1(){
        visitorPhone1.setText("");
        visitorEmail1.setText("");
        checkoutName.setText("");
        hostName1.setText("");
        hostLocation1.setText("");
    }
    
    
    //*********************TABLE VIEW CONTROLLER END****************//
    
    
    //***************CONTROLLER FOR CHECK OUT WINDOW ***************//
    @FXML
    private void getDetails(ActionEvent event) {
        
        String qu = "SELECT * FROM VISITOR WHERE checkouttime = checkintime" ;
        
        String name = checkoutName.getText();
        String host = hostName1.getText();
        String email = visitorEmail1.getText();
        String phone = visitorPhone1.getText();
        String location = hostLocation1.getText();
        String checkintime = null;
        
        if(name.isEmpty()==false){
            qu = qu + " AND name = '";
            qu = qu + name;
            qu = qu + "' ";
         }
        if(host.isEmpty()==false){
            qu = qu + " AND host = '";
            qu = qu + host;
            qu = qu + "' ";
        }
        if(email.isEmpty()==false){
            qu = qu + " AND email = '";
            qu = qu + email;
            qu = qu + "' ";
        }
        if(phone.isEmpty()==false){
            qu = qu + " AND phone = '";
            qu = qu + phone;
            qu = qu + "' ";
        }
        if(location.isEmpty()==false){
            qu = qu + " AND location = '";
            qu = qu + location;
            qu = qu + "' ";
        }
        
        //System.out.println(qu);
        ResultSet rs = databasehandler.execQuery(qu);
        
        int counter=0; //resutl we get should be one only
        try{
            while(rs.next()){
                counter++;
                name = rs.getString("name");
                host = rs.getString("host");
                email = rs.getString("email");
                phone = rs.getString("phone");
                location = rs.getString("location");
                checkintime = rs.getString("checkintime");
                //System.out.println("for table " + email);
                //System.out.println("the setting details : " + host + " " + email + " "  + phone + " " + location + " " + checkintime);
                
                
                //System.out.println("size of list is : " + list.size());
            }
        }
        
        catch(SQLException e){
            //Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,e);
            System.out.println("Error in fxmlcontroller checkoutdetailsfilling method");
        }
        
        if(counter>1){
            //Error Database have more than member present with these details
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed! There are more than one visitors currently present with given information");
            alert.showAndWait();
        }
        else if(counter==0){
            //no present visitor have these details
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed There is no visitor with given information");
            alert.showAndWait();
        }
        else if(counter==1){
            checkoutName.setText(name);
            visitorPhone1.setText(phone);
            visitorEmail1.setText(email);

            hostLocation1.setText(location);
            hostName1.setText(host);
            handleCheckOut(name,phone,email,host,location);
            
            String checkout = getCurrentTimeUsingCalendar(); 
            sendEmailToVisitor(name,phone,checkintime,checkout,host,location,email);
        }
    }

    public void handleCheckOut(String name,String phone,String email,String host,String location) {


        String time = getCurrentTimeUsingCalendar();
        String qu = "UPDATE VISITOR SET checkouttime = '" + time + "' WHERE name = '" + name + "' AND phone = '" + phone + "' AND email = '" + email + "' AND host = '" + host + "' AND location = '" + location +"' ";
        
        if(databasehandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfull Check Out");
            alert.showAndWait();
            //checkData();
        }
        else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed to Check Out");
            alert.showAndWait();
        }
        //updating table again
        loadData();
        emptyfield1();
    }

    //******************CONTROLLER FOR CHECK OUT ENDS*****************//
    
    
    
    
    //***************HELPER FUNCTIONS AND CLASSES********************//

    public static void sendEmailToHost(String visName,String visPhone, String visEmail, String host,String location){
        
        String body = "Hello " + host + ", \nIt is machine generated Mail to infom you about the visitor who just came to visit you below is the detailed"
                + " Information. \nName : " + visName + "\nPhone : " + visPhone + " \nEmail : "  + visEmail + "\nWhere to visit : " + location + " \n";
        
        SimpleEmail.sendmail("syedfaizan824@gmail.com", "Visitor Inormation", body);
    }
    
    public static void sendEmailToVisitor(String name,String phone,String checkin,String checkout, String host, String location, String visEmail){
        String body = "Hello " + name + ", \nIt is machine generated Mail to inform you about the visit. \nName : " + name + " \nPhone : " + phone + " \nCheck-In Time "
                + ": " + checkin + " \nCheck-Out Time : " + checkout + " \nHost : " + host + "  \nAddress Visited : " + location  + " \n" ;
        SimpleEmail.sendmail(visEmail, "Your Visit Info", body);
    }
    
    public static String getCurrentTimeUsingCalendar() {
        java.util.Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd-mm-yyyy");  
        String strDate = dateFormat.format(date);  
        return strDate;
    }
    
    private void checkData(){
        String qu = "SELECT email,checkouttime FROM VISITOR";
        ResultSet rs = databasehandler.execQuery(qu);
        try{
            //System.out.println("retrieving all visitor till now");
            while(rs.next()){
                String namex = rs.getString("email");
                String che = rs.getString("checkouttime");
                //System.out.println(namex + che);
            }
        }
        catch(SQLException e){
            //Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,e);
            System.out.println("Error in fxmlcontroller checkData() method");
        }
    }

    public class Visitor{
        private final SimpleStringProperty name;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;
        private final SimpleStringProperty checkin;
        private final SimpleStringProperty checkout;
        private final SimpleStringProperty hostname;
        private final SimpleStringProperty hostlocation;
        
        public Visitor(String name,String phone,String email,String checkin, String hostname,String hostlocation ){
            this.name = new SimpleStringProperty(name);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
            this.checkin = new SimpleStringProperty(checkin);
            this.hostname = new SimpleStringProperty(hostname);
            this.hostlocation = new SimpleStringProperty(hostlocation);
            this.checkout = new SimpleStringProperty("-1");
        }

        public String getName() {
            return name.get();
        }

        public String getPhone() {
            return phone.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getCheckin() {
            return checkin.get();
        }

        public String getHostname() {
            return hostname.get();
        }

        public String getHostlocation() {
            return hostlocation.get();
        }
        
        
        
    }
    //************************HELPER FUNCTIONS AND CLASSES END*****************//
    
}
