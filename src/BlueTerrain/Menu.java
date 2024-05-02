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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Menu class handles the display of menu items based on different categories.
 * It provides methods to show menu items in popup windows.
 * 
 * <p>This class also includes methods to set and get user profile information.</p>
 * 
 * @author Preetham
 */
public class Menu {

    /**
     * The SQL query to retrieve menu items based on the item type.
     */
    private static String MENU_QUERY = "SELECT ItemValue, ItemName FROM Menu WHERE ItemType = ?";
    static String firstName;
    static String lastName;
    static String profileType;
    static String loginType;

    /**
     * Sets the first name of the logged-in user.
     * 
     * @param firstName The first name of the user.
     */
    public static void setFirstName(String firstName) {
        Menu.firstName = firstName;
    }

    /**
     * Sets the last name of the logged-in user.
     * 
     * @param lastName The last name of the user.
     */
    public static void setLastName(String lastName) {
        Menu.lastName = lastName;
    }

    /**
     * Sets the profile type of the logged-in user.
     * 
     * @param profileType The profile type of the user.
     */
    public static void setProfileType(String profileType) {
        Menu.profileType = profileType;
    }

    /**
     * Sets the login type of the user.
     * 
     * @param loginType The login type of the user.
     */
    public static void setLoginType(String loginType) {
        Menu.profileType = loginType;
    }

    /**
     * Retrieves the first name of the logged-in user.
     * 
     * @return The first name of the user.
     */
    public static String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of the logged-in user.
     * 
     * @return The last name of the user.
     */
    public static String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the profile type of the logged-in user.
     * 
     * @return The profile type of the user.
     */
    public static String getProfileType() {
        return profileType;
    }

    /**
     * Retrieves the login type of the user.
     * 
     * @return The login type of the user.
     */
    public static String getLoginType() {
        return loginType;
    }

    /**
     * Displays the main menu with different food categories.
     * 
     * @param primaryStage The primary stage of the application.
     */
    public static void showMenu(Stage primaryStage) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button startersButton = Functions.createButtonMenu("STARTERS", Color.LAVENDER);
        Button fishMenuButton = Functions.createButtonMenu("FISH MENU", Color.LAVENDER);
        Button grillMeatButton = Functions.createButtonMenu("GRILL & MEAT", Color.LAVENDER);
        Button veganButton = Functions.createButtonMenu("VEGAN", Color.LAVENDER);
        Button pitButton = Functions.createButtonMenu("DRINKS", Color.LAVENDER);
        Button chefSpecialButton = Functions.createButtonMenu("CHEF SPECIAL", Color.LAVENDER);

        startersButton.setOnAction(e -> showMenuItemPopup("Starter"));
        fishMenuButton.setOnAction(e -> showMenuItemPopup("Fish_Menu"));
        grillMeatButton.setOnAction(e -> showMenuItemPopup("Grill_Meat"));
        veganButton.setOnAction(e -> showMenuItemPopup("Vegan"));
        pitButton.setOnAction(e -> showMenuItemPopup("Drinks"));
        chefSpecialButton.setOnAction(e -> showMenuItemPopup("Chef_Special"));

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

    /**
     * Displays a popup window for a specific category of menu items.
     * 
     * @param itemType The type of menu items to display (e.g., Starter, Fish_Menu).
     */
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

        tableView.getColumns().addAll(slNoColumn, itemNameColumn, itemPriceColumn);

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

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}