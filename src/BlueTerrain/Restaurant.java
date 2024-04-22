package BlueTerrain;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        StackPane lightBluePadding = Functions.restuarantLabel();

        Label openingHoursLabel = new Label("Opening Hours: 11:00 AM - 12:00 PM");
        openingHoursLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        openingHoursLabel.setStyle("-fx-text-fill: darkblue;");

        VBox leftBox = Functions.createButtonVBox(Color.YELLOW, "BOOKINGS", "EVENTS", "MENU");
        VBox centreBox = Functions.createButtonVBox(Color.ORANGE, "ORDERS", "TABLES", "STAFFS");
        VBox rightBox = Functions.createButtonVBox(Color.GREENYELLOW, "DELIVERY", "REPORTS", "HISTORY");

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        root.getChildren().addAll(lightBluePadding, openingHoursLabel, buttonsBox);
        Functions.setupAndShowScene(primaryStage, root, 800, 600);

        Button menuButton = (Button) leftBox.getChildren().get(2); 
        menuButton.setOnAction(e -> Menu.showMenuPopup(primaryStage));

        Button staffButton = (Button) centreBox.getChildren().get(2); 
        staffButton.setOnAction(e -> Staff.showStaffPopup(primaryStage));

        Button tablesButton = (Button) centreBox.getChildren().get(1); 
        tablesButton.setOnAction(e -> Tables.showTablesPopup(primaryStage));
    }

}
