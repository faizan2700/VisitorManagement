package com.mycompany.visitormanagement;



import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("/fxml/loginWindow.fxml"));
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println("location problem");
            System.exit(0);
        }
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/addMember.css");
        
        stage.setTitle("Visitor Management Login");
        stage.setScene(scene);
        
        //lets load databasae while loading the software main window so that next time user access database it will not take much time to load
        databaseHandler.getInstance();
        
        //lets start preferred settings (username , password for now)
        Preferences.initConfig();
 
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
