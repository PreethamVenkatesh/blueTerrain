package BlueTerrain;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Bookings extends Application {

    // Data model for bookings
    private ObservableList<Booking> bookingsData = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
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

            // Clear input fields
            tableNumberField.clear();
            guestsField.clear();
            durationField.clear();
        });

        TableColumn<Booking, String> bookingIDCol = new TableColumn<>("Booking ID");
        bookingIDCol.setCellValueFactory(cellData -> cellData.getValue().bookingIDProperty());

        TableColumn<Booking, String> tableNumberCol = new TableColumn<>("Table Number");
        tableNumberCol.setCellValueFactory(cellData -> cellData.getValue().tableNumberProperty());

       //TableColumn<Booking, Integer> guestsCol = new TableColumn<>("Guests");
        //guestsCol.setCellValueFactory(cellData -> cellData.getValue().guestsProperty().asString());

        TableColumn<Booking, String> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

        // Create a TableView for displaying bookings
        TableView<Booking> bookingsTable = new TableView<>();
        bookingsTable.setItems(bookingsData);
        bookingsTable.getColumns().addAll(bookingIDCol, tableNumberCol,  durationCol);

        // Create a layout to hold the input fields and button
        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(tableNumberLabel, tableNumberField, guestsLabel, guestsField,
                durationLabel, durationField, addButton);

        // Create a layout to hold the input layout and bookings table
        HBox root = new HBox(10);
        root.getChildren().addAll(inputLayout, bookingsTable);

        // Create the scene with the layout
        Scene scene = new Scene(root, 800, 400);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle("Table Booking Screen");

        // Show the stage
        primaryStage.show();
    }

    // Method to generate a unique booking ID
    private String generateBookingID() {
        // Implement your own logic to generate a unique ID using UUID
        return java.util.UUID.randomUUID().toString();
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
        
        
        public int getGuests() {
            return guests;
        }

        public String getDuration() {
            return duration;
        }

        // Properties for JavaFX binding
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
