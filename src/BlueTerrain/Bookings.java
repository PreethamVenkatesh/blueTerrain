package BlueTerrain;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

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
            displayBookings(primaryStage);
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
            // Fetch and display bookings from the database
            displayBookings(primaryStage);
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

    // Method to fetch and display bookings from the database
    private void displayBookings(Stage primaryStage) {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM bookings";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            VBox layout = new VBox(10);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int guests = resultSet.getInt("guests");
                String duration = resultSet.getString("duration");
                String specialInstructions = resultSet.getString("special_instructions");

                Label bookingLabel = new Label("Booking ID: " + id + ", Name: " + name + ", Guests: " + guests + ", Duration: " + duration + ", Special Instructions: " + specialInstructions);
                layout.getChildren().add(bookingLabel);
            }

            Scene scene = new Scene(layout, 600, 400);
            primaryStage.setScene(scene);

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch bookings from the database.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
<<<<<<< Updated upstream
=======

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showBookingPopup(String firstName, String lastName) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("My Bookings");
    
        TableView<Reservation> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Reservation, Integer> slNoColumn = new TableColumn<>("Sl. No.");
        slNoColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-alignment: CENTER;");
        slNoColumn.setCellValueFactory(new PropertyValueFactory<>("slNo"));
    
        TableColumn<Reservation, String> bookingDateColumn = new TableColumn<>("Date");
        bookingDateColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        bookingDateColumn.setComparator(Comparator.comparing(String::toString));

        TableColumn<Reservation, String> bookingTimeColumn = new TableColumn<>("Time");
        bookingTimeColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        bookingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));

        TableColumn<Reservation, Integer> tableTypeColumn = new TableColumn<>("Table Type");
        tableTypeColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-alignment: CENTER;");
        tableTypeColumn.setCellValueFactory(new PropertyValueFactory<>("tableType"));

        TableColumn<Reservation, Boolean> bookingStatusColumn = new TableColumn<>("Status");
        bookingStatusColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        bookingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
    
        tableView.getColumns().addAll(slNoColumn, bookingDateColumn, bookingTimeColumn, tableTypeColumn, bookingStatusColumn);
    
        ObservableList<Reservation> bookingList = FXCollections.observableArrayList();

        int customerId = getCustomerId(firstName, lastName); 
    
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_QUERY)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int slNo = 1;
                while (resultSet.next()) {
                    String bookingDate = resultSet.getString("date");
                    String bookingTime = resultSet.getString("time");
                    int tableType = resultSet.getInt("tableType");
                    Boolean status = resultSet.getBoolean("isApproved");
                    String bookingStatus = status ? "Booked" : "Pending";
                    bookingList.add(new Reservation(slNo++, bookingDate, bookingTime, tableType, bookingStatus));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch staff profiles - " + ex.getMessage());
        }
        
        bookingList.sort(Comparator.comparing(Reservation::getBookingDate));
        tableView.setItems(bookingList);
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        Scene popupScene = new Scene(popupRoot, 400, 600);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
    
>>>>>>> Stashed changes
}