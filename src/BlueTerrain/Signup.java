package Bluetrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class Signup {
    public void start(Stage primaryStage, String defaultSignupType) {
        VBox root = Functions.createRootVBox();

        HBox firstNameBox = Functions.createLabeledField("First Name", "Enter your First Name");
        HBox lastNameBox = Functions.createLabeledField("Last Name", "Enter your Last Name");
        HBox addressBox = Functions.createLabeledField("Address     ", "Enter your Address");
        HBox emailBox = Functions.createLabeledField("Email ID      ", "Enter your Email ID");
        Button signUpButton = Functions.createButton("Sign Up");

        HBox signupTypeBox = new HBox(10);
        signupTypeBox.setAlignment(Pos.CENTER);
        Label signUpTypeLabel = new Label("Select Signup Type");
        signUpTypeLabel.setFont(new Font(15));
        signUpTypeLabel.setStyle("-fx-text-fill: black;");
        ChoiceBox<String> signUpTypeChoiceBox = new 
            ChoiceBox<>(FXCollections.observableArrayList("Customer", "Staff"));
        signUpTypeChoiceBox.getSelectionModel().select(defaultSignupType); 
        signUpTypeChoiceBox.setStyle("-fx-text-fill: black;");
        signupTypeBox.getChildren().addAll(signUpTypeLabel, signUpTypeChoiceBox);

        HBox profileTypeBox = new HBox(10);
        profileTypeBox.setAlignment(Pos.CENTER);
        Label profileTypeLabel = new Label("Select Profile Type");
        profileTypeLabel.setFont(new Font(15));
        profileTypeLabel.setStyle("-fx-text-fill: black;");
        ChoiceBox<String> profileTypeChoiceBox = new 
            ChoiceBox<>(FXCollections.observableArrayList("Waiter", "Chef", "Delivery Driver", "Manager"));
        profileTypeChoiceBox.getSelectionModel().selectFirst(); 
        profileTypeChoiceBox.setStyle("-fx-text-fill: black;");
        profileTypeBox.getChildren().addAll(profileTypeLabel, profileTypeChoiceBox);

        signUpTypeChoiceBox.setOnAction(e -> {
            if (signUpTypeChoiceBox.getValue().equals("Customer")) {
                root.getChildren().remove(firstNameBox);
                root.getChildren().remove(lastNameBox);
                root.getChildren().remove(emailBox);
                root.getChildren().remove(profileTypeBox);
                root.getChildren().remove(signUpButton);
                root.getChildren().addAll(firstNameBox, lastNameBox, 
                                    emailBox, profileTypeBox, signUpButton);
                if (!root.getChildren().contains(addressBox)) {
                    root.getChildren().add(5, addressBox);
                }
            } else {
                root.getChildren().remove(profileTypeBox);
                root.getChildren().remove(firstNameBox);
                root.getChildren().remove(lastNameBox);
                root.getChildren().remove(emailBox);
                root.getChildren().remove(addressBox);
                root.getChildren().remove(signUpButton);
                root.getChildren().addAll(firstNameBox, lastNameBox, 
                        emailBox, profileTypeBox, signUpButton);
            }
        });

        signUpButton.setOnAction(e -> {
            String firstName = ((TextField) firstNameBox.getChildren().get(1)).getText();
            String lastName = ((TextField) lastNameBox.getChildren().get(1)).getText();
            String email = ((TextField) emailBox.getChildren().get(1)).getText();
            String address = ((TextField) addressBox.getChildren().get(1)).getText();
            String profileType = profileTypeChoiceBox.getValue();
            String tableName;

            if (signUpTypeChoiceBox.getValue().equals("Customer")) {
                tableName = "customers";
            } else {
                tableName = "staffs";
            }

            try {
                Connection connection = Functions.getConnection();
                if (tableName.equals("customers")) {
                    String query = "INSERT INTO " + tableName + " (first_name, last_name, email, address) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, address);
                    preparedStatement.executeUpdate();
                } else {
                    String query = "INSERT INTO " + tableName + " (first_name, last_name, email, profile_type) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, profileType);
                    preparedStatement.executeUpdate();
                }   
                System.out.println("User signed up successfully!");
                primaryStage.close();
                Login login = new Login();
                login.start(new Stage());
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: Failed to sign up user");
            }
        });

        Hyperlink loginLink = new Hyperlink("Return to Login page");
        loginLink.setOnAction(e -> {
            primaryStage.close();
                Login login = new Login();
                login.start(new Stage());
        });

        root.getChildren().addAll(Functions.welcomePane(), signupTypeBox, firstNameBox, 
                    lastNameBox, emailBox, addressBox, signUpButton, loginLink);
        Functions.setupAndShowScene(primaryStage, root, 800, 600); 
    }
}
