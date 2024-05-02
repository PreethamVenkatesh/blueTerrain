package BlueTerrain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * The Functions class contains utility functions used throughout the BlueTerrain application
 * @author Preetham Venkatesh
 */
public class Functions {

    private static String OPENING_HOURS = "Opening Hours: 11:00 AM - 11:00 PM";
    private static String BLUE_TERRAIN = "BLUE TERRAIN RESTAURANT";

     /**
     * Establishes a connection to the MySQL database
     * 
     * @return a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String JDBC_URL = "jdbc:mysql://localhost:3306/BlueTerrain_Restaurant";
            String USERNAME = "root";
            String PASSWORD = "410707";

            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Creates a background image with the specified image path
     * 
     * @param imagePath The path to the image file
     * @return The background image
     */
    public static Background backGroundImage(String imagePath) {      
        Image backgroundImage = new Image(imagePath);
        BackgroundSize backgroundSize = new BackgroundSize(1000, 800, false, false, true, true);
        BackgroundImage backgroundImg = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT, // Don't repeat the image
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            backgroundSize
        );  
        Background background = new Background(backgroundImg);
        return background;
    }

     /**
     * Creates a welcome pane with the Blue Terrain Restaurant label
     * 
     * @return The welcome pane
     */
    public static StackPane welcomePane() {
        StackPane lightBluePadding = new StackPane();
        lightBluePadding.setPadding(new Insets(20));
        
        Label welcomeLabel = new Label(BLUE_TERRAIN);
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        welcomeLabel.setStyle("-fx-text-fill: white;");
        
        lightBluePadding.getChildren().add(welcomeLabel);

        return lightBluePadding;
    }

    /**
     * Sets up and displays the scene on the primary stage
     * 
     * @param primaryStage The primary stage of the JavaFX application
     * @param root         The root node of the scene graph
     */
    public static void setupAndShowScene(Stage primaryStage, Parent root) {
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a common header with background image for various views
     * 
     * @param imagePath The path to the image file
     * @return The VBox containing the common header
     */
    public static VBox commonHeader(String imagePath) {
        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER); 
        Background background = Functions.backGroundImage(imagePath);
        root.setBackground(background);
        return root;
    }

    /**
     * Creates a close button that navigates back to the restaurant view
     * 
     * @param primaryStage The primary stage of the JavaFX application
     * @return The close button
     */
    public static Button closeButton(Stage primaryStage) {
        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            Restaurant restaurant = new Restaurant();
            restaurant.start(primaryStage, Menu.getFirstName(), Menu.getLastName(), Menu.getProfileType(), Menu.getLoginType());
        });
        return closeButton;
    }

    /**
     * Creates a label displaying the opening hours of the restaurant
     * 
     * @return The label displaying the opening hours
     */
    public static Label openingHours() {
        Label openingHoursLabel = new Label(OPENING_HOURS);
        openingHoursLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        openingHoursLabel.setStyle("-fx-text-fill: white; -fx-background-color: #2E5CB8; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
        return openingHoursLabel;
    }

    /**
     * Sets margin for a node within a VBox layout
     * 
     * @param vbox    the VBox containing the node
     * @param node    the node to set margin for
     * @param insets  the insets specifying the margin
     */
    public static void setMarginForNode(VBox vbox, Node node, Insets insets) {
        VBox.setMargin(node, insets);
    }
    
    public static HBox createLabeledField(String labelText, String promptText) {
        HBox box = new HBox(10);
        Label label = new Label(labelText);
        label.setFont(new Font(20));
        label.setStyle("-fx-text-fill: white;");
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(new Font(15));
        textField.setMaxWidth(400);
        box.getChildren().addAll(label, textField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Creates an HBox containing a label and a text field for staff-related input fields
     * 
     * @param labelText   The label text
     * @param promptText  The prompt text for the text field
     * @return            The HBox containing the label and text field
     */
    public static HBox staffField(String labelText, String promptText) {
        HBox box = new HBox(10);
        Label label = new Label(labelText);
        label.setFont(new Font(20));
        label.setStyle("-fx-text-fill: darkBlue;");
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(new Font(15));
        textField.setMaxWidth(400);
        box.getChildren().addAll(label, textField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Creates an HBox containing a label and a text field for signup-related input fields
     * 
     * @param labelText   the text to be displayed as the label for the text field
     * @param promptText  the text to be displayed as a prompt inside the text field
     * @return            a horizontal box (HBox) containing a label and a text field
     */
    public static HBox createSignupField(String labelText, String promptText) {
        HBox box = new HBox(10);
        Label label = new Label(labelText);
        label.setFont(new Font(20));
        label.setStyle("-fx-text-fill: white;");
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(new Font(15));
        textField.setMaxWidth(400);
        box.getChildren().addAll(label, textField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Creates an HBox containing a label and a choice box for selecting profile types
     * 
     * @param labelText           The text to be displayed as the label for the text field
     * @param options             The array of options for the choice box
     * @param defaultSignupType   The default selection for the choice box
     * @return                    a horizontal box (HBox) containing the label and choice box
     */
    public static HBox selectProfileType(String labelText, String[] options, String defaultSignupType) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        Label label = new Label(labelText);
        label.setFont(new Font(15));
        label.setStyle("-fx-text-fill: black;");
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(options));
        choiceBox.getSelectionModel().select(defaultSignupType);
        choiceBox.setStyle("-fx-text-fill: black;");
        box.getChildren().addAll(label, choiceBox);
        return box;
    }

    /**
     * Creates a button with the specified text
     * 
     * @param text The text of the button
     * @return     The created button
     */
    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font(17));
        button.setStyle("-fx-text-fill: darkblue;");
        return button;
    }

    /**
     * Creates a VBox layout with centered alignment and default spacing and padding
     * 
     * @return The created VBox layout
     */
    public static VBox createRootVBox() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10); 
        root.setPadding(new Insets(20));
        return root;
    }

    /**
     * Creates a VBox layout with centered alignment and default spacing and padding, containing the specified buttons
     * 
     * @param buttons The buttons to be added to the VBox layout
     * @return        The created VBox layout
     */
    public static VBox createButtonVBoxMenu(Button... buttons) {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.getChildren().addAll(buttons);
        return box;
    }

    /**
     * Creates a button with the specified text, font size, and background color
     * 
     * @param text    The text of the button
     * @param bgColor The background color of the button
     * @return        The created button
     */
    public static Button createButtonMenu(String text, Color bgColor) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setTextFill(Color.DARKBLUE);
        button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setPrefSize(150, 150);

        button.setOnMouseEntered(event -> {
            Button sourceButton = (Button) event.getSource();
            Tooltip tooltip = (Tooltip) sourceButton.getTooltip();
            if (tooltip != null) {
                tooltip.show(sourceButton, event.getScreenX(), event.getScreenY() + 15);
            }
        });

        button.setOnMouseExited(event -> {
            Button sourceButton = (Button) event.getSource();
            Tooltip tooltip = (Tooltip) sourceButton.getTooltip();
            if (tooltip != null) {
                tooltip.hide();
            }
        });

        return button;
    }

    /**
     * Creates a custom-styled Button for the customer menu
     * 
     * @param text     the text to display on the button
     * @param bgColor  the background color of the button
     * @return         a custom-styled Button with the specified text and background color
     */
    public static Button createButtonMenuCustomer(String text, Color bgColor) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setTextFill(Color.DARKBLUE);
        button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setPrefSize(300, 300);

        button.setOnMouseEntered(event -> {
            Button sourceButton = (Button) event.getSource();
            Tooltip tooltip = (Tooltip) sourceButton.getTooltip();
            if (tooltip != null) {
                tooltip.show(sourceButton, event.getScreenX(), event.getScreenY() + 15);
            }
        });

        button.setOnMouseExited(event -> {
            Button sourceButton = (Button) event.getSource();
            Tooltip tooltip = (Tooltip) sourceButton.getTooltip();
            if (tooltip != null) {
                tooltip.hide();
            }
        });

        return button;
    }

    /**
     * Creates a logout button with confirmation dialog
     * 
     * @param primaryStage The primary stage of the JavaFX application
     * @return     The logout button
     */
    public static Node logOut(Stage primaryStage) {
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        logoutButton.setOnAction(e -> {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Logout Confirmation");
            alert.setHeaderText("Are you sure you want to logout?");

            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButtonType, cancelButtonType);

            // Handle user response
            alert.showAndWait().ifPresent(response -> {
                if (response == confirmButtonType) {
                    // Navigate back to login page
                    Login login = new Login();
                    login.start(primaryStage);
                }
            });
        });

        VBox logoutBox = new VBox(20);
        logoutBox.setAlignment(Pos.BOTTOM_RIGHT);
        logoutBox.setPadding(new Insets(20));
        logoutBox.getChildren().addAll(logoutButton);

        return logoutBox;
    }

    /**
     * Fetches data from the database based on the specified item type and SQL query
     * 
     * @param itemType The type of item to fetch
     * @param query    The SQL query to execute
     * @return         An ArrayList containing the fetched data
     */
    public static ArrayList<String> fetchDBdata(String itemType, String query) {        
        ArrayList<String> tooltipTextList = new ArrayList<>();
        try (Connection connection = Functions.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, itemType);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String value = resultSet.getString("result_column");
                    tooltipTextList.add(value);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch names");
        }
        
        return tooltipTextList;
    }

    /**
     * Creates a button with the specified text, font size, and background color
     * 
     * @param text    The text of the button
     * @param bgColor The background color of the button
     * @return The created button
     */
    public static Button createButton1(String text, Color bgColor) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setTextFill(Color.DARKBLUE);
        button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setPrefWidth(150);
        button.setPrefHeight(150);
        return button;
    }

    /**
     * Creates a VBox layout with centered alignment, default spacing and padding, containing buttons with specified labels and background color
     * 
     * @param color         The background color for the buttons
     * @param buttonLabels  The labels for the buttons
     * @return              The created VBox layout
     */
    public static VBox createButtonVBox(Color color, String... buttonLabels) {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        
        for (String label : buttonLabels) {
            Button button = createButton1(label, color);
            box.getChildren().add(button);
        }
        return box;
    }
   
}
