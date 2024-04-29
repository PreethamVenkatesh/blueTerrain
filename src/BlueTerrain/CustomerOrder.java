package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private static String MENU_QUERY = "SELECT ItemValue, ItemName FROM Menu WHERE ItemType = ?";
    private static ObservableList<Item> selectedItems = FXCollections.observableArrayList();

    public static void showOrder(Stage primaryStage, String firstName, String lastName) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");
    
        Button startersButton = Functions.createButtonMenu("STARTERS", Color.LAVENDER);
        Button fishMenuButton = Functions.createButtonMenu("FISH MENU", Color.LAVENDER);
        Button grillMeatButton = Functions.createButtonMenu("GRILL & MEAT", Color.LAVENDER);
        Button veganButton = Functions.createButtonMenu("VEGAN", Color.LAVENDER);
        Button pitButton = Functions.createButtonMenu("MEAT MAINS", Color.LAVENDER);
        Button chefSpecialButton = Functions.createButtonMenu("CHEF'S SPECIAL", Color.LAVENDER);
    
        startersButton.setOnAction(e -> showMenuItemPopup("Starter", firstName, lastName));
        fishMenuButton.setOnAction(e -> showMenuItemPopup("Fish_Menu", firstName, lastName));
        grillMeatButton.setOnAction(e -> showMenuItemPopup("Grill_Meat", firstName, lastName));
        veganButton.setOnAction(e -> showMenuItemPopup("Vegan", firstName, lastName));
        pitButton.setOnAction(e -> showMenuItemPopup("Meat Main", firstName, lastName));
        chefSpecialButton.setOnAction(e -> showMenuItemPopup("Chef special", firstName, lastName));
    
        VBox leftBox = Functions.createButtonVBoxMenu(startersButton, fishMenuButton);
        VBox centreBox = Functions.createButtonVBoxMenu(grillMeatButton, pitButton);
        VBox rightBox = Functions.createButtonVBoxMenu(veganButton, chefSpecialButton);
    
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);
    
        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            Bookings bookings = new Bookings();
            bookings.start(primaryStage, firstName, lastName);
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
        for (Item item : itemList) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void viewCart(String firstName, String lastName) {
        Stage cartStage = new Stage();
        cartStage.initModality(Modality.APPLICATION_MODAL);
        cartStage.setTitle("My Cart");
    
        TableView<Item> cartTableView = new TableView<>();
        cartTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Item, Integer> slNoColumn = new TableColumn<>("SL. NO.");
        slNoColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        slNoColumn.setCellValueFactory(new PropertyValueFactory<>("slNo"));
    
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    
        TableColumn<Item, Double> itemPriceColumn = new TableColumn<>("Item Price (£)");
        itemPriceColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    
        cartTableView.getColumns().addAll(slNoColumn, itemNameColumn, itemPriceColumn);
    
        cartTableView.setItems(selectedItems);
    
        Button confirmOrderButton = new Button("Confirm Order");
        confirmOrderButton.setOnAction(e -> confirmOrder(selectedItems, firstName, lastName));
    
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
        private boolean clicked = false;

        private ButtonCell() {
            selectButton.setOnAction(event -> {
                if (!clicked) {
                    Item selectedItem = getTableView().getItems().get(getIndex());
                    selectedItem.setSelected(!selectedItem.isSelected());
                    System.out.println("Item selected: " + selectedItem.getItemName());
                    clicked = true;
                    setStyle("-fx-background-color: green;");
                    setGraphic(null);
                    setText(selectedItem.isSelected() ? "Selected" : "Deselected");
                    setTextFill(Color.WHITE);
                    setAlignment(Pos.CENTER);
                }
            });
            setGraphic(selectButton);
        }
    }

    private static void confirmOrder(ObservableList<Item> itemList, String firstName, String lastName) {
        int customerId = getCustomerId(firstName, lastName);
        System.out.println("Customer ID: " + customerId); 

        try (Connection connection = Functions.getConnection()) {
            String query = "INSERT INTO test_order (itemName, itemPrice, customerId) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Item item : itemList) {
                    preparedStatement.setString(1, item.getItemName());
                    preparedStatement.setDouble(2, item.getItemPrice());
                    preparedStatement.setInt(3, customerId); 
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to insert order - " + ex.getMessage());
        }

        Stage confirmationStage = new Stage();
        confirmationStage.initModality(Modality.APPLICATION_MODAL);
        confirmationStage.setTitle("Order Confirmation");

        VBox confirmationRoot = new VBox(20);
        confirmationRoot.setAlignment(Pos.CENTER);
        confirmationRoot.setPadding(new Insets(20));
        confirmationRoot.getChildren().addAll(new Label("Order created successfully, View your orders in My Orders"));
        confirmationRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene confirmationScene = new Scene(confirmationRoot, 400, 200);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.showAndWait();

    }

    private static int getCustomerId(String firstName, String lastName) {
        int customerId = 0;
        String query = "SELECT customerId FROM customers WHERE firstName = ? AND lastName = ?";
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customerId = resultSet.getInt("customer_Id");
                } else {
                    System.err.println("Error: Customer ID not found for " + firstName + " " + lastName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to retrieve customer ID - " + ex.getMessage());
        }
        return customerId;
    }
}