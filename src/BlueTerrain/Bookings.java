package BlueTerrain;

import javafx.application.Application;
<<<<<<< Updated upstream
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
=======
>>>>>>> Stashed changes
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

<<<<<<< Updated upstream
public class Bookings extends Application {

    // Data model for bookings
    private ObservableList<Booking> bookingsData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // Create the main scene with two buttons
        Button createButton = new Button("Create Bookings");
        Button viewButton = new Button("View My Bookings");

        VBox mainLayout = new VBox(20, createButton, viewButton);
        mainLayout.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(mainLayout, 400, 200);

        // Event handling for the createButton
        createButton.setOnAction(e -> {
            // Switch to the create bookings scene
            primaryStage.setScene(createBookingsScene(primaryStage));
        });

        // Event handling for the viewButton
        viewButton.setOnAction(e -> {
            // Switch to the view bookings scene
            // Code to implement this functionality goes here
        });

        // Set the main scene to the stage
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Table Booking Screen");
        primaryStage.show();
    }

    // Method to create the scene for creating bookings
    private Scene createBookingsScene(Stage primaryStage) {
        // Create labels and text fields for input
        Label tableNumberLabel = new Label("Table Number:");
        TextField tableNumberField = new TextField();
        Label guestsLabel = new Label("Number of Guests:");
        TextField guestsField = new TextField();
        Label durationLabel = new Label("Duration:");
        TextField durationField = new TextField();

        // Create a button to add a booking
        Button addButton = new Button("Add Booking");
        addButton.setOnAction(e -> {
            String tableNumber = tableNumberField.getText();
            int guests = Integer.parseInt(guestsField.getText());
            String duration = durationField.getText();

            // Generate a unique booking ID
            String bookingID = generateBookingID();

            // Create a new booking object and add it to the data model
            Booking booking = new Booking(bookingID, tableNumber, guests, duration);
            bookingsData.add(booking);

            // Show confirmation message
            showAlert("Booking Confirmed", "Your booking is confirmed!");

            // Clear input fields
            tableNumberField.clear();
            guestsField.clear();
            durationField.clear();
        });

        // Create a layout to hold the input fields and button
        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(tableNumberLabel, tableNumberField, guestsLabel, guestsField,
                durationLabel, durationField, addButton);
        inputLayout.setAlignment(Pos.CENTER);

        // Create the scene for creating bookings
        Scene createBookingsScene = new Scene(inputLayout, 400, 300);
        return createBookingsScene;
    }

    // Method to generate a unique booking ID
    private String generateBookingID() {
        // Implement your own logic to generate a unique ID using UUID
        return java.util.UUID.randomUUID().toString();
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Booking class to represent a booking
    public static class Booking {
        private final String bookingID;
        private final String tableNumber;
        private final int guests;
        private final String duration;

        public Booking(String bookingID, String tableNumber, int guests, String duration) {
            this.bookingID = bookingID;
            this.tableNumber = tableNumber;
            this.guests = guests;
            this.duration = duration;
        }

        public String getBookingID() {
            return bookingID;
        }

        public String getTableNumber() {
            return tableNumber;
        }

        public IntegerProperty guestsProperty() {
            return new SimpleIntegerProperty(guests);
        }

        public StringProperty bookingIDProperty() {
            return new SimpleStringProperty(bookingID);
        }

        public StringProperty tableNumberProperty() {
            return new SimpleStringProperty(tableNumber);
        }

        public StringProperty durationProperty() {
            return new SimpleStringProperty(duration);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bookings extends Application {

    // JDBC connection parameters
        
    String databaseName = "cafedb";
    String USERNAME = "root";
    String PASSWORD = "2360313";
    String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/" + databaseName;

    @Override
    public void start(Stage primaryStage) {
        // Create buttons for booking and viewing bookings
        Button makeBookingButton = new Button("Make Booking");
        Button viewBookingsButton = new Button("View Bookings");

        // Event handling for make booking button
        makeBookingButton.setOnAction(event -> {
            // Switch to the booking scene
            primaryStage.setScene(createBookingScene(primaryStage));
        });

        // Event handling for view bookings button
        viewBookingsButton.setOnAction(event -> {
            // Fetch and display bookings from the database
            showAlert("View Bookings", "Fetching bookings from the database...");
        });

        // Create the main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(makeBookingButton, viewBookingsButton);

        // Create the main scene
        Scene scene = new Scene(mainLayout, 400, 300);

        // Set the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Booking Application");
        primaryStage.show();
    }

    // Method to create the booking scene
    private Scene createBookingScene(Stage primaryStage) {
        // Create labels and text fields for booking information
        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();
        Label guestsLabel = new Label("Number of Guests:");
        TextField guestsField = new TextField();
        Label durationLabel = new Label("Duration:");
        TextField durationField = new TextField();
        Label specialInstructionsLabel = new Label("Special Instructions:");
        TextField specialInstructionsField = new TextField();

        // Create a button to add the booking
        Button addButton = new Button("Add Booking");
        addButton.setOnAction(event -> {
            // Get booking information from the form
            String name = nameField.getText();
            int guests = Integer.parseInt(guestsField.getText());
            String duration = durationField.getText();
            String specialInstructions = specialInstructionsField.getText();

            // Save booking data to the database
            saveBookingToDatabase(name, guests, duration, specialInstructions);

            // Show a confirmation dialog
            showAlert("Booking Confirmation", "Your booking has been submitted successfully!");

            // Clear fields
            nameField.clear();
            guestsField.clear();
            durationField.clear();
            specialInstructionsField.clear();
        });

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            // Switch back to the main scene
            primaryStage.setScene(createMainScene(primaryStage));
        });

        // Create the booking layout
        VBox bookingLayout = new VBox(20);
        bookingLayout.setAlignment(Pos.CENTER);
        bookingLayout.getChildren().addAll(
                nameLabel, nameField,
                guestsLabel, guestsField,
                durationLabel, durationField,
                specialInstructionsLabel, specialInstructionsField,
                addButton, backButton
        );

        // Create the booking scene
        return new Scene(bookingLayout, 400, 300);
    }

    // Method to save booking data to the database
    private void saveBookingToDatabase(String name, int guests, String duration, String specialInstructions) {
        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create SQL query
            String sql = "INSERT INTO bookings (name, guests, duration, special_instructions) VALUES (?, ?, ?, ?)";

            // Create prepared statement
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, guests);
            statement.setString(3, duration);
            statement.setString(4, specialInstructions);

            // Execute the statement
            statement.executeUpdate();

            // Close connection and statement
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save booking data to the database.");
        }
    }

    // Method to create the main scene
    private Scene createMainScene(Stage primaryStage) {
        // Add your code here to create the main scene
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);

        Button makeBookingButton = new Button("Make Booking");
        Button viewBookingsButton = new Button("View Bookings");

        makeBookingButton.setOnAction(event -> {
            primaryStage.setScene(createBookingScene(primaryStage));
        });

        viewBookingsButton.setOnAction(event -> {
            showAlert("View Bookings", "Fetching bookings from the database...");
        });

        mainLayout.getChildren().addAll(makeBookingButton, viewBookingsButton);

        return new Scene(mainLayout, 400, 300);
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
>>>>>>> Stashed changes
