/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visitormanagement;
import Email.SimpleEmail;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private JFXTextField hostName;
    @FXML
    private JFXTextField hostLocation;
    @FXML
    private JFXButton checkInButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private StackPane stackpane;
    
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
    private JFXTextField hostName1;
    @FXML
    private JFXTextField hostLocation1;
    @FXML
    private JFXButton checkoutButton;
    @FXML
    private JFXButton cancelButton1;
    

    @FXML
    private TableView<Visitor> tableView1;
    
    @FXML
    private TableColumn<Visitor, String> nameCol1;
    @FXML
    private TableColumn<Visitor, String> phoneCol1;
    @FXML
    private TableColumn<Visitor, String> emailCol1;
    @FXML
    private TableColumn<Visitor, String> checkinCol1;
    @FXML
    private TableColumn<Visitor, String> checkoutCol1;
    @FXML
    private TableColumn<Visitor, String> hostnameCol1;
    @FXML
    private TableColumn<Visitor, String> locationCol1;
    @FXML
    private MenuItem createnewhost;
    @FXML
    private MenuItem createnewadmin;
    @FXML
    private TableView<Host> hostTable;
    @FXML
    private TableColumn<Host, String> hostNameCol;
    @FXML
    private TableColumn<Host, String> hostPhoneCol;
    @FXML
    private TableColumn<Host, String> hostEmailCol;

    
    
     databaseHandler databasehandler;
    ObservableList<Visitor> list = FXCollections.observableArrayList();//this shows the list of current visitors
    ObservableList<Visitor> list1 = FXCollections.observableArrayList();//this shows the record of previous visitor
    ObservableList<Host> list2 = FXCollections.observableArrayList();//thie list shows the table of hosts present
    String visitorCheckIn = null;
    Visitor vis = null;
    @FXML
    private MenuItem removehost;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databasehandler = databaseHandler.getInstance();
        inittable();//current visitors table initiating
        initRecord();//record of all visitors initiating
        initHostTable();//record table of host is initiating
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
            styleAlert(alert);
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
            styleAlert(alert);
            alert.showAndWait();
        }
        else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            styleAlert(alert);
            alert.showAndWait();
        }
        
        loadData();
        //addInTable(vis);
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
    
    
    private void inittable() {
        //System.out.println("loading table...now");
        initCol();
        loadData();
    }
    
    private void initCol(){
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        locationCol.setCellValueFactory(new PropertyValueFactory("hostlocation"));
        checkinCol.setCellValueFactory(new PropertyValueFactory("checkin"));
        hostnameCol.setCellValueFactory(new PropertyValueFactory("hostname"));
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
                list.add(new Visitor(name,phone,email,checkintime,host,location,checkintime));
                //System.out.println("size of list is : " + list.size());
            }
        }
        
        catch(SQLException e){
            //e.printStackTrace();
            //Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,e);
            System.out.println("Error in fxmlcontroller loadData() method");
        }
        tableView.getItems().setAll(list);
    }
    
    private void addInTable(Visitor vis){
        list.add(vis);
        //tableView.getItems().setAll(list);
    }
    
    private void removeFromTable(Visitor vis){
        list.remove(vis);
        //tableView.getItems().setAll(list);
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
            styleAlert(alert);
            alert.showAndWait();
            
            handleCancel(event);
        }
        else if(counter==0){
            //no present visitor have these details
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed There is no visitor with given information");
            styleAlert(alert);
            alert.showAndWait();
            
            handleCancel(event);
        }
        else if(counter==1){
            checkoutName.setText(name);
            visitorPhone1.setText(phone);
            visitorEmail1.setText(email);

            hostLocation1.setText(location);
            hostName1.setText(host);
            
            vis =new  Visitor(name,phone,email,checkintime,host,location,checkintime);
            //handleCheckOut(name,phone,email,host,location);
        }
    }
    @FXML
    private void handleCheckOut(ActionEvent event) {

        if(vis==null){
            getDetails(event);
        }
        
        String name = vis.getName();
        String phone = vis.getPhone();
        String email = vis.getEmail();
        String location = vis.getHostlocation();
        String host = vis.getHostname();
        String time = getCurrentTimeUsingCalendar();
        
        String qu = "UPDATE VISITOR SET checkouttime = '" + time + "' WHERE name = '" + name + "' AND phone = '" + phone + "' AND email = '" + email + "' AND host = '" + host + "' AND location = '" + location +"' ";
        
        if(databasehandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfull Check Out at : " + time);
            styleAlert(alert);
            alert.showAndWait();
            //checkData();
        }
        else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed to Check Out");
            styleAlert(alert);
            alert.showAndWait();
            handleCancel(event);
            return;
        }
        //updating table again
        
        emptyfield1();
        loadData();//updating the current visitor list
        //removeFromTable(vis);//updating the current visitor list;
        
        vis.setCheckout(time);
        addVisitor(vis);//updating record list
        
        String checkout = time;
        sendEmailToVisitor(vis.getName(),vis.getPhone(),vis.getCheckin(),checkout,vis.getHostname(),vis.getHostlocation(),vis.getEmail());
        
        vis = null;
    }
    
        @FXML
    private void handleCancel1(ActionEvent event) {
        emptyfield1();
        vis = null;
    }
    
    public void emptyfield1(){
        visitorPhone1.setText("");
        visitorEmail1.setText("");
        checkoutName.setText("");
        hostName1.setText("");
        hostLocation1.setText("");
    }

    //******************CONTROLLER FOR CHECK OUT ENDS*****************//
    
    //****************** CONTROLLER FOR RECORD TABLE STARTS*****************//
    
    
    private void initRecord(){
        initCol1();
        loadRecords();
    }
    
        private void initCol1(){
        nameCol1.setCellValueFactory(new PropertyValueFactory("name"));
        emailCol1.setCellValueFactory(new PropertyValueFactory("email"));
        phoneCol1.setCellValueFactory(new PropertyValueFactory("phone"));
        locationCol1.setCellValueFactory(new PropertyValueFactory("hostlocation"));
        checkinCol1.setCellValueFactory(new PropertyValueFactory("checkin"));
        checkoutCol1.setCellValueFactory(new PropertyValueFactory("checkout"));
        hostnameCol1.setCellValueFactory(new PropertyValueFactory("hostname"));
    }
    
    private void loadRecords() {
        
        String qu = "SELECT * FROM VISITOR WHERE NOT checkouttime = checkintime";
        ResultSet rs = databasehandler.execQuery(qu);
        list1.clear();
        try{
            while(rs.next()){
                String name = rs.getString("name");
                String host = rs.getString("host");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String location = rs.getString("location");
                String checkintime = rs.getString("checkintime");
                String checkouttime = rs.getString("checkouttime");
                //System.out.println("for table " + email);
                list1.add(new Visitor(name,phone,email,checkintime,host,location,checkouttime));
                //System.out.println("size of list is : " + list.size());
            }
        }
        
        catch(SQLException e){
            //Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,e);
            //System.out.println("Error in fxmlcontroller loadData() method");
            
        }
        tableView1.getItems().setAll(list1);
    }
    
    private void addVisitor(Visitor vis){
        list1.add(vis);
        tableView1.getItems().setAll(list1);
    }
    

    //******************CONTROLLER FOR RECORD ENDS*****************//
    
    //*****************CONTROLLER FOR HOST LIST*****************//
    
    private void initHostTable(){
        initCol2();
        loadHostList();
    }
    
    private void initCol2(){
        hostNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        hostEmailCol.setCellValueFactory(new PropertyValueFactory("email"));
        hostPhoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
    }
    
    private void loadHostList() {
        
        String qu = "SELECT * FROM HOST";
        ResultSet rs = databasehandler.execQuery(qu);
        list2.clear();
        try{
            while(rs.next()){
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                //System.out.println("for table " + email);
                list2.add(new Host(name,phone,email));
                //System.out.println("size of list is : " + list.size());
            }
        }
        
        catch(SQLException e){
            //Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE,null,e);
            //System.out.println("Error in fxmlcontroller loadData() method");
        }
        hostTable.getItems().setAll(list2);
    }
   
    
    public void addHostToList(Host host){
        list2.add(host);
        System.out.println(host);
        if(hostTable==null) System.out.println("this is null");
        else hostTable.getItems().setAll(list2);
    }
    
    public  void removeHostFromList(Host host){
        list2.remove(host);
        hostTable.getItems().setAll(list2);
    }
    
    //*****************CONTROLLER FOR HOST RECORD ENDED**************//
    
    //**************8 Menu Bar ***********************************//
    
    @FXML
    private void CreateNewHost(ActionEvent event) {
        String loc = "/fxml/CreateNewHost.fxml";
        String title = "Add Host";
        loadWindow(loc,title);
    
    }
    @FXML
    private void RemoveHost(ActionEvent event) {
        String loc = "/fxml/RemoveHost.fxml";
        String title = "Remove Host";
        loadWindow(loc,title);
    }

    
    //********************* Menubar ENDED***************//
   
    //***************HELPER FUNCTIONS AND CLASSES********************//

    public static void sendEmailToHost(String visName,String visPhone, String visEmail, String host,String location){
        
        String body = "Hello " + host + ", \nIt is machine generated Mail to infom you about the visitor who just came to visit you below is the detailed"
                + " Information. \nName : " + visName + "\nPhone : " + visPhone + " \nEmail : "  + visEmail + "\nWhere to visit : " + location + " \n";
        if(SimpleEmail.sendmail("syedfaizan824@gmail.com", "Visitor Inormation", body)){// success do nothing

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Email is not sent becauese of some error ( please check your internet connection may be) ");
            styleAlert(alert);
            alert.showAndWait();
        }
    }
    
    public static void sendEmailToVisitor(String name,String phone,String checkin,String checkout, String host, String location, String visEmail){
        String body = "Hello " + name + ", \nIt is machine generated Mail to inform you about the visit. \nName : " + name + " \nPhone : " + phone + " \nCheck-In Time "
                + ": " + checkin + " \nCheck-Out Time : " + checkout + " \nHost : " + host + "  \nAddress Visited : " + location  + " \n" ;
        if(SimpleEmail.sendmail(visEmail, "Your Visit Info", body)){// success do nothing

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Email is not sent becauese of some error ( please check your internet connection may be) ");
            styleAlert(alert);
            alert.showAndWait();
        }
    }
    
    public static String getCurrentTimeUsingCalendar() {
        java.util.Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd-MMMM-yyyy");  
        String strDate = dateFormat.format(date);  
        return strDate;
        /*Timestamp time = new Timestamp(System.currentTimeMillis());
        return time;*/
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
    
    void loadWindow(String loc,String title){

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
    
    public static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/styles/darktheme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }



    public class Visitor{
        private final SimpleStringProperty name;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;
        private final SimpleStringProperty checkin;
        private SimpleStringProperty checkout;
        private final SimpleStringProperty hostname;
        private final SimpleStringProperty hostlocation;
        
        
        /*
                nameCol1.setCellValueFactory(new PropertyValueFactory("name"));
        emailCol1.setCellValueFactory(new PropertyValueFactory("email"));
        phoneCol1.setCellValueFactory(new PropertyValueFactory("phone"));
        locationCol1.setCellValueFactory(new PropertyValueFactory("hostlocation"));
        checkinCol1.setCellValueFactory(new PropertyValueFactory("checkin"));
        checkoutCol1.setCellValueFactory(new PropertyValueFactory("checkout"));
        hostnameCol1.setCellValueFactory(new PropertyValueFactory("hostname"));
        */
        
        public Visitor(String name,String phone,String email,String checkin, String hostname,String hostlocation,String checkout ){
            this.name = new SimpleStringProperty(name);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
            this.checkin = new SimpleStringProperty(checkin);
            this.checkout = new SimpleStringProperty(checkout);
            this.hostname = new SimpleStringProperty(hostname);
            this.hostlocation = new SimpleStringProperty(hostlocation);
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
        
        public String getCheckout(){
            return checkout.get();
        }
        public void setCheckout(String checkouttime){
            this.checkout = new SimpleStringProperty(checkouttime);
        }
        
        
        
    }
    
    
    public class Host{
        private final SimpleStringProperty name;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;
        
        public Host(String name,String phone, String email){
            this.name = new SimpleStringProperty(name);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
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
    }
    //************************HELPER FUNCTIONS AND CLASSES END*****************//
    
    
    
}
