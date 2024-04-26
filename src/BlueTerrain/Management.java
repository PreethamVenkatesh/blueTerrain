package BlueTerrain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Management {
    public static void showManagementPopup(Stage primaryStage) {

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setBackground(Functions.backGroundImage("/BlueTerrain/Images/BT_Common.jpeg"));

        Button dineInButton = Functions.createButtonMenu("Dine-In", Color.LAVENDER);
        Button takeAwayButton = Functions.createButtonMenu("Take-Away", Color.LAVENDER);
        Button bookingStatusButton = Functions.createButtonMenu("Booking Status", Color.LAVENDER);

        HBox buttonsBox = new HBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(dineInButton, takeAwayButton, bookingStatusButton);

        Functions.setMarginForNode(root, buttonsBox, new Insets(20, 20, 20, 0));

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            Restaurant restaurant = new Restaurant();
            restaurant.start(primaryStage, Menu.getFirstName(), Menu.getLastName(), Menu.getProfileType());
        });

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }
}
