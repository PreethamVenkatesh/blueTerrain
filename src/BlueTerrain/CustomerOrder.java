package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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

/**
 * The CustomerOrder class manages the customer's order functionality,
 * including displaying menu, selecting items, viewing the cart, and confirming orders
 * @author Aravind Sivakumar, Clinton Ekhameye
 */
public class CustomerOrder {

    @SuppressWarnings("unused")
    private static final Node cartTableView = null;
    private static String MENU_QUERY = "SELECT ItemValue, ItemName FROM Menu WHERE ItemType = ?";
    private static ObservableList<Item> selectedItems = FXCollections.observableArrayList();

    /**
     * Displays the customer's order interface
     * 
     * @param primaryStage The primary stage of the JavaFX application
     * @param firstName    The first name of the customer
     * @param lastName     The last name of the customer
     * @param loginType    The type of login (e.g., customer, staff)
     * @param profileType  The type of profile (e.g., manager,waiter,chef,delivery driver)
     */
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
   
     /**
     * Displays a popup window showing the menu items for a specific itemType
     * 
     * @param itemType   The type of menu items to display
     * @param firstName  The first name of the customer
     * @param lastName   The last name of the customer
     */
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

     /**
     * Displays a popup window showing the menu items for a specific item type
     * 
     * @param itemType   The type of menu items to display
     * @param firstName  The first name of the customer
     * @param lastName   The last name of the customer
     */
    private static void addToCart(ObservableList<Item> itemList) {
        System.out.println("Adding selected items to cart...");
        for (Item item : itemList) {
            if (item.isSelected() && !selectedItems.contains(item)) { 
                selectedItems.add(item);
                System.out.println("Added: " + item.getItemName());
            }
        }
    }
    
    /**
     * Displays the customer's cart with selected items
     * 
     * @param firstName The first name of the customer
     * @param lastName  The last name of the customer
     */
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
    
     /**
     * TableCell implementation for the "Select" button in the menu item table
     */
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
    
    /**
     * Confirms the customer's order and updates the database
     * 
     * @param itemList   The list of items in the customer's cart
     * @param firstName  The first name of the customer
     * @param lastName   The last name of the customer
     * @param cartStage  The stage displaying the cart
     * @throws SQLException if there is an error with database operations
     */
    private static void confirmOrder(ObservableList<Item> itemList, String firstName, String lastName, Stage cartStage) throws SQLException {
        int customerId = getCustomerId(firstName, lastName);
        String queryInsertOrder = "INSERT INTO orders (customer_id, OrderNumber, itemName, itemPrice, orderStatus) VALUES (?, ?, ?, ?, ?)";
    
        int orderNumber = generateUniqueOrderNumber();
    
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatementInsertOrder = connection.prepareStatement(queryInsertOrder)) {
    
            for (Item item : itemList) {
                preparedStatementInsertOrder.setInt(1, customerId);
                preparedStatementInsertOrder.setInt(2, orderNumber);
                preparedStatementInsertOrder.setString(3, item.getItemName());
                preparedStatementInsertOrder.setDouble(4, item.getItemPrice());
                preparedStatementInsertOrder.setString(5, "Pending");
                preparedStatementInsertOrder.executeUpdate();
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
    
        confirmationRoot.getChildren().addAll(new Label("Order created successfully with Order Number: " + orderNumber), closeConfirmationButton);
        confirmationRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    
        Scene confirmationScene = new Scene(confirmationRoot, 400, 200);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.showAndWait();
    }
     
    /**
     * Generates a unique order number based on timestamp
     * 
     * @return The generated unique order number
     */
    private static int generateUniqueOrderNumber() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; 
        int orderNumber = (int) ((timestamp / 1000) % 1000000) * 10000 + randomNumber;
        orderNumber = Math.abs(orderNumber);
        return orderNumber;
    }

    /**
     * Retrieves the customer ID from the database based on first name and last name
     * 
     * @param firstName  The first name of the customer
     * @param lastName   The last name of the customer
     * @return The customer ID
     */
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
