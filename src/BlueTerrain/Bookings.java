package BlueTerrain;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Bookings {

    
    public void start(Stage primaryStage) {
        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        

        Label openingHoursLabel = Functions.openingHours();

        root.getChildren().addAll(Functions.welcomePane(), openingHoursLabel);
        Functions.setupAndShowScene(primaryStage, root);
    }
    
}
