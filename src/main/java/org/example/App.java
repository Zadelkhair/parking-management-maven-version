package org.example;

import com.github.sarxos.webcam.WebcamPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.controller.ViewController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * JavaFX App
 */
public class App extends Application {
    
    //database object helper, based on JDBC 
    //this class allow us to interract with the database
    public static Database db;
    
    //configuration object
    public static Config config;
    
    //Authentificated user , his name , password , roles ...
    //functions like logout , login ...
    public static Auth auth;
    
    //The middleware for security reason's
    public static Middleware middleware;
    
    //Class that controlle all navigation's in the application
    public static ViewController viewController;
    
    //Map key value , we use it to make interraction between all controller's possible
    public static Map<String, Object> passed_data;

    // The entry point of our application
    @Override
    public void start(Stage stage) throws IOException {

//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
//        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

        //App.go(stage);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to restar the application ?");
        AtomicBoolean b = new AtomicBoolean(false);
        do {
            
            //Class the go function , to start the application
            b.set(App.go(stage));

            if (!b.get()) {
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        b.set(false);
                    } else {
                        b.set(true);
                    }
                });
            }
        } while (!b.get());
        
    }
    
    //Start the application if the database is well configuered and the user is authentificated 
    private static boolean go(Stage stage) {
        go();
        
        //check if the database is confegured
        if (db.checkDatabaseConnection() != 1) {
            (new ViewController()).display("databaseconfig.fxml", new Stage(), true);
            return false;
        }

        //check if user is authetificated
        if (!auth.isLoggedIn()) {
            viewController = new ViewController();

            viewController.display("login.fxml", new Stage(), true);

            if(!auth.isLoggedIn())
                return false;

            stage.initStyle(StageStyle.UNDECORATED);
            
            //call the displayMain to display the main page (The container will containe all pages of our application) 
            viewController.displayMain("container.fxml", stage);
        }

        return true;
    }

    //Check the database configuration , will return true if it's well configured
    public static void go() {
        config = Config.getConfig();

        String DB_URL = (String) config.getFromConfig("DB_URL");
        String DB_NAME = (String) config.getFromConfig("DB_NAME");
        String DB_USER = (String) config.getFromConfig("DB_USER");
        String DB_PASSWORD = (String) config.getFromConfig("DB_PASSWORD");

        db = new Database(DB_URL,DB_NAME,DB_USER,DB_PASSWORD);

        auth = new Auth();

        middleware = new Middleware(auth);

        passed_data = new HashMap<>();
    }

    public static void passData(String key, Object val) {

        passed_data.put(key, val);

    }

    public static Object getData(String key) {
        return getData(key, true);
    }

    public static Object getData(String key, boolean delete_after_use) {

        if (!passed_data.containsKey(key)) {
            return null;
        }

        Object o = passed_data.get(key);

        if (delete_after_use) {
            passed_data.remove(key);
        }

        return o;
    }

}
