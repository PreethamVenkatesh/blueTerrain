package BlueTerrain;

import javafx.scene.control.TableCell;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Menu {

    private static String MENU_QUERY = "SELECT ItemValue, ItemName FROM Menu WHERE ItemType = ?";
    private static String firstName;
    private static String lastName;
    private static String profileType;

    public static void setFirstName(String firstName) {
        Menu.firstName = firstName;
    }

    public static void setLastName(String lastName) {
        Menu.lastName = lastName;
    }

    public static void setProfileType(String profileType) {
        Menu.profileType = profileType;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getProfileType() {
        return profileType;
    }

    public static void showMenu(Stage primaryStage) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button startersButton = Functions.createButtonMenu("STARTERS", Color.LAVENDER);
        Button fishMenuButton = Functions.createButtonMenu("FISH MENU", Color.LAVENDER);
        Button grillMeatButton = Functions.createButtonMenu("GRILL & MEAT", Color.LAVENDER);
        Button veganButton = Functions.createButtonMenu("VEGAN", Color.LAVENDER);
        Button pitButton = Functions.createButtonMenu("MEAT MAINS", Color.LAVENDER);
        Button chefSpecialButton = Functions.createButtonMenu("CHEF'S SPECIAL", Color.LAVENDER);

        startersButton.setOnAction(e -> showMenuItemPopup("Starter"));
        fishMenuButton.setOnAction(e -> showMenuItemPopup("Fish_Menu"));
        grillMeatButton.setOnAction(e -> showMenuItemPopup("Grill_Meat"));
        veganButton.setOnAction(e -> showMenuItemPopup("Vegan"));
        pitButton.setOnAction(e -> showMenuItemPopup("Meat Main"));
        chefSpecialButton.setOnAction(e -> showMenuItemPopup("Chef special"));

        VBox leftBox = Functions.createButtonVBoxMenu(startersButton, fishMenuButton);
        VBox centreBox = Functions.createButtonVBoxMenu(grillMeatButton, pitButton);
        VBox rightBox = Functions.createButtonVBoxMenu(veganButton, chefSpecialButton);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showMenuItemPopup(String itemType) {
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

        TableColumn<Item, Void> selectColumn = new TableColumn<>("Select/Deselect");
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

        // View My Cart Button
        Button viewCartButton = new Button("View My Cart");
        viewCartButton.setOnAction(e -> viewCart(itemList));

        // Confirm Order Button
        Button confirmOrderButton = new Button("Confirm Order");
        confirmOrderButton.setOnAction(e -> confirmOrder(itemList));

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(viewCartButton, confirmOrderButton);

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, buttonBox);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void viewCart(ObservableList<Item> itemList) {
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

        cartTableView.setItems(itemList);

        VBox cartRoot = new VBox(10);
        cartRoot.setAlignment(Pos.CENTER);
        cartRoot.setPadding(new Insets(20));
        cartRoot.getChildren().addAll(cartTableView);

        Scene cartScene = new Scene(cartRoot, 400, 300);
        cartStage.setScene(cartScene);
        cartStage.showAndWait();
    }

    private static class ButtonCell extends TableCell<Item, Void> {
        private final Button selectButton = new Button("Select/Deselect");
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













    // Method to confirm the order
    private static void confirmOrder(ObservableList<Item> itemList) {
        // Retrieve customerId based on logged-in user
        int customerId = getCustomerId(getFirstName(), getLastName());
        System.out.println("Customer ID: " + customerId); // Log the retrieved customerId

        try (Connection connection = Functions.getConnection()) {
            String query = "INSERT INTO test_order (itemName, itemPrice, customerId) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Item item : itemList) {
                    preparedStatement.setString(1, item.getItemName());
                    preparedStatement.setDouble(2, item.getItemPrice());
                    preparedStatement.setInt(3, customerId); // Set customerId
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to insert order - " + ex.getMessage());
        }

        // Show confirmation message
        Stage confirmationStage = new Stage();
        confirmationStage.initModality(Modality.APPLICATION_MODAL);
        confirmationStage.setTitle("Order Confirmation");

        VBox confirmationRoot = new VBox(20);
        confirmationRoot.setAlignment(Pos.CENTER);
        confirmationRoot.setPadding(new Insets(20));
        confirmationRoot.getChildren().addAll(new Label("Order confirmed successfully!"));

        Scene confirmationScene = new Scene(confirmationRoot, 300, 100);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.showAndWait();
    }

    // Method to get the customerId based on firstName and lastName
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