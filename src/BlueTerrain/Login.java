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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {

    private static String LOGIN_TYPE = "Select login type";
    private static String SIGN_UP = "Don't have an account? Sign up";
    private static String ENTER_USERNAME = "Enter your username";
    private static String INVALID_USERNAME = "Invalid username.";
    private static String FAILED_AUTHENTICATE = "Error: Failed to authenticate user";

    public void start(Stage primaryStage) {
        VBox root = Functions.createRootVBox();

        HBox loginTypeBox = new HBox(10);
        loginTypeBox.setAlignment(Pos.CENTER);
        Label loginTypeLabel = new Label(LOGIN_TYPE);
        loginTypeLabel.setFont(new Font(15));
        loginTypeLabel.setStyle("-fx-text-fill: black;");
        ChoiceBox<String> loginTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Customer", "Staff"));
        loginTypeChoiceBox.getSelectionModel().selectFirst();
        loginTypeChoiceBox.setStyle("-fx-text-fill: black;");
        loginTypeBox.getChildren().addAll(loginTypeLabel, loginTypeChoiceBox);

        HBox usernameBox = Functions.createLabeledField("Username", ENTER_USERNAME);
        Button loginButton = Functions.createButton("Login");

        Hyperlink signUpLink = new Hyperlink(SIGN_UP);
        signUpLink.setOnAction(e -> {
            Signup signUpPage = new Signup();
            signUpPage.start(primaryStage, "Customer");
        });

        Text errorMessage = new Text();
        errorMessage.setFill(Color.RED);

        root.getChildren().addAll(Functions.welcomePane(), loginTypeBox,
                usernameBox, loginButton, signUpLink, errorMessage);
        Functions.setupAndShowScene(primaryStage, root, 800, 600);

        loginButton.setOnAction(e -> {
            TextField usernameField = (TextField) usernameBox.getChildren().get(1);
            String username = usernameField.getText();
            String loginType = loginTypeChoiceBox.getValue();

            if (authenticate(username, loginType)) {
                if (loginType.equals(STAFF)) { 
                    Restaurant restaurant = new Restaurant();
                    restaurant.start(primaryStage);
                } else if (loginType.equals(CUSTOMER)) {
                    Bookings bookings = new Bookings();
                    bookings.start(primaryStage);
                } else {
                    errorMessage.setText("Customer login development in progress");
                }
            } else {
                errorMessage.setText(INVALID_USERNAME);
            }

        });
    }

    private boolean authenticate(String username, String loginType) {
        String tableName = (loginType.equals("Customer")) ? "customers" : "staffs";
        String query = "SELECT * FROM " + tableName + " WHERE first_name = ?";
        try (Connection connection = Functions.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(FAILED_AUTHENTICATE);
            return false;

        }
    }
}
