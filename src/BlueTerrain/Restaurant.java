package BlueTerrain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The Restaurant class represents the main functionality of the restaurant application.
 * It includes methods to start the application, display the main interface, and handle user interactions.
 * 
 * <p>This class contains functionality related to displaying the main menu, user details, and various options based on the user's profile type.</p>
 * 
 * <p>It also initializes and handles actions for buttons such as bookings, menu, staff management, orders, reports, and more.</p>
 * 
 * @author Preetham
 */
public class Restaurant {

    /**
     * Starts the restaurant application.
     * 
     * @param primaryStage The primary stage of the JavaFX application.
     * @param firstName    The first name of the logged-in user.
     * @param lastName     The last name of the logged-in user.
     * @param profileType  The profile type of the logged-in user (e.g., Manager, Waiter, Chef).
     * @param loginType    The login type of the user (e.g., Staff, Customer).
     */
    public void start(Stage primaryStage, String firstName, String lastName, String profileType, String loginType) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

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

        VBox bookingBox = Functions.createButtonVBox(Color.YELLOW, "BOOKINGS");
        VBox managementBox = Functions.createButtonVBox(Color.YELLOW, "MANAGEMENT");
        VBox menuBox = Functions.createButtonVBox(Color.ORANGE, "MENU");
        VBox staffsBox = Functions.createButtonVBox(Color.ORANGE, "STAFFS");
        VBox ordersBox = Functions.createButtonVBox(Color.GREENYELLOW, "ORDERS");
        VBox reportsBox = Functions.createButtonVBox(Color.GREENYELLOW, "REPORTS");
        VBox chefSpecialBox = Functions.createButtonVBox(Color.GREENYELLOW, "CHEF SPECIAL");

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        if (profileType.equals("Manager")) {
            buttonsBox.getChildren().addAll(bookingBox, menuBox, staffsBox, reportsBox);
        } else if (profileType.equals("Waiter")) {
            buttonsBox.getChildren().addAll(managementBox, menuBox, ordersBox);
        } else if (profileType.equals("Chef")) {
            buttonsBox.getChildren().addAll(ordersBox, chefSpecialBox);
        } else if (profileType.equals("Delivery Driver")) {
            buttonsBox.getChildren().addAll(ordersBox);
        } else {
            buttonsBox.getChildren().addAll(bookingBox, managementBox, menuBox, staffsBox, ordersBox, reportsBox);
        }

        root.getChildren().addAll(Functions.welcomePane(), userDetailsBox, openingHoursLabel, buttonsBox, Functions.logOut(primaryStage));
        Functions.setupAndShowScene(primaryStage, root);

        Button menuButton = (Button) menuBox.getChildren().get(0); 
        menuButton.setOnAction(e -> Menu.showMenu(primaryStage));

        Button staffButton = (Button) staffsBox.getChildren().get(0); 
        staffButton.setOnAction(e -> Staff.showStaffPopup(primaryStage));

        Button managementButton = (Button) managementBox.getChildren().get(0);
        managementButton.setOnAction(e -> Management.showManagementPopup(primaryStage));

        Button chefSpecialButton = (Button) chefSpecialBox.getChildren().get(0);
        chefSpecialButton.setOnAction(e -> ChefSpecial.showChefSpecialPopup(primaryStage));

        Button reportsButton = (Button) reportsBox.getChildren().get(0);
        reportsButton.setOnAction(e -> Reports.showReportsPopup(primaryStage));

        Button ordersButton = (Button) ordersBox.getChildren().get(0);
        ordersButton.setOnAction(e -> CustomerOrder.showOrder(primaryStage, firstName, lastName, loginType, profileType));

        Menu.setFirstName(firstName);
        Menu.setLastName(lastName);
        Menu.setProfileType(profileType);
    }
}