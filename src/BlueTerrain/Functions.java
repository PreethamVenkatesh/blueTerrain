package BlueTerrain;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Functions {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // JDBC URL, username, and password of MySQL server
            String JDBC_URL = "jdbc:mysql://localhost:3306/BlueTerrain_Restaurant";
            String USERNAME = "root";
            String PASSWORD = "";

            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("DB Connection successful");
            } else {
                System.out.println("Unsuccessful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection error
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return connection;
    }

    public static StackPane welcomePane() {
        Color lightBlue = Color.rgb(173, 216, 230);
        StackPane lightBluePadding = new StackPane();
        lightBluePadding.setBackground(new Background(new BackgroundFill(lightBlue, CornerRadii.EMPTY, Insets.EMPTY)));
        lightBluePadding.setPadding(new Insets(10));
        
        Label welcomeLabel = new Label("Welcome to BlueTerrain");
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
}
