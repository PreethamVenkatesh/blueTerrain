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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Functions {

    private static String OPENING_HOURS = "Opening Hours: 11:00 AM - 12:00 PM";
    private static String BLUE_TERRAIN = "Welcome to BlueTerrain";
//will vary basedo n your db setup
    public static Connection getConnection() {
        Connection connection = null;
        try {
                 
            String databaseName = "cafedb";
    String USERNAME = "root";
    String PASSWORD = "2360313";
    String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/" + databaseName;

            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return connection;
    }

    public static StackPane welcomePane() {
        Color lightBlue = Color.rgb(173, 216, 230);
        StackPane lightBluePadding = new StackPane();
        lightBluePadding.setBackground(new Background(new BackgroundFill(lightBlue, CornerRadii.EMPTY, Insets.EMPTY)));
        lightBluePadding.setPadding(new Insets(10));
        
        Label welcomeLabel = new Label(BLUE_TERRAIN);
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        welcomeLabel.setStyle("-fx-text-fill: darkblue;");
        
        lightBluePadding.getChildren().add(welcomeLabel);

        return lightBluePadding;
    }
    
    public static HBox createLabeledField(String labelText, String promptText) {
        HBox box = new HBox(10);
        Label label = new Label(labelText);
        label.setFont(new Font(20));
        label.setStyle("-fx-text-fill: darkblue;");
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(new Font(15));
        textField.setMaxWidth(400);
        box.getChildren().addAll(label, textField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

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

    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font(17));
        button.setStyle("-fx-text-fill: darkblue;");
        return button;
    }

    public static VBox createRootVBox() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10); 
        root.setPadding(new Insets(20));
        return root;
    }

    public static void setupAndShowScene(Stage primaryStage, Parent root, int width, int height) {
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static StackPane restuarantLabel() {
        Color lightBlue = Color.rgb(173, 216, 230);
        
        StackPane stackPane = new StackPane();
        stackPane.setBackground(new Background(new BackgroundFill(lightBlue, CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.setPadding(new Insets(10));
        
        Label label = new Label("BlueTerrain");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        label.setStyle("-fx-text-fill: darkblue;");
        
        stackPane.getChildren().add(label);
        
        return stackPane;
    }

    public static VBox createButtonVBoxMenu(Button... buttons) {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.getChildren().addAll(buttons);
        return box;
    }

    public static void setTooltipMenu(Button button, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setAutoHide(false);
        button.setTooltip(tooltip);
    }

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

    public static void setTooltip(Button button, ArrayList<String> tooltipTextList) {
        if (!tooltipTextList.isEmpty()) {
            StringBuilder tooltipText = new StringBuilder();
            for (String name : tooltipTextList) {
                tooltipText.append(name).append("\n");
            }
            Tooltip tooltip = new Tooltip(tooltipText.toString());
            tooltip.setAutoHide(false);
            button.setTooltip(tooltip);
        }
    }

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

    public static Stage createPopupWindow(Stage primaryStage) {
        Stage menuPopup = new Stage();
        menuPopup.initOwner(primaryStage);
        menuPopup.initStyle(StageStyle.UTILITY);
        menuPopup.initModality(Modality.APPLICATION_MODAL);
        return menuPopup;
    }

    public static Button createButton1(String text, Color bgColor) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setTextFill(Color.DARKBLUE);
        button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setPrefWidth(150);
        button.setPrefHeight(150);
        return button;
    }

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

    public static Label openingHours() {
        Label openingHoursLabel = new Label(OPENING_HOURS);
        openingHoursLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        openingHoursLabel.setStyle("-fx-text-fill: darkblue;");
        return openingHoursLabel;
    }

}
