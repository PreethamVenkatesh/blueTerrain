package BlueTerrain;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * The Controller class controls the behavior of the FXML view.
 */
public class Controller {
    
    @FXML
    private Label label;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the FXMLLoader once all FXML elements have been loaded.
     * It sets the text of the label to display information about the Java and JavaFX versions.
     */
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }
}
