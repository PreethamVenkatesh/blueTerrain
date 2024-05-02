package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Login class manages the login functionality of the application.
 * It provides methods to authenticate users and display the login interface.
 * 
 * @author Preetham
 */
public class Login {

@FXML
private StackPane loginPane;

    private static String LOGIN_TYPE = "Select login type";
    private static String SIGN_UP = "Don't have an account? Sign up";
    private static String ENTER_USERNAME = "Enter your username";
    private static String INVALID_USERNAME = "Invalid username.";
    private static String CUSTOMER = "Customer";
    private static String STAFF = "Staff";

    /**
     * Displays the login interface.
     * 
     * @param primaryStage The primary stage of the application.
     */
    public void start(Stage primaryStage) {        
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_BackgroundImage.jpg");

        HBox loginTypeBox = new HBox(10);
        loginTypeBox.setAlignment(Pos.TOP_CENTER);
        Label loginTypeLabel = new Label(LOGIN_TYPE);
        loginTypeLabel.setFont(new Font(20));
        loginTypeLabel.setStyle("-fx-text-fill: white;");
        ChoiceBox<String> loginTypeChoiceBox = new 
                ChoiceBox<>(FXCollections.observableArrayList(CUSTOMER, STAFF));
        loginTypeChoiceBox.getSelectionModel().selectFirst();
        loginTypeChoiceBox.setStyle("-fx-text-fill: black;");
        loginTypeBox.getChildren().addAll(loginTypeLabel, loginTypeChoiceBox);

        HBox usernameBox = Functions.createLabeledField("\tUsername", ENTER_USERNAME);
        usernameBox.setAlignment(Pos.TOP_CENTER);
        
        Button loginButton = Functions.createButton("Login");
        loginButton.setAlignment(Pos.TOP_CENTER);

        Hyperlink signUpLink = new Hyperlink(SIGN_UP);
        signUpLink.setAlignment(Pos.TOP_CENTER);
        signUpLink.setFont(new Font(17));
        signUpLink.setOnAction(e -> {
            Signup signUpPage = new Signup();
            signUpPage.start(primaryStage, CUSTOMER);
        });

        Text errorMessage = new Text();
        errorMessage.setFill(Color.RED);

        root.getChildren().addAll(Functions.welcomePane(), loginTypeBox, 
                    usernameBox, loginButton, signUpLink, errorMessage);
        Functions.setupAndShowScene(primaryStage, root); 
        
        loginButton.setOnAction(e -> {
            TextField usernameField = (TextField) usernameBox.getChildren().get(1);
            String username = usernameField.getText();
            String loginType = loginTypeChoiceBox.getValue();

            String[] userDetails = authenticate(username, loginType);
            if (userDetails != null) {
                String firstName = userDetails[0];
                String lastName = userDetails[1];
                String profileType = userDetails[2];
                if (loginType.equals(STAFF)) { 
                    Restaurant restaurant = new Restaurant();
                    restaurant.start(primaryStage, firstName, lastName, profileType, loginType);
                } else if (loginType.equals(CUSTOMER))
                 {
                    Bookings bookings = new Bookings();
                    bookings.start(primaryStage,firstName,lastName, loginType, profileType);
                } else {
                    errorMessage.setText("Customer login development in progress");
                }
            } else {
                errorMessage.setText(INVALID_USERNAME);
            }
        });
    }

    /**
     * Authenticates a user based on the provided username and login type.
     * 
     * @param username  The username of the user.
     * @param loginType The type of login (e.g., Customer or Staff).
     * @return An array containing the user's details if authenticated, or null if authentication fails.
     */
    private String[] authenticate(String username, String loginType) {
        String tableName = (loginType.equals(CUSTOMER)) ? "customers" : "staffs";
        String query = "SELECT * FROM " + tableName + " WHERE first_name = ?";
        
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (loginType.equals(STAFF)) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String profileType = resultSet.getString("profile_type");
                        return new String[]{firstName, lastName, profileType};
                    } else {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        return new String[]{firstName, lastName, ""};
                    }
                    
                } else {
                    return null; 
                } 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to authenticate user - " + ex.getMessage()); 
            return null; 
        }
    }

}