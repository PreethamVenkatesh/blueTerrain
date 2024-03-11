package BlueTerrain;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login {
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setSpacing(15);
        root.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome to BlueTerrain");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        welcomeLabel.setStyle("-fx-text-fill: darkblue;");

        Label loginTypeLabel = new Label("Select login type");
        loginTypeLabel.setFont(new Font(15));
        loginTypeLabel.setStyle("-fx-text-fill: black;");
        ChoiceBox<String> loginTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Customer", "Staff"));
        loginTypeChoiceBox.getSelectionModel().selectFirst();
        loginTypeChoiceBox.setStyle("-fx-text-fill: black;");

        HBox usernameBox = new HBox(10);
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font(20));
        usernameLabel.setStyle("-fx-text-fill: darkblue;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setFont(new Font(15));
        usernameField.setMaxWidth(400);
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        HBox passwordBox = new HBox(10);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(20));
        passwordLabel.setStyle("-fx-text-fill: darkblue;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setFont(new Font(15));
        passwordField.setMaxWidth(400);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-text-fill: darkblue;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticate(username, password)) {
                root.getChildren().clear();
                root.getChildren().add(new Label("Welcome, " + username + " to BlueTerrain"));
            } else {
                root.getChildren().clear();
                root.getChildren().addAll(new Label("Invalid username or password."), usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
            }
        });
        loginButton.setFont(new Font(17));

        

        root.getChildren().addAll(welcomeLabel, loginTypeLabel, loginTypeChoiceBox, usernameBox, passwordBox, loginButton);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();    
    }

    private boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("password");
    }
}
