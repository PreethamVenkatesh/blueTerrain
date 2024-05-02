package BlueTerrain;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point of the BlueTerrain application.
 * @author Preetham Venkatesh
 */
public class Main extends Application {
   /**
     * The main method is the entry point of the Java application.
     * It launches the JavaFX application.
     * 
     * @param args The command line arguments passed to the application.
     */ 
    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application by calling the launch method.
    }

    @Override
    public void start(Stage primaryStage) {
   // Initializes a new instance of the Login class.
        Login login = new Login();
   // Starts the login process by calling the start method of the Login instance, passing the primary stage.
        login.start(primaryStage);
        
    }
}
