package BlueTerrain;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

        root.getChildren().addAll(Functions.welcomePane(), signupTypeBox, firstNameBox, 
                    lastNameBox, emailBox, addressBox, signUpButton);
        Functions.setupAndShowScene(primaryStage, root, 800, 600); 
    }
}
