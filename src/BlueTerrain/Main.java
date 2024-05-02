package BlueTerrain;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point of the BlueTerrain application.
 * @author Preetham
 */
public class Main extends Application {
   /**
     * The main method is the entry point of the Java application.
     * It launches the JavaFX application.
     * 
     * @param args The command line arguments passed to the application.
     */ 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Login login = new Login();
        login.start(primaryStage);
        
    }
}