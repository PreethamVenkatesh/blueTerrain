package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The Signup class represents the functionality for user signup in the restaurant application.
 * 
 * <p>Users can sign up with their first name, last name, and address.</p>
 * 
 * <p>After signing up, the user is navigated back to the login page.</p>
 * 
 * @author Preetham Venkatesh
 */
public class Signup {
    /**
     * Starts the signup process.
     * 
     * @param primaryStage      The primary stage of the JavaFX application.
     * @param defaultSignupType The default signup type.
     */
    public void start(Stage primaryStage, String defaultSignupType) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Signup.png");
        // Creates a Label with a prompt for signing up.
        Label signupLabel = new Label("Are you here for the first time? Signup below");
        signupLabel.setFont(new Font(20)); 
        signupLabel.setStyle("-fx-text-fill: yellow;");
        // Creates a Label with a note regarding the navigation after signing up.
        Label noteLabel = new Label("After Signup you will be navigated back to Login page");
        noteLabel.setFont(new Font(16)); 
        noteLabel.setStyle("-fx-text-fill: yellow;");

        HBox firstNameBox = Functions.createLabeledField("First Name", "Enter your First Name");
        HBox lastNameBox = Functions.createLabeledField("Last Name", "Enter your Last Name");
        HBox addressBox = Functions.createLabeledField("Address     ", "Enter your Address");
        Button signUpButton = Functions.createButton("Sign Up");
        // Creates a Hyperlink for navigating back to the login page if the user already has an account.
        Hyperlink loginLink = new Hyperlink("Aleady have an account? Return to Login page");
        loginLink.setFont(new Font(15));
        loginLink.setOnAction(e -> {
            primaryStage.close();
                Login login = new Login();
                login.start(new Stage());
        });

        Functions.setMarginForNode(root, signupLabel, new Insets(10, 10, 10, 0));
        Functions.setMarginForNode(root, firstNameBox, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, lastNameBox, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, addressBox, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, signUpButton, new Insets(10, 10, 10, 0));

        signUpButton.setOnAction(e -> {
            String firstName = ((TextField) firstNameBox.getChildren().get(1)).getText();
            String lastName = ((TextField) lastNameBox.getChildren().get(1)).getText();
            String address = ((TextField) addressBox.getChildren().get(1)).getText();
            String tableName = "customers";

            try {
                Connection connection = Functions.getConnection();
                String query = "INSERT INTO " + tableName + " (first_name, last_name, address) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, address);
                preparedStatement.executeUpdate();

                System.out.println("User signed up successfully!");
                primaryStage.close();
                Login login = new Login();
                login.start(new Stage());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    
        root.getChildren().addAll(Functions.welcomePane(), signupLabel, firstNameBox, lastNameBox, addressBox, signUpButton,noteLabel, loginLink);
        Functions.setupAndShowScene(primaryStage, root); 
    }
}
