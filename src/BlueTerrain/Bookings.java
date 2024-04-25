package BlueTerrain;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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