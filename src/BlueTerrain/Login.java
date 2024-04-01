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
        public void start(Stage primaryStage) {
        
        VBox root = Functions.createRootVBox();

        HBox loginTypeBox = new HBox(10);
        loginTypeBox.setAlignment(Pos.CENTER);
        Label loginTypeLabel = new Label("Select login type");
        loginTypeLabel.setFont(new Font(15));
        loginTypeLabel.setStyle("-fx-text-fill: black;");
        ChoiceBox<String> loginTypeChoiceBox = new 
                ChoiceBox<>(FXCollections.observableArrayList("Customer", "Staff"));
        loginTypeChoiceBox.getSelectionModel().selectFirst();
        loginTypeChoiceBox.setStyle("-fx-text-fill: black;");
        loginTypeBox.getChildren().addAll(loginTypeLabel, loginTypeChoiceBox);

        HBox usernameBox = Functions.createLabeledField("Username", "Enter your username");
        Button loginButton = Functions.createButton("Login");

        Hyperlink signUpLink = new Hyperlink("Don't have an account? Sign up");
        signUpLink.setOnAction(e -> {
            Signup signUpPage = new Signup();
            signUpPage.start(primaryStage, "Customer");
        });

        root.getChildren().addAll(Functions.welcomePane(), loginTypeBox, 
                    usernameBox, loginButton, signUpLink);
        Functions.setupAndShowScene(primaryStage, root, 800, 600); 
        
        loginButton.setOnAction(e -> {
            Restaurant restaurant = new Restaurant();
            restaurant.start(primaryStage);
        });

        // loginButton.setOnAction(e -> {
        //     String username = usernameField.getText();
        //     String password = passwordField.getText();

        //     if (authenticate(username, password)) {
        //         root.getChildren().clear();
        //         root.getChildren().add(new Label("Welcome, " 
        //                 + username + " to BlueTerrain"));
        //     } else {
        //         root.getChildren().clear();
        //         root.getChildren().addAll(new Label("Invalid username or password."), 
        //                 usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        //     }
        // });
    }

    // private boolean authenticate(String username, String password) {
    //     return username.equals("admin") && password.equals("password");
    // }
}
