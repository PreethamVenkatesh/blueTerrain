package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login {
<<<<<<< HEAD
        public void start(Stage primaryStage) {
=======
    public void start(Stage primaryStage) {
>>>>>>> 9bfa5410f9d6d81c3369899f5863b21fd776248f
        
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
            TextField usernameField = (TextField) usernameBox.getChildren().get(1);
            String username = usernameField.getText();
            String loginType = loginTypeChoiceBox.getValue();

            if (authenticate(username, loginType)) {
                Restaurant restaurant = new Restaurant();
                restaurant.start(primaryStage);
            } else {
                System.out.println("Invalid username.");
            }

            
        });
    }

    private boolean authenticate(String username, String loginType) {
        String tableName = (loginType.equals("Customer")) ? "customers" : "staffs";
        String query = "SELECT * FROM " + tableName + " WHERE first_name = ?";
        
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to authenticate user");
            return false; 
        }
    }
}
