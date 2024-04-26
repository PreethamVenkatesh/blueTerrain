package BlueTerrain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Restaurant {

    public void start(Stage primaryStage, String firstName, String lastName, String profileType) {
        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        Background background = Functions.backGroundImage("/BlueTerrain/Images/BT_Common.jpeg");
        root.setBackground(background);

        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" HH:mm:ss dd-MM-yyyy");
        String formattedDateTime = now.atZone(zoneId).format(formatter);
        Label timeLabel = new Label("Logged in at: " + formattedDateTime);
        timeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14;");

        VBox currentTimeBox = new VBox(timeLabel);
        currentTimeBox.setAlignment(Pos.TOP_RIGHT);

        Label userDetailsLabel = new Label(firstName + " " + lastName + " - " + profileType);
        userDetailsLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");

        VBox userDetailsBox = new VBox(userDetailsLabel);
        userDetailsBox.setAlignment(Pos.TOP_LEFT);

        Label openingHoursLabel = Functions.openingHours();

        VBox leftBox = Functions.createButtonVBox(Color.YELLOW, "BOOKINGS", "MANAGEMENT");
        VBox centreBox = Functions.createButtonVBox(Color.ORANGE, "MENU", "STAFFS");
        VBox rightBox = Functions.createButtonVBox(Color.GREENYELLOW, "ORDERS", "REPORTS");

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        root.getChildren().addAll(Functions.welcomePane(), userDetailsBox, openingHoursLabel, buttonsBox, currentTimeBox);
        Functions.setupAndShowScene(primaryStage, root);

        Button menuButton = (Button) centreBox.getChildren().get(0); 
        menuButton.setOnAction(e -> Menu.showMenu(primaryStage));

        Button staffButton = (Button) centreBox.getChildren().get(1); 
        staffButton.setOnAction(e -> Staff.showStaffPopup(primaryStage));

        Button managementButton = (Button) leftBox.getChildren().get(1);
        managementButton.setOnAction(e -> Management.showManagementPopup(primaryStage));

        Menu.setFirstName(firstName);
        Menu.setLastName(lastName);
        Menu.setProfileType(profileType);
    }

}
