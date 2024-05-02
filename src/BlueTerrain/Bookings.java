package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Bookings {

    private static String BOOKING_QUERY = "SELECT * FROM bookings WHERE customerId = ?";

    public void start(Stage primaryStage, String firstName, String lastName, String loginType, String profileType) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Bookings.jpeg");
        Label openingHoursLabel = Functions.openingHours();

        StackPane lightBluePadding = new StackPane();
        lightBluePadding.setPadding(new Insets(20));
        
        Label welcomeLabel = new Label("Welcome to BLUE TERRAIN RESTAURANT");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-background-color: #2E5CB8; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
        
        lightBluePadding.getChildren().add(welcomeLabel);

        Label userDetailsLabel = new Label("HEY! " + firstName + " " + lastName + " Our Place or Yours ?");
        userDetailsLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 20; -fx-background-color: #2E5CB8; -fx-padding: 5px 10px; -fx-border-radius: 5px;");

        Functions.setMarginForNode(root, userDetailsLabel, new Insets(20, 20, 20, 0));
        
        VBox leftBox = Functions.createButtonVBox(Color.LAVENDER, "BOOK TABLE");
        VBox centreBox = Functions.createButtonVBox(Color.LAVENDER, "TAKE AWAY");

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox);

        Hyperlink myBookings = new Hyperlink("My Bookings");
        myBookings.setStyle("-fx-underline: true; -fx-text-fill: white; -fx-font-size: 20; -fx-background-color: #2E5CB8; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
        myBookings.setOnAction(e -> showBookingPopup(firstName, lastName));

        Hyperlink myOrders = new Hyperlink("My Orders");
        myOrders.setStyle("-fx-underline: true; -fx-text-fill: white; -fx-font-size: 20; -fx-background-color: #2E5CB8; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
        myOrders.setOnAction(e -> showOrderPopup(firstName, lastName));

        HBox hyperlinkBox = new HBox();
        hyperlinkBox.getChildren().addAll(myBookings, myOrders);
        hyperlinkBox.setAlignment(Pos.CENTER_RIGHT);
        
        root.getChildren().addAll(welcomeLabel, openingHoursLabel, userDetailsLabel, buttonsBox, hyperlinkBox, Functions.logOut(primaryStage));
        Functions.setupAndShowScene(primaryStage, root);

        Button bookTableButton = (Button) leftBox.getChildren().get(0); 
        bookTableButton.setOnAction(e -> bookTablePopup(firstName, lastName));

        Button orderNowButton = (Button) centreBox.getChildren().get(0);
        orderNowButton.setOnAction(e -> CustomerOrder.showOrder(primaryStage, firstName, lastName, loginType, profileType));

    }

    private void bookTablePopup(String firstName, String lastName) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Book Table");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setStyle("-fx-background-color: lightblue;");
    
        Label tableTypeLabel = new Label("Number of Guests:");
        tableTypeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-alignment: CENTER;");
        ComboBox<String> tableTypeComboBox = new ComboBox<>();
        tableTypeComboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    
        Label dateLabel = new Label("Date:");
        dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-alignment: CENTER;");
        TextField dateTextField = new TextField();
        dateTextField.setPromptText("YYYY-MM-DD");
    
        Label timeLabel = new Label("Time:");
        timeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-alignment: CENTER;");
        ComboBox<String> timeComboBox = new ComboBox<>();
        for (int hour = 11; hour <= 22; hour++) {
            timeComboBox.getItems().add(String.format("%02d:00", hour)); 
        }
    
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-font-size: 16;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 16;");
    
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(confirmButton, cancelButton);
    
        gridPane.add(tableTypeLabel, 0, 0);
        gridPane.add(tableTypeComboBox, 1, 0);
        gridPane.add(dateLabel, 0, 1);
        gridPane.add(dateTextField, 1, 1);
        gridPane.add(timeLabel, 0, 2);
        gridPane.add(timeComboBox, 1, 2);
        gridPane.add(buttonBox, 0, 3, 2, 1);
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.TOP_CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(gridPane); 
    
        confirmButton.setOnAction(e -> {
            int customerId = getCustomerId(firstName, lastName); 
            String tableType = tableTypeComboBox.getValue();
            String date = dateTextField.getText();
            String time = timeComboBox.getValue();
           
            insertBooking(customerId, tableType, date, time);
        
            popupStage.close();
            showBookingSuccessMessage();
        });

        cancelButton.setOnAction(e -> {
            popupStage.close();
        });
    
        Scene popupScene = new Scene(popupRoot, 400, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void insertBooking(int customerId, String tableType, String date, String time) {
    
        String query = "INSERT INTO bookings (customerId, tableType, date, time, isApproved) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, tableType); 
            preparedStatement.setString(3, date); 
            preparedStatement.setString(4, time);
            preparedStatement.setBoolean(5, false);
             
            
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Booking inserted successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to insert booking");
        }
    }

    private static int getCustomerId(String firstName, String lastName) {
        int customerId = 0; 
                
        String query = "SELECT customer_id FROM customers WHERE first_name = ? AND last_name = ?";
        
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customerId = resultSet.getInt("customer_id");
                } else {
                    System.out.println("customerId not received");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to retrieve customer ID");
        }
        
        return customerId;
    }

    private void showBookingSuccessMessage() {
        Stage messageStage = new Stage();
        messageStage.initModality(Modality.APPLICATION_MODAL);
        messageStage.setTitle("Success");
    
        Label messageLabel = new Label("Booking created successfully, View your bookings in My Bookings");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> messageStage.close());
    
        VBox messageRoot = new VBox(20);
        messageRoot.setAlignment(Pos.CENTER);
        messageRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        messageRoot.getChildren().addAll(messageLabel, closeButton);
        Scene messageScene = new Scene(messageRoot, 600, 100);
    
        messageStage.setScene(messageScene);
        messageStage.show();
    }

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

        TableColumn<Reservation, Integer> tableTypeColumn = new TableColumn<>("Guests");
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

    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void showOrderPopup(String firstName, String lastName) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("My Orders");
    
        TableView<Order> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order No.");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");

        TableColumn<Order, String> orderStatusColumn = new TableColumn<>("Order Status");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderStatusColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
    
        TableColumn<Order, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));
        actionColumn.setStyle("-fx-alignment: CENTER;");
        actionColumn.setCellFactory(param -> new TableCell<Order, Void>() {
            private final Button button = new Button("View");
    
            {
                button.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    showOrderItemsPopup(order);
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        
        tableView.getColumns().addAll(orderIdColumn, orderStatusColumn, actionColumn);
    
        ObservableList<Order> orders = FXCollections.observableArrayList();
        int customerId = getCustomerId(firstName, lastName);
    
        String query = "SELECT orderNumber, orderStatus, itemName, itemPrice FROM orders WHERE customer_id = ?";
        try (Connection connection = Functions.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, customerId); // Set the customer ID parameter
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            Set<Integer> uniqueOrderNumbers = new HashSet<>();
            while (resultSet.next()) {
                int orderNumber = resultSet.getInt("orderNumber");
                if (!uniqueOrderNumbers.contains(orderNumber)) {
                    uniqueOrderNumbers.add(orderNumber);
                    orders.add(new Order(
                            orderNumber,
                            resultSet.getString("orderStatus"),
                            resultSet.getString("itemName"),
                            resultSet.getDouble("itemPrice")
                    ));
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.err.println("Error: Failed to fetch order details - " + ex.getMessage());
    }
    
        tableView.setItems(orders);
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        Scene popupScene = new Scene(popupRoot, 800, 600);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
    
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showOrderItemsPopup(Order order) {
        // Create a new stage for showing order items
        Stage itemsPopupStage = new Stage();
        itemsPopupStage.initModality(Modality.APPLICATION_MODAL);
        itemsPopupStage.setTitle("Order Items");
    
        // Create TableView for displaying order items
        TableView<Item> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    
        TableColumn<Item, Double> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    
        // Add columns to TableView
        tableView.getColumns().addAll(itemNameColumn, itemPriceColumn);
    
        // Populate TableView with order items
        ObservableList<Item> orderItems = FXCollections.observableArrayList();
    
        String query = "SELECT itemName, itemPrice FROM orders WHERE orderNumber = ?";
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getOrderId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderItems.add(new Item(
                        0, resultSet.getString("itemName"),
                        resultSet.getDouble("itemPrice"), false
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch order item details - " + ex.getMessage());
        }
    
        tableView.setItems(orderItems);
    
        // Create VBox to hold TableView
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        // Create Scene
        Scene popupScene = new Scene(popupRoot, 400, 300);
    
        // Set Scene and show stage
        itemsPopupStage.setScene(popupScene);
        itemsPopupStage.showAndWait();
    }
    
    
    
}
