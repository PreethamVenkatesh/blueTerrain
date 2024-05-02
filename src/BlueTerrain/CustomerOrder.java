package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerOrder {

    @SuppressWarnings("unused")
    private static final Node cartTableView = null;
    private static String MENU_QUERY = "SELECT ItemValue, ItemName FROM Menu WHERE ItemType = ?";
    private static ObservableList<Item> selectedItems = FXCollections.observableArrayList();

    public static void showOrder(Stage primaryStage, String firstName, String lastName, String loginType, String profileType) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");
    
        Button startersButton = Functions.createButtonMenu("STARTERS", Color.LAVENDER);
        Button fishMenuButton = Functions.createButtonMenu("FISH MENU", Color.LAVENDER);
        Button grillMeatButton = Functions.createButtonMenu("GRILL & MEAT", Color.LAVENDER);
        Button veganButton = Functions.createButtonMenu("VEGAN", Color.LAVENDER);
        Button pitButton = Functions.createButtonMenu("DRINKS", Color.LAVENDER);
        Button chefSpecialButton = Functions.createButtonMenu("CHEF SPECIAL", Color.LAVENDER);
    
        startersButton.setOnAction(e -> showMenuItemPopup("Starter", firstName, lastName));
        fishMenuButton.setOnAction(e -> showMenuItemPopup("Fish_Menu", firstName, lastName));
        grillMeatButton.setOnAction(e -> showMenuItemPopup("Grill_Meat", firstName, lastName));
        veganButton.setOnAction(e -> showMenuItemPopup("Vegan", firstName, lastName));
        pitButton.setOnAction(e -> showMenuItemPopup("Drinks", firstName, lastName));
        chefSpecialButton.setOnAction(e -> showMenuItemPopup("Chef_Special", firstName, lastName));
    
        VBox leftBox = Functions.createButtonVBoxMenu(startersButton, fishMenuButton);
        VBox centreBox = Functions.createButtonVBoxMenu(grillMeatButton, pitButton);
        VBox rightBox = Functions.createButtonVBoxMenu(veganButton, chefSpecialButton);
    
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);
    
        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            
            if (loginType.equals("Customer")) {
                Bookings bookings = new Bookings();
                bookings.start(primaryStage, firstName, lastName, loginType, profileType);
            } else {
                Restaurant restaurant = new Restaurant();
                restaurant.start(primaryStage, firstName, lastName, profileType, loginType);
            }
        });
    
        Button viewCartButton = new Button("View My Cart");
        viewCartButton.setOnAction(e -> viewCart(firstName, lastName)); // Pass firstName and lastName to viewCart
    
        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, viewCartButton, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }
    

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showMenuItemPopup(String itemType, String firstName, String lastName) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(itemType + " Menu");

        TableView<Item> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Item, Integer> slNoColumn = new TableColumn<>("SL. NO.");
        slNoColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        slNoColumn.setCellValueFactory(new PropertyValueFactory<>("slNo"));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, Double> itemPriceColumn = new TableColumn<>("Item Price (£)");
        itemPriceColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, Void> selectColumn = new TableColumn<>("Selection");
        selectColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        selectColumn.setCellFactory(tc -> new ButtonCell());
        tableView.getColumns().addAll(slNoColumn, itemNameColumn, itemPriceColumn, selectColumn);

        ObservableList<Item> itemList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(MENU_QUERY)) {
            preparedStatement.setString(1, itemType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int slNo = 1;
                while (resultSet.next()) {
                    String itemName = resultSet.getString("ItemName");
                    double itemPrice = resultSet.getDouble("ItemValue");
                    itemList.add(new Item(slNo++, itemName, itemPrice, false));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch menu items - " + ex.getMessage());
        }

        tableView.setItems(itemList);

        Button addOrderButton = new Button("Add to Order Cart");
        addOrderButton.setOnAction(e -> {
            addToCart(itemList);
            popupStage.close(); // Close the popupStage
        });

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, addOrderButton);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private static void addToCart(ObservableList<Item> itemList) {
        System.out.println("Adding selected items to cart...");
        for (Item item : itemList) {
            if (item.isSelected() && !selectedItems.contains(item)) {  // Avoid duplicate entries
                selectedItems.add(item);
                System.out.println("Added: " + item.getItemName());
            }
        }
    }
    
    @SuppressWarnings({"deprecation", "unchecked" })
    private static void viewCart(String firstName, String lastName) {
        Stage cartStage = new Stage();
        cartStage.initModality(Modality.APPLICATION_MODAL);
        cartStage.setTitle("My Cart");
        
        TableView<Item> cartTableView = new TableView<>();
        cartTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Item, Integer> slNoColumn = new TableColumn<>("SL. NO.");
        slNoColumn.setCellValueFactory(new PropertyValueFactory<>("slNo"));
        
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        TableColumn<Item, Double> itemPriceColumn = new TableColumn<>("Item Price (£)");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    
        cartTableView.getColumns().addAll(slNoColumn, itemNameColumn, itemPriceColumn);
        
        cartTableView.setItems(selectedItems);  // Bind the selectedItems to the TableView
        
        Button confirmOrderButton = new Button("Confirm Order");
        confirmOrderButton.setOnAction(e -> {
            try {
                confirmOrder(selectedItems, firstName, lastName, cartStage);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        
        VBox cartRoot = new VBox(10);
        cartRoot.setAlignment(Pos.CENTER);
        cartRoot.setPadding(new Insets(20));
        cartRoot.getChildren().addAll(cartTableView, confirmOrderButton);
    
        Scene cartScene = new Scene(cartRoot, 700, 500);
        cartStage.setScene(cartScene);
        cartStage.showAndWait();
    }
    
    private static class ButtonCell extends TableCell<Item, Void> {
        private final Button selectButton = new Button("Select");
    
        private ButtonCell() {
            selectButton.setOnAction(event -> {
                Item selectedItem = getTableView().getItems().get(getIndex());
                selectedItem.setSelected(!selectedItem.isSelected()); 
    
                if (selectedItem.isSelected()) {
                    selectButton.setText("Remove");
                    selectButton.setStyle("-fx-background-color: green;");
                } else {
                    selectButton.setText("Select");
                    selectButton.setStyle("-fx-background-color: red;");
                }
    
                System.out.println("Item " + (selectedItem.isSelected() ? "selected: " : "removed: ") + selectedItem.getItemName());
            });
        }
    
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null); 
            } else {
                setGraphic(selectButton); 
            }
        }
    }
    
    private static void confirmOrder(ObservableList<Item> itemList, String firstName, String lastName, Stage cartStage) throws SQLException {
    
        int customerId = getCustomerId(firstName, lastName);
        String queryInsertOrder = "INSERT INTO orders (customer_id, itemName, itemPrice, orderStatus) VALUES (?, ?, ?, ?)";
        String queryGetOrderId = "SELECT LAST_INSERT_ID()";
        int orderId;

        try (Connection connection = Functions.getConnection();
            PreparedStatement preparedStatementInsertOrder = connection.prepareStatement(queryInsertOrder);
            PreparedStatement preparedStatementGetOrderId = connection.prepareStatement(queryGetOrderId)) {
            
            preparedStatementInsertOrder.setInt(1, customerId);
            preparedStatementInsertOrder.setString(4, "Pending"); 
            
            for (Item item : itemList) {
                preparedStatementInsertOrder.setString(2, item.getItemName());
                preparedStatementInsertOrder.setDouble(3, item.getItemPrice());
                preparedStatementInsertOrder.executeUpdate();
            }

            // Retrieve the generated order ID
            try (ResultSet resultSet = preparedStatementGetOrderId.executeQuery()) {
                if (resultSet.next()) {
                    orderId = resultSet.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve the order ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to insert order - " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Show order confirmation
        Stage confirmationStage = new Stage();
        confirmationStage.initModality(Modality.APPLICATION_MODAL);
        confirmationStage.setTitle("Order Confirmation");

        VBox confirmationRoot = new VBox(20);
        confirmationRoot.setAlignment(Pos.CENTER);
        confirmationRoot.setPadding(new Insets(20));
        Button closeConfirmationButton = new Button("Close Confirmation");
        closeConfirmationButton.setOnAction(event -> {
            confirmationStage.close();  // Close the confirmation stage
            cartStage.close();          // Close the cart stage
        });

        confirmationRoot.getChildren().addAll(new Label("Order created successfully with Order ID: " + orderId), closeConfirmationButton);
        confirmationRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene confirmationScene = new Scene(confirmationRoot, 400, 200);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.showAndWait();
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
}
