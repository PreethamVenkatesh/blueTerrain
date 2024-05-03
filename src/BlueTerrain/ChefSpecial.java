package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChefSpecial {
    public static void showChefSpecialPopup(Stage primaryStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        HBox loginTypeBox = new HBox(10);
        loginTypeBox.setAlignment(Pos.TOP_CENTER);
        HBox specialDishField = Functions.createLabeledField("\tChef Special", "Enter Chef Special");
        specialDishField.setAlignment(Pos.TOP_CENTER);

        HBox specialDishPrice = Functions.createLabeledField("\tItem Price (£)", "Enter Price of the Dish");
        specialDishPrice.setAlignment(Pos.TOP_CENTER);
        
        Button confirmButton = new Button("Confirm");

        HBox buttonBox = new HBox(confirmButton, Functions.closeButton(primaryStage));
        buttonBox.setAlignment(Pos.CENTER); // Align buttons at the center horizontally
        buttonBox.setSpacing(10);

        Functions.setMarginForNode(root, specialDishField, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, specialDishPrice, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, buttonBox, new Insets(10, 10, 10, 0));

        // Add action to the confirm button
        confirmButton.setOnAction(event -> {
            TextField specialDish = (TextField) specialDishField.getChildren().get(1);
            String specialDishText = specialDish.getText();

            TextField dishPrice = (TextField) specialDishPrice.getChildren().get(1);
            double specialItemPrice = Double.parseDouble(dishPrice.getText());

            insertIntoChefSpecial(specialDishText, specialItemPrice);

            // Display confirmation popup
            displayPopup("Chef Special Details confirmed");
        });

        // Add label, text field, and confirm button to the root VBox
        root.getChildren().addAll(Functions.welcomePane(), specialDishField, specialDishPrice, buttonBox);

        // Show the stage with the updated root
        Functions.setupAndShowScene(primaryStage, root);
    }

    private static void insertIntoChefSpecial(String itemName, double specialItemPrice) {
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Menu (ItemName, ItemValue, ItemType) VALUES (?, ?, 'Chef_Special');")) {
            // Set parameters for the prepared statement
            preparedStatement.setString(1, itemName);
            preparedStatement.setDouble(2, specialItemPrice);

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to insert data into ChefSpecial - " + ex.getMessage());
        }
    }

    public static void displayPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Customizing the appearance of the alert dialog
        VBox vbox = new VBox(new Label(message));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(400); // Set the preferred width
        vbox.setPrefHeight(200); // Set the preferred height
        alert.getDialogPane().setContent(vbox);
        
        // Show the popup
        alert.showAndWait();
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void chefOrderPopUp() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("My Orders");
    
        TableView<Cook> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Cook, Integer> orderIdColumn = new TableColumn<>("Order No.");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
    
        TableColumn<Cook, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));
        actionColumn.setStyle("-fx-alignment: CENTER;");
        actionColumn.setCellFactory(param -> new TableCell<Cook, Void>() {
            private final Button button = new Button("View");
    
            {
                button.setOnAction(event -> {
                    Cook cook = getTableView().getItems().get(getIndex());
                    showOrderItemsPopup(cook);
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

        TableColumn<Cook, Void> serveOrderColumn = new TableColumn<>("Serve Order");
        serveOrderColumn.setSortable(false);
        serveOrderColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));
        serveOrderColumn.setStyle("-fx-alignment: CENTER;");
        serveOrderColumn.setCellFactory(param -> new TableCell<Cook, Void>() {
            private final Button button = new Button("Serve");
            
            {
                button.setOnAction(event -> {
                    Cook cook = getTableView().getItems().get(getIndex());
                    serveOrder(cook);
                    getTableView().getItems().remove(cook);
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
        
        tableView.getColumns().addAll(orderIdColumn, actionColumn, serveOrderColumn);
    
        ObservableList<Cook> cooks = FXCollections.observableArrayList();
    
        String query = "SELECT orderNumber, itemName, itemPrice, orderType FROM orders WHERE orderStatus = 'Pending'";
        try (Connection connection = Functions.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            Set<Integer> uniqueOrderNumbers = new HashSet<>();
            while (resultSet.next()) {
                int orderNumber = resultSet.getInt("orderNumber");
                if (!uniqueOrderNumbers.contains(orderNumber)) {
                    uniqueOrderNumbers.add(orderNumber);
                    cooks.add(new Cook(
                            orderNumber,
                            resultSet.getString("itemName"),
                            resultSet.getDouble("itemPrice"),
                            resultSet.getString("orderType")
                    ));
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.err.println("Error: Failed to fetch order details - " + ex.getMessage());
    }
    
        tableView.setItems(cooks);
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        Scene popupScene = new Scene(popupRoot, 800, 600);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private static void serveOrder(Cook cook) {
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET orderStatus = 'Completed' WHERE orderNumber = ?")) {
            preparedStatement.setInt(1, cook.getOrderId());
            preparedStatement.executeUpdate();
            System.out.println("Order served successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to serve order - " + ex.getMessage());
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showOrderItemsPopup(Cook cook) {
        Stage itemsPopupStage = new Stage();
        itemsPopupStage.initModality(Modality.APPLICATION_MODAL);
        itemsPopupStage.setTitle("Order Items");
    
        TableView<Item> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    
        TableColumn<Item, Double> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    
        tableView.getColumns().addAll(itemNameColumn, itemPriceColumn);
    
        ObservableList<Item> orderItems = FXCollections.observableArrayList();
    
        String query = "SELECT itemName, itemPrice FROM orders WHERE orderNumber = ?";
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cook.getOrderId());
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
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        Scene popupScene = new Scene(popupRoot, 400, 300);
    
        itemsPopupStage.setScene(popupScene);
        itemsPopupStage.showAndWait();
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void deliveryPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Delivery Orders");
    
        TableView<Delivery> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Delivery, Integer> orderIdColumn = new TableColumn<>("Order No.");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
    
        TableColumn<Delivery, String> orderTypeColumn = new TableColumn<>("Order Type");
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        orderTypeColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");

        TableColumn<Delivery, Void> deliverOrderColumn = new TableColumn<>("Deliver Order");
        deliverOrderColumn.setSortable(false);
        deliverOrderColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));
        deliverOrderColumn.setStyle("-fx-alignment: CENTER;");
        deliverOrderColumn.setCellFactory(param -> new TableCell<Delivery, Void>() {
            private final Button button = new Button("Deliver");
            
            {
                button.setOnAction(event -> {
                    Delivery deliver = getTableView().getItems().get(getIndex());
                    deliveryOrder(deliver);
                    getTableView().getItems().remove(deliver);
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
    
        tableView.getColumns().addAll(orderIdColumn, orderTypeColumn, deliverOrderColumn);
    
        ObservableList<Delivery> deliveries = FXCollections.observableArrayList();
    
        String query = "SELECT orderNumber, orderType FROM orders WHERE orderType = 'Delivery'";
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int orderNumber = resultSet.getInt("orderNumber");
                String orderType = resultSet.getString("orderType");
                deliveries.add(new Delivery(orderNumber, orderType));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch order details - " + ex.getMessage());
        }
    
        tableView.setItems(deliveries);
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        Scene popupScene = new Scene(popupRoot, 800, 600);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    } 
    
    private static void deliveryOrder(Delivery delivery) {
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET orderStatus = 'Delivered' WHERE orderNumber = ?")) {
            preparedStatement.setInt(1, delivery.getOrderId());
            preparedStatement.executeUpdate();
            System.out.println("Order delivered successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to serve order - " + ex.getMessage());
        }
    }
}
