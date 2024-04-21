package Bluetrain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        StackPane lightBluePadding = Functions.welcomePane();

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

        Button menuButton = (Button) leftBox.getChildren().get(2); // Get the MENU button
        menuButton.setOnAction(e -> Menu.showMenuPopup(primaryStage));

        Button staffButton = (Button) centreBox.getChildren().get(2); // Get the MENU button
        staffButton.setOnAction(e -> Staff.showStaffPopup(primaryStage));

        Button reportButton = (Button) centreBox.getChildren().get(1); // Get Reports button.
        reportButton.setOnAction(e -> new ReportSelectionScreen().start(primaryStage));
    }

    private Button createButton(String text, Color bgColor) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setTextFill(Color.DARKBLUE);
        button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setPrefWidth(150);
        button.setPrefHeight(150);
        return button;
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
