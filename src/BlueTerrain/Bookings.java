package BlueTerrain;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Bookings {

    
    public void start(Stage primaryStage) {
        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        StackPane lightBluePadding = Functions.restuarantLabel();

        Label openingHoursLabel = Functions.openingHours();

        root.getChildren().addAll(lightBluePadding, openingHoursLabel);
        Functions.setupAndShowScene(primaryStage, root, 800, 600);
    }
    
}
