package BlueTerrain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Restaurant {
    
    public void start(Stage primaryStage) {
        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        Color lightBlue = Color.rgb(173, 216, 230);
        StackPane lightBluePadding = new StackPane();
        lightBluePadding.setBackground(new Background(new BackgroundFill(lightBlue, CornerRadii.EMPTY, Insets.EMPTY)));
        lightBluePadding.setPadding(new Insets(10));
        Label welcomeLabel = new Label("BlueTerrain");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        welcomeLabel.setStyle("-fx-text-fill: darkblue;");
        lightBluePadding.getChildren().add(welcomeLabel);

        Label openingHoursLabel = new Label("Opening Hours: 11:00 AM - 12:00 PM");
        openingHoursLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        openingHoursLabel.setStyle("-fx-text-fill: darkblue;");

        VBox leftBox = createButtonVBox(Color.YELLOW, "BOOKINGS", "EVENTS", "MENU");
        VBox centreBox = createButtonVBox(Color.ORANGE, "ORDERS", "TABLES", "STAFFS");
        VBox rightBox = createButtonVBox(Color.GREENYELLOW, "DELIVERY", "REPORTS", "HISTORY");

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        root.getChildren().addAll(lightBluePadding, openingHoursLabel, buttonsBox);
        Functions.setupAndShowScene(primaryStage, root, 800, 600);
    }

    private VBox createButtonVBox(Color color, String... buttonLabels) {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        
        for (String label : buttonLabels) {
            Button button = createButton(label, color);
            box.getChildren().add(button);
        }
        return box;
    }
}
